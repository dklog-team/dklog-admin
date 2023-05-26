package kr.dklog.admin.dklogadmin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.dklog.admin.dklogadmin.dto.request.RequestStudentDeleteDto;
import kr.dklog.admin.dklogadmin.dto.request.RequestStudentRegisterDto;
import kr.dklog.admin.dklogadmin.entity.Student;
import kr.dklog.admin.dklogadmin.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    @Transactional
    void 학생등록() throws Exception {
        // given
        RequestStudentRegisterDto registerDto = RequestStudentRegisterDto.builder()
                .name("홍길동")
                .phoneNumber("010-1111-2222")
                .semester(1)
                .build();

        String requestJson = objectMapper.writeValueAsString(registerDto);

        // when, then
        mockMvc.perform(post("/students")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print());

    }

    @Test
    @Transactional
    void 학생삭제() throws Exception {
        // given
        Student savedStudent1 = studentRepository.save(Student.builder()
                .name("홍길동")
                .phoneNumber("01012341234")
                .semester(2)
                .build());

        Student savedStudent2 = studentRepository.save(Student.builder()
                .name("김춘삼")
                .phoneNumber("01011111234")
                .semester(2)
                .build());

        RequestStudentDeleteDto deleteDto = RequestStudentDeleteDto.builder()
                .studentIds(List.of(savedStudent1.getStudentId(), savedStudent2.getStudentId()))
                .build();

        String requestJson = objectMapper.writeValueAsString(deleteDto);

        // expected
        mockMvc.perform(post("/students/resources")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson)
                )
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(print());
    }
}
