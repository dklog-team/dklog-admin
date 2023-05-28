package kr.dklog.admin.dklogadmin.controller;

import kr.dklog.admin.dklogadmin.dto.request.RequestCommentDeleteDto;
import kr.dklog.admin.dklogadmin.dto.request.RequestCommentListDto;
import kr.dklog.admin.dklogadmin.dto.response.ResponseCommentDto;
import kr.dklog.admin.dklogadmin.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/comments")
@RestController
public class CommentController {
    private  final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<ResponseCommentDto>> getCommentList(@RequestBody RequestCommentListDto requestCommentListDto){
        List<ResponseCommentDto> commentDtoList = commentService.getList(requestCommentListDto);
        return ResponseEntity.ok(commentDtoList);
    }
    @PostMapping
    public ResponseEntity<?> commentRemove(@RequestBody RequestCommentDeleteDto requestCommentDeleteDto){
        commentService.removeCommentList(requestCommentDeleteDto.getCommentIds());
        return ResponseEntity.noContent().build();
    }
}