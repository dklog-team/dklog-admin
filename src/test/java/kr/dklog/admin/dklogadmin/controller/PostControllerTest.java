package kr.dklog.admin.dklogadmin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.dklog.admin.dklogadmin.common.exception.PostNotFoundException;
import kr.dklog.admin.dklogadmin.dto.request.RequestPostDeleteDto;
import kr.dklog.admin.dklogadmin.entity.Post;
import kr.dklog.admin.dklogadmin.repository.PostRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostRepository postRepository;

    @Value("${test-token.taken}")
    private String token;

    @Test
    @Transactional
    @Order(1)
    void 게시글조회() throws Exception {
        mockMvc.perform(get("/posts")
                        .header("Authorization",token)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    @Order(2)
    void 특정컬럼내림차순정렬조회() throws Exception {
        mockMvc.perform(get("/posts")
                        .header("Authorization",token)
                        .param("column", "member.username")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    @Order(3)
    void 특정컬럼오름차순정렬조회() throws Exception {
        mockMvc.perform(get("/posts")
                        .header("Authorization",token)
                        .param("column", "member.username")
                        .param("sortDirection", "asc")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    @Order(4)
    void 있는페이지조회() throws Exception {
        mockMvc.perform(get("/posts")
                        .header("Authorization",token)
                        .param("page", "1")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    @Order(5)
    void 없는페이지조회() throws Exception {
        mockMvc.perform(get("/posts")
                        .header("Authorization",token)
                        .param("page", "100000000")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    @Order(6)
    void 있는검색조회() throws Exception {
        mockMvc.perform(get("/posts")
                        .header("Authorization",token)
                        .param("keyword", "test")
                        .param("keywordType","title")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    @Order(6)
    void 없는검색조회() throws Exception {
        mockMvc.perform(get("/posts")
                        .header("Authorization",token)
                        .param("keyword", "ㅂㅁㄵㄻㅈㄷㅀㄵㄷㅎㄱㄴㅇㅁㅈㅇㅁㄻㄴㅇㅈㅁㅇ")
                        .param("keywordType","contentHtml")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }
    
    @Test
    @Transactional
    @Order(7)
    void 날짜정렬조회() throws Exception {
        mockMvc.perform(get("/posts")
                        .header("Authorization",token)
                        .param("startDate", "2023-05-10")
                        .param("endDate","2023-05-10")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }
    
    @Test
    @Transactional
    @Order(8)
    void 있는게시글삭제() throws Exception {
        RequestPostDeleteDto requestPostDeleteDto = new RequestPostDeleteDto();
        List<Post> postList = postRepository.findTop4ByOrderByPostIdDesc();
        requestPostDeleteDto.setPostIds(postList.stream().map(x -> x.getPostId()).collect(Collectors.toList()));
        String requestJson = objectMapper.writeValueAsString(requestPostDeleteDto);
        mockMvc.perform(post("/posts/resources")
                        .header("Authorization", token)
                        .contentType(APPLICATION_JSON)
                        .content(requestJson)
                )
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(print());
    }

    @Test
    @Transactional
    @Order(9)
    void 없는게시글삭제() throws Exception {
        RequestPostDeleteDto requestPostDeleteDto = new RequestPostDeleteDto();
        requestPostDeleteDto.setPostIds(List.of(10000000000000000L));
        String requestJson = objectMapper.writeValueAsString(requestPostDeleteDto);
        mockMvc.perform(post("/posts/resources")
                        .header("Authorization", token)
                        .contentType(APPLICATION_JSON)
                        .content(requestJson)
                )
                .andExpect(result -> Assertions.assertEquals(PostNotFoundException.class.getCanonicalName(), result.getResolvedException().getClass().getCanonicalName()))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(print());
    }
}
