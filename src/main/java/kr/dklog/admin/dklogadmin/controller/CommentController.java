package kr.dklog.admin.dklogadmin.controller;

import kr.dklog.admin.dklogadmin.dto.request.RequestCommentDeleteDto;
import kr.dklog.admin.dklogadmin.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/comments")
@RestController
public class CommentController {
    private  final CommentService commentService;
    @PostMapping
    public ResponseEntity<?> commentRemove(@RequestBody RequestCommentDeleteDto requestCommentDeleteDto){
        commentService.removeCommentList(requestCommentDeleteDto.getCommentIds());
        return ResponseEntity.noContent().build();
    }
}