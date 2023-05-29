package kr.dklog.admin.dklogadmin.service;

import kr.dklog.admin.dklogadmin.common.util.PagingUtil;
import kr.dklog.admin.dklogadmin.dto.response.ResponseCommentListDto;
import kr.dklog.admin.dklogadmin.entity.Member;
import kr.dklog.admin.dklogadmin.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public ResponseCommentListDto getList(RequestCommentListDto requestCommentListDto){
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        PageRequest pageRequest = PageRequest.of(requestCommentListDto.getPage(), requestCommentListDto.getPageSize(),sort);
        Page<Comment> commentList = commentRepository.findAll(condition(requestCommentListDto),pageRequest);

        ResponseCommentListDto responseCommentListDto= ResponseCommentListDto.builder().pagingUtil(new PagingUtil(commentList.getTotalElements(), commentList.getTotalPages(),commentList.getNumber(), commentList.getSize())).commentList(commentList.stream()
                .map(Comment::toResponseCommentDto)
                .collect(Collectors.toList())).build();

        return responseCommentListDto;
    }

    public Specification<Comment> condition(RequestCommentListDto requestCommentListDto) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (requestCommentListDto.getStudentId() != null) {
                Join<Comment, Member> memberJoin = root.join("member");
                Join<Member, Student> studentJoin = memberJoin.join("student");
                predicates.add(criteriaBuilder.equal(studentJoin.get("studentId"),  requestCommentListDto.getStudentId()));
            }

            if (StringUtils.hasText(requestCommentListDto.getName())) {
                Join<Comment, Member> memberJoin = root.join("member");
                Join<Member, Student> studentJoin = memberJoin.join("student");
                predicates.add(criteriaBuilder.like(studentJoin.get("name"), "%" + requestCommentListDto.getName() + "%"));
            }

            if(StringUtils.hasText(requestCommentListDto.getContent())) {
                predicates.add(criteriaBuilder.like(root.get("content"),"%"+requestCommentListDto.getContent()+"%"));
            }

            if (requestCommentListDto.getSemester() != null) {
                Join<Comment, Member> memberJoin = root.join("member");
                Join<Member, Student> studentJoin = memberJoin.join("student");
                predicates.add(criteriaBuilder.equal(studentJoin.get("semester"), requestCommentListDto.getSemester()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    @Transactional
    public void removeCommentList(List<Long> commentIds){
        commentRepository.deleteAllById(commentIds);
    }

}