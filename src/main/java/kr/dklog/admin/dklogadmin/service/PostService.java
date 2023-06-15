package kr.dklog.admin.dklogadmin.service;

import kr.dklog.admin.dklogadmin.common.exception.PostNotFoundException;
import kr.dklog.admin.dklogadmin.common.util.PagingUtil;
import kr.dklog.admin.dklogadmin.dto.request.RequestPostDto;
import kr.dklog.admin.dklogadmin.dto.response.ResponsePostListDto;
import kr.dklog.admin.dklogadmin.entity.Post;
import kr.dklog.admin.dklogadmin.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public void removePostList(List<Long> postIds){
        try {
            postRepository.deleteAllById(postIds);
        }catch (EmptyResultDataAccessException e){
            throw new PostNotFoundException();
        }
    }

    public ResponsePostListDto getAll(RequestPostDto requestPostDto){
        if(requestPostDto.getColumn()==null || requestPostDto.getColumn()==""){
            requestPostDto.setColumn("postId");
        }
        Pageable pageable = PageRequest.of(requestPostDto.getPage(), requestPostDto.getPageSize(), requestPostDto.getSortDirection(), requestPostDto.getColumn());
        Page<Post> postList = postRepository.findAll(searchWith(requestPostDto), pageable);
        ResponsePostListDto responsePostListDto = ResponsePostListDto.builder()
                .pagingUtil(new PagingUtil(postList.getTotalElements(), postList.getTotalPages(), postList.getNumber(), postList.getSize()))
                .postList(postList.stream().map(Post::toResponsePostDto).collect(Collectors.toList()))
                .build();
        return responsePostListDto;
    }

    private Specification<Post> searchWith(RequestPostDto requestPostDto){
        return ((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(StringUtils.hasText(requestPostDto.getKeyword()) && StringUtils.hasText(requestPostDto.getKeywordType()))
                predicates.add(builder.like(root.get(requestPostDto.getKeywordType()), "%"+requestPostDto.getKeyword()+"%"));
            if(StringUtils.hasText(requestPostDto.getStartDate())&& StringUtils.hasText(requestPostDto.getEndDate()))
                predicates.add(builder.between(root.get("createdDate"), LocalDate.parse(requestPostDto.getStartDate()).atStartOfDay(), LocalDate.parse(requestPostDto.getEndDate()).atTime(LocalTime.MAX)));
            return builder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
