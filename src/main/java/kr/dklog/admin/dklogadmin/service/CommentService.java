package kr.dklog.admin.dklogadmin.service;

import kr.dklog.admin.dklogadmin.entity.Comment;
import kr.dklog.admin.dklogadmin.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public void delete(Long id){
        Comment comment = commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 댓글이 존재하지 않습니다."+id));

        commentRepository.delete(comment);
    }

}
