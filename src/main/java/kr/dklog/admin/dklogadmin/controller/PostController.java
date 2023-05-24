package kr.dklog.admin.dklogadmin.controller;

import kr.dklog.admin.dklogadmin.dto.response.ResponsePostDto;
import kr.dklog.admin.dklogadmin.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @DeleteMapping("/{postIds}")
    public ResponseEntity<List<ResponsePostDto>> remove(@PathVariable List<Long> postIds) {
        postService.delete(postIds);
        List<ResponsePostDto> postList = postService.findAll();
        ResponseEntity<List<ResponsePostDto>> response = ResponseEntity.ok(postList);
        return response;
    }
}
