package kr.dklog.admin.dklogadmin.controller;

import kr.dklog.admin.dklogadmin.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class CommentController {
    private  final CommentService commentService;

    @DeleteMapping("/posts/{id}/comments/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        commentService.delete(id);
        return ResponseEntity.ok(id);
    }
}
