package kr.dklog.admin.dklogadmin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.dklog.admin.dklogadmin.entity.Member;
import kr.dklog.admin.dklogadmin.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Transactional
    void 회원조회() throws Exception {
        // given
        Long studentId = 1L;

        // expected
        mockMvc.perform(get("/members/" + studentId))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    void 회원삭제() throws Exception {
        //given
        Member testMember = memberRepository.save(Member.builder()
                .githubUsername("test1234")
                .username("testUser")
                .build());

        Long savedId = testMember.getMemberId();

        String requestJson = objectMapper.writeValueAsString(savedId);

        //expected
        mockMvc.perform(post("/members/resources")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}

