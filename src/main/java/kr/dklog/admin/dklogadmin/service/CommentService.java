package kr.dklog.admin.dklogadmin.service;

import kr.dklog.admin.dklogadmin.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public void removeCommentList(List<Long> commentIds){
        commentRepository.deleteAllById(commentIds);
    }

}