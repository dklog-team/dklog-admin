package kr.dklog.admin.dklogadmin.controller;

import kr.dklog.admin.dklogadmin.dto.request.RequestCommentDeleteDto;
import kr.dklog.admin.dklogadmin.dto.request.RequestCommentListDto;
import kr.dklog.admin.dklogadmin.dto.response.ResponseCommentListDto;
import kr.dklog.admin.dklogadmin.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/comments")
@RestController
public class CommentController {
    private  final CommentService commentService;

    @GetMapping
    public ResponseEntity<ResponseCommentListDto> getCommentList(RequestCommentListDto requestCommentListDto){
        ResponseCommentListDto responseCommentListDto = commentService.getList(requestCommentListDto);
        return ResponseEntity.ok(responseCommentListDto);
    }
    @PostMapping("/resources")
    public ResponseEntity<?> commentRemove(@RequestBody RequestCommentDeleteDto requestCommentDeleteDto){
        commentService.removeCommentList(requestCommentDeleteDto.getCommentIds());
        return ResponseEntity.noContent().build();
    }
}