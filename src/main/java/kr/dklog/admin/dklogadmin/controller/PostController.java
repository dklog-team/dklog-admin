package kr.dklog.admin.dklogadmin.controller;

import kr.dklog.admin.dklogadmin.common.data.AdminData;
import kr.dklog.admin.dklogadmin.dto.request.RequestPostDto;
import kr.dklog.admin.dklogadmin.dto.request.RequestPostDeleteDto;
import kr.dklog.admin.dklogadmin.dto.response.ResponsePopularPostListDto;
import kr.dklog.admin.dklogadmin.dto.response.ResponsePostListDto;
import kr.dklog.admin.dklogadmin.dto.response.ResponseRecentPostListDto;
import kr.dklog.admin.dklogadmin.service.PostService;
import kr.dklog.admin.dklogadmin.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    private final StatisticsService statisticsService;

    //insert하는 경우랑 겹치면 그때가서 uri 생각하기
    @PostMapping("/resources")
    public ResponseEntity postRemove(AdminData adminData, @RequestBody RequestPostDeleteDto requestPostDeleteDto) {
        postService.removePostList(requestPostDeleteDto.getPostIds());
        return ResponseEntity.status(204).build();
    }

    @GetMapping
    public ResponseEntity<ResponsePostListDto> postList(AdminData adminData, RequestPostDto requestPostDto) {
        ResponsePostListDto postList = postService.getAll(requestPostDto);
        return ResponseEntity.ok(postList);
    }

    @GetMapping("/popular")
    public ResponseEntity<ResponsePopularPostListDto> popular(AdminData adminData) {
        ResponsePopularPostListDto popularPostList = statisticsService.getPopularPostList();
        return ResponseEntity.ok(popularPostList);
    }

    @GetMapping("/recent")
    public ResponseEntity<ResponseRecentPostListDto> recent(AdminData adminData) {
        ResponseRecentPostListDto recentPostList = statisticsService.getRecentPostList();
        return ResponseEntity.ok(recentPostList);
    }
}
