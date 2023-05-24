package kr.dklog.admin.dklogadmin.service;

import kr.dklog.admin.dklogadmin.dto.response.ResponsePostDto;
import kr.dklog.admin.dklogadmin.entity.Post;
import kr.dklog.admin.dklogadmin.repository.PostRepository;
import lombok.RequiredArgsConstructor;
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

    public List<ResponsePostDto> getAll(){
        List<Post> postList = postRepository.findAll();
        List<ResponsePostDto> responsePostDtoList = postList.stream().map(Post::toResponsePostDto).collect(Collectors.toList());
        return responsePostDtoList;
    }
}
