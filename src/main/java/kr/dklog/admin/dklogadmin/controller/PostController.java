package kr.dklog.admin.dklogadmin.controller;

import kr.dklog.admin.dklogadmin.dto.common.RequestPageDto;
import kr.dklog.admin.dklogadmin.dto.request.RequestKeywordDto;
import kr.dklog.admin.dklogadmin.dto.request.RequestPostDeleteDto;
import kr.dklog.admin.dklogadmin.dto.response.ResponsePostDto;
import kr.dklog.admin.dklogadmin.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<ResponsePostDto>> postList(RequestKeywordDto requestKeywordDto, RequestPageDto requestPageDto){
        List<ResponsePostDto> postList = postService.getAll(requestKeywordDto, requestPageDto);
        ResponseEntity<List<ResponsePostDto>> response = ResponseEntity.ok(postList);
        return response;
    }
}
