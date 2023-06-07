package kr.dklog.admin.dklogadmin.service;

import kr.dklog.admin.dklogadmin.common.util.PagingUtil;
import kr.dklog.admin.dklogadmin.dto.response.ResponseCommentListDto;
import kr.dklog.admin.dklogadmin.entity.Member;
import kr.dklog.admin.dklogadmin.entity.Student;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import kr.dklog.admin.dklogadmin.dto.request.RequestCommentListDto;
import kr.dklog.admin.dklogadmin.entity.Comment;
import kr.dklog.admin.dklogadmin.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public ResponseCommentListDto getList(RequestCommentListDto requestCommentListDto) {
        if (requestCommentListDto.getColumn() == null)
            requestCommentListDto.setColumn("createdDate");
        System.out.println(requestCommentListDto.getStartDate());
        System.out.println(requestCommentListDto.getEndDate());

        PageRequest pageRequest = PageRequest.of(requestCommentListDto.getPage(), requestCommentListDto.getPageSize(), requestCommentListDto.getSortDirection(), requestCommentListDto.getColumn());
        Page<Comment> commentList = commentRepository.findAll(searchWith(requestCommentListDto), pageRequest);

        ResponseCommentListDto responseCommentListDto = ResponseCommentListDto.builder().pagingUtil(new PagingUtil(commentList.getTotalElements(), commentList.getTotalPages(), commentList.getNumber(), commentList.getSize())).commentList(commentList.stream()
                .map(Comment::toResponseCommentDto)
                .collect(Collectors.toList())).build();

        return responseCommentListDto;
    }

    @Transactional
    public void removeCommentList(List<Long> commentIds) {
        commentRepository.deleteAllById(commentIds);
    }

    private Specification<Comment> searchWith(RequestCommentListDto requestCommentListDto) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (requestCommentListDto.getStudentId() != null) {
                Join<Comment, Member> memberJoin = root.join("member");
                Join<Member, Student> studentJoin = memberJoin.join("student");
                predicates.add(criteriaBuilder.equal(studentJoin.get("studentId"), requestCommentListDto.getStudentId()));
            }

            if (StringUtils.hasText(requestCommentListDto.getName())) {
                Join<Comment, Member> memberJoin = root.join("member");
                Join<Member, Student> studentJoin = memberJoin.join("student");
                predicates.add(criteriaBuilder.like(studentJoin.get("name"), "%" + requestCommentListDto.getName() + "%"));
            }

            if (StringUtils.hasText(requestCommentListDto.getContent())) {
                predicates.add(criteriaBuilder.like(root.get("content"), "%" + requestCommentListDto.getContent() + "%"));
            }

            if (requestCommentListDto.getSemester() != null) {
                Join<Comment, Member> memberJoin = root.join("member");
                Join<Member, Student> studentJoin = memberJoin.join("student");
                predicates.add(criteriaBuilder.equal(studentJoin.get("semester"), requestCommentListDto.getSemester()));
            }

            if (Strings.isNotEmpty(requestCommentListDto.getStartDate()) && Strings.isNotEmpty(requestCommentListDto.getEndDate())) {
                DateTimeFormatter formatterAtLocaleDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                predicates.add(criteriaBuilder.between(root.get("createdDate"),
                                LocalDateTime.of(LocalDate.parse(requestCommentListDto.getStartDate(), formatterAtLocaleDate), LocalTime.of(0, 0, 0)),
                                LocalDateTime.of(LocalDate.parse(requestCommentListDto.getEndDate(), formatterAtLocaleDate), LocalTime.of(23, 59, 59))
                        )
                );
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}