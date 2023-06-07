package kr.dklog.admin.dklogadmin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.dklog.admin.dklogadmin.common.exception.MemberNotFoundException;
import kr.dklog.admin.dklogadmin.entity.Member;
import kr.dklog.admin.dklogadmin.entity.Student;
import kr.dklog.admin.dklogadmin.repository.MemberRepository;
import kr.dklog.admin.dklogadmin.repository.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private StudentRepository studentRepository;

    @Value("${test-token.token}")
    private String token;

    @Test
    @Transactional
    void 회원조회() throws Exception {
        // given
        Student student = Student.builder()
                .name("홍길동")
                .phoneNumber("01012341234")
                .semester(4)
                .build();

        Student savedStudent = studentRepository.save(student);

        Member member = Member.builder()
                .githubUsername("test")
                .username("testMember")
                .student(savedStudent)
                .build();

        Member savedMember = memberRepository.save(member);

        // expected
        mockMvc.perform(get("/members/{studentId}", savedMember.getStudent().getStudentId())
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    void 회원조회실패() throws Exception {
        // given
        Student student = Student.builder()
                .name("홍길동")
                .phoneNumber("01012341234")
                .semester(4)
                .build();

        Student savedStudent = studentRepository.save(student);

        Member member = Member.builder()
                .githubUsername("test")
                .username("testMember")
                .student(savedStudent)
                .build();

        Member savedMember = memberRepository.save(member);

        //expected
        mockMvc.perform(get("/members/{studentId}", savedMember.getStudent().getStudentId() + 1L)
                        .header("Authorization", token))
                .andExpect(result -> {
                    System.out.println("================================");
                    System.out.println(result.getResolvedException().toString());
                    System.out.println("================================");
                    Assertions.assertEquals(MemberNotFoundException.class.getCanonicalName(), result.getResolvedException().getClass().getCanonicalName());
                    Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
                })
                .andDo(print());

    }

    @Test
    @Transactional
    void 회원삭제() throws Exception {
        //given
        Student student = Student.builder()
                .name("홍길동")
                .phoneNumber("01012341234")
                .semester(4)
                .build();

        Student savedStudent = studentRepository.save(student);

        Member member = Member.builder()
                .githubUsername("test")
                .username("testMember")
                .student(savedStudent)
                .build();

        Member savedMember = memberRepository.save(member);

        String requestJson = objectMapper.writeValueAsString(savedMember.getMemberId());

        //expected
        mockMvc.perform(post("/members/resources")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @Transactional
    void 회원삭제실패() throws Exception {
        //given
        Student student = Student.builder()
                .name("홍길동")
                .phoneNumber("01012341234")
                .semester(4)
                .build();

        Student savedStudent = studentRepository.save(student);

        Member member = Member.builder()
                .githubUsername("test")
                .username("testMember")
                .student(savedStudent)
                .build();

        Member savedMember = memberRepository.save(member);

        String requestJson = objectMapper.writeValueAsString(savedMember.getMemberId() + 1L);

        //expected
        mockMvc.perform(post("/members/resources")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(result -> {
                    System.out.println("===============================================");
                    System.out.println(result.getResolvedException().toString());
                    System.out.println("===============================================");
                    Assertions.assertEquals(MemberNotFoundException.class.getCanonicalName(), result.getResolvedException().getClass().getCanonicalName());
                    Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), result.getResponse().getStatus());
                })
                .andDo(print());
    }
}

