package kr.dklog.admin.dklogadmin.controller;

import kr.dklog.admin.dklogadmin.dto.common.RequestPageDto;
import kr.dklog.admin.dklogadmin.dto.request.RequestKeywordDto;
import kr.dklog.admin.dklogadmin.dto.request.RequestPostDeleteDto;
import kr.dklog.admin.dklogadmin.dto.response.ResponsePostDetailDto;
import kr.dklog.admin.dklogadmin.dto.response.ResponsePostListDto;
import kr.dklog.admin.dklogadmin.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    //insert하는 경우랑 겹치면 그때가서 uri 생각하기
    @PostMapping
    public ResponseEntity postRemove(@RequestBody RequestPostDeleteDto requestPostDeleteDto) {
        postService.removePostList(requestPostDeleteDto.getPostIds());
        return ResponseEntity.status(204).build();
    }

    @GetMapping
    public ResponseEntity<ResponsePostListDto> postList(RequestKeywordDto requestKeywordDto, RequestPageDto requestPageDto){
        ResponsePostListDto postList = postService.getAll(requestKeywordDto, requestPageDto);
        return ResponseEntity.ok(postList);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ResponsePostDetailDto> postDetail(@PathVariable Long postId){
        ResponsePostDetailDto postDetailDto = postService.getOne(postId);
        return ResponseEntity.ok(postDetailDto);
    }
}
