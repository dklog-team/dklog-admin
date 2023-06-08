package kr.dklog.admin.dklogadmin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.dklog.admin.dklogadmin.dto.common.RequestListDto;
import kr.dklog.admin.dklogadmin.dto.request.RequestCommentDeleteDto;
import kr.dklog.admin.dklogadmin.dto.request.RequestCommentListDto;
import kr.dklog.admin.dklogadmin.entity.Comment;
import kr.dklog.admin.dklogadmin.repository.CommentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CommentRepository commentRepository;

    @Value("${test-token.token}")
    private String token;

    @Test
    void 댓글리스트조회_최신순() throws Exception {
        RequestListDto requestCommentListDto = RequestCommentListDto.builder().build();

        mockMvc.perform(get("/comments")
                        .header("Authorization", token)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    void 댓글리스트조회_오래된순() throws Exception {
        RequestListDto requestCommentListDto = RequestCommentListDto.builder()
                .sortDirection(Sort.Direction.ASC)
                .build();

        String direction = String.valueOf(requestCommentListDto.getSortDirection());
        String column = requestCommentListDto.getColumn();

        mockMvc.perform(get("/comments").param("sortDirection", direction)
                        .header("Authorization", token)
                        .param("column", column))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    void 댓글리스트이름별_ASC() throws Exception {
        RequestListDto requestCommentListDto = RequestCommentListDto.builder()
                .sortDirection(Sort.Direction.ASC)
                .column("member.student.name")
                .build();

        String sortDirection = String.valueOf(requestCommentListDto.getSortDirection());
        String column = requestCommentListDto.getColumn();

        mockMvc.perform(get("/comments")
                        .header("Authorization", token)
                        .param("sortDirection", sortDirection)
                        .param("column", column))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    void 댓글리스트이름별_DESC() throws Exception {
        RequestListDto requestCommentListDto = RequestCommentListDto.builder()
                .sortDirection(Sort.Direction.DESC)
                .column("member.student.name")
                .build();

        String sortDirection = String.valueOf(requestCommentListDto.getSortDirection());
        String column = requestCommentListDto.getColumn();

        mockMvc.perform(get("/comments")
                        .header("Authorization", token)
                        .param("sortDirection", sortDirection)
                        .param("column", column))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    void 댓글리스트_기수별() throws Exception {
        RequestCommentListDto requestCommentListDto = RequestCommentListDto.builder()
                .semester(1)
                .build();

        String semester = String.valueOf(requestCommentListDto.getSemester());

        mockMvc.perform(get("/comments")
                        .header("Authorization", token)
                        .param("semester", semester))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    void 댓글작성자검색() throws Exception {
        RequestCommentListDto requestCommentListDto = RequestCommentListDto.builder()
                .name("구인영")
                .build();

        String name = requestCommentListDto.getName();

        mockMvc.perform(get("/comments")
                        .header("Authorization", token)
                        .param("name", name))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    void 댓글내용검색() throws Exception {
        RequestCommentListDto requestCommentListDto = RequestCommentListDto.builder()
                .content("test")
                .build();

        String content = requestCommentListDto.getContent();

        mockMvc.perform(get("/comments")
                        .header("Authorization", token)
                        .param("content", content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    void 댓글삭제() throws Exception {
        Comment commentTest1 = commentRepository.save(Comment.builder()
                .commentId(1L)
                .content("삭제 테스트~")
                .build());

        Comment commentTest2 = commentRepository.save(Comment.builder()
                .commentId(1L)
                .content("삭제 테스트 2222~")
                .build());

        RequestCommentDeleteDto requestCommentDeleteDto = RequestCommentDeleteDto.builder()
                .commentIds(List.of(commentTest1.getCommentId(), commentTest2.getCommentId()))
                .build();

        String requestJson = objectMapper.writeValueAsString(requestCommentDeleteDto);

        mockMvc.perform(post("/comments/resources")
                        .header("Authorization", token)
                        .contentType(APPLICATION_JSON)
                        .content(requestJson)
                )
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(print());
    }

    @Test
    void 게시글삭제_실패() throws Exception{

        Comment commentTest1 = commentRepository.save(Comment.builder()
                .commentId(1L)
                .content("삭제 테스트~")
                .build());

        Comment commentTest2 = commentRepository.save(Comment.builder()
                .commentId(1L)
                .content("삭제 테스트 2222~")
                .build());

        RequestCommentDeleteDto requestCommentDeleteDto = RequestCommentDeleteDto.builder()
                .commentIds(List.of(commentTest1.getCommentId() +1L, commentTest2.getCommentId() +1L))
                .build();

        String requestJson = objectMapper.writeValueAsString(requestCommentDeleteDto);

        mockMvc.perform(post("/comments/resources")
                .header("Authorization",token)
                .contentType(APPLICATION_JSON)
                .content(requestJson))
                .andExpect((result -> {
                    System.out.println("=========================================");
                    System.out.println(result.getResolvedException().toString());
                    System.out.println("=========================================");
                    Assertions.assertEquals(HttpStatus.NOT_FOUND.value(),result.getResponse().getStatus());
                }))
                .andDo(print());
    }

}
