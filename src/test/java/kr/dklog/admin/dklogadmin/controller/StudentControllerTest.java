package kr.dklog.admin.dklogadmin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.dklog.admin.dklogadmin.dto.common.RequestListDto;
import kr.dklog.admin.dklogadmin.dto.request.RequestStudentDeleteDto;
import kr.dklog.admin.dklogadmin.dto.request.RequestStudentDto;
import kr.dklog.admin.dklogadmin.dto.request.RequestStudentRegisterDto;
import kr.dklog.admin.dklogadmin.entity.Student;
import kr.dklog.admin.dklogadmin.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
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

    @Test
    void 학생전체조회_최신순() throws Exception {
        // given
        RequestStudentDto requestStudentDto = RequestStudentDto.builder().build();

        // expected
        mockMvc.perform(get("/students"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    void 학생전체조회_등록순() throws Exception {
        // given
        RequestStudentDto requestStudentDto = RequestStudentDto.builder()
                .sortDirection(Sort.Direction.ASC)
                .build();

        String direction = String.valueOf(requestStudentDto.getSortDirection());
        String column = requestStudentDto.getColumn();

        // expected
        mockMvc.perform(get("/students").param("sortDirection", direction)
                        .param("Column", column))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    void 학생기수별조회_AZ순() throws Exception {
        // given
        RequestStudentDto requestStudentDto = RequestStudentDto.builder()
                .semester(1)
                .column("name")
                .build();

        String semester = String.valueOf(requestStudentDto.getSemester());
        String column = requestStudentDto.getColumn();

        // expected
        mockMvc.perform(get("/students").param("semester", semester)
                        .param("column", column))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    void 학생기수별조회_ZA순() throws Exception {
        // given
        RequestStudentDto requestStudentDto = RequestStudentDto.builder()
                .semester(1)
                .sortDirection(Sort.Direction.DESC)
                .column("name")
                .build();

        String semester = String.valueOf(requestStudentDto.getSemester());
        String sortDirection = String.valueOf(requestStudentDto.getSortDirection());
        String column = requestStudentDto.getColumn();

        // expected
        mockMvc.perform(get("/students")
                        .param("semester", semester)
                        .param("sortDirection", sortDirection)
                        .param("column", column))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    void 학생이름검색() throws Exception {
        // given
        RequestStudentDto requestStudentDto = RequestStudentDto.builder()
                .name("홍길동")
                .build();

        String name = requestStudentDto.getName();

        // expected
        mockMvc.perform(get("/students")
                        .param("name", name))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    void 학생이름과기수검색() throws Exception {
        // given
        RequestStudentDto requestStudentDto = RequestStudentDto.builder()
                .name("홍길동")
                .semester(2)
                .build();

        String name = requestStudentDto.getName();
        String semester = String.valueOf(requestStudentDto.getSemester());

        // expected
        mockMvc.perform(get("/students")
                        .param("name", name)
                        .param("semester", semester))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }
}
