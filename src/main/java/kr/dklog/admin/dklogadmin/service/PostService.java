package kr.dklog.admin.dklogadmin.service;

import kr.dklog.admin.dklogadmin.dto.response.ResponsePostDto;
import kr.dklog.admin.dklogadmin.entity.Post;
import kr.dklog.admin.dklogadmin.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public void delete(List<Long> postIds){
        postRepository.deleteAllById(postIds);
    }

    public List<ResponsePostDto> findAll(){
        List<Post> postList = postRepository.findAll();
        List<ResponsePostDto> responsePostDtos = postList.stream().map(post -> {
            ResponsePostDto responsePostDto = ResponsePostDto.builder()
                    .postId(post.getPostId())
                    .title(post.getTitle())
                    .picture(post.getMember().getPicture())
                    .username(post.getMember().getGithubUsername())
                    .createdDate(post.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")))
                    .modifiedDate((post.getModifiedDate()!=null) ? post.getModifiedDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")) : null)
                    .contentHtml(post.getContentHtml())
                    .contentMd(post.getContentMd())
                    .views(post.getViews())
                    .build();
            return responsePostDto;
        }).collect(Collectors.toList());
        return responsePostDtos;
    }
}
