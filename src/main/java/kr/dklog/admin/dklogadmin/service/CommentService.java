package kr.dklog.admin.dklogadmin.service;

import kr.dklog.admin.dklogadmin.dto.response.ResponseCommentDto;
import kr.dklog.admin.dklogadmin.entity.Comment;
import kr.dklog.admin.dklogadmin.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public List<ResponseCommentDto> getList(){
        List<Comment> commentList = commentRepository.findAll();

        List<ResponseCommentDto> comments= commentList.stream()
                .map(Comment::toResponseCommentDto)
                .collect(Collectors.toList());

        return comments;
    }

    @Transactional
    public void delete(List<Long> commentIds){
        commentRepository.deleteAllById(commentIds);
    }

}