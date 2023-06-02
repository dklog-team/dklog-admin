package kr.dklog.admin.dklogadmin.controller;

import kr.dklog.admin.dklogadmin.common.data.AdminData;
import kr.dklog.admin.dklogadmin.dto.request.RequestKeywordDto;
import kr.dklog.admin.dklogadmin.dto.request.RequestPostDeleteDto;
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
    @PostMapping("/resources")
    public ResponseEntity postRemove(AdminData adminData, @RequestBody RequestPostDeleteDto requestPostDeleteDto) {
        postService.removePostList(requestPostDeleteDto.getPostIds());
        return ResponseEntity.status(204).build();
    }

    @GetMapping
    public ResponseEntity<ResponsePostListDto> postList(AdminData adminData, RequestKeywordDto requestKeywordDto){
        System.out.println(requestKeywordDto.getPage());
        System.out.println(requestKeywordDto.getSortDirection());
        ResponsePostListDto postList = postService.getAll(requestKeywordDto);
        return ResponseEntity.ok(postList);
    }

    // 하나만 가져오는 기능이 필요가 없어짐 나중에 필요하면 사용~
//    @GetMapping("/{postId}")
//    public ResponseEntity<ResponsePostDetailDto> postDetail(AdminData adminData, @PathVariable Long postId){
//        ResponsePostDetailDto postDetailDto = postService.getOne(postId);
//        return ResponseEntity.ok(postDetailDto);
//    }
}
