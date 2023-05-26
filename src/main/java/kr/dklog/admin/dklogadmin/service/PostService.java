package kr.dklog.admin.dklogadmin.service;

import kr.dklog.admin.dklogadmin.common.util.PageUtils;
import kr.dklog.admin.dklogadmin.dto.common.RequestPageDto;
import kr.dklog.admin.dklogadmin.dto.request.RequestKeywordDto;
import kr.dklog.admin.dklogadmin.dto.response.ResponsePostDto;
import kr.dklog.admin.dklogadmin.entity.Post;
import kr.dklog.admin.dklogadmin.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public void removePostList(List<Long> postIds){
        postRepository.deleteAllById(postIds);
    }

    public List<ResponsePostDto> getAll(RequestKeywordDto requestKeywordDto, RequestPageDto requestPageDto){
        Pageable pageable = PageUtils.setPageable(requestPageDto);
        Page<Post> postList = postRepository.findAll(PostSpecification.searchWith(requestKeywordDto), pageable);
        List<ResponsePostDto> responsePostDtoList = postList.stream().map(Post::toResponsePostDto).collect(Collectors.toList());
        return responsePostDtoList;
    }
}
