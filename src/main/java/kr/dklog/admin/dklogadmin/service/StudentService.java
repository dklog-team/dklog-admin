package kr.dklog.admin.dklogadmin.service;

import kr.dklog.admin.dklogadmin.dto.request.RequestStudentRegisterDto;
import kr.dklog.admin.dklogadmin.dto.request.RequestStudentUpdateDto;
import kr.dklog.admin.dklogadmin.dto.response.ResponseStudentListDto;
import kr.dklog.admin.dklogadmin.dto.response.ResponseStudentRegisterDto;
import kr.dklog.admin.dklogadmin.entity.Student;
import kr.dklog.admin.dklogadmin.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public ResponseStudentRegisterDto register(RequestStudentRegisterDto registerDto){
        Student student = Student.builder()
                .name(registerDto.getName())
                .phoneNumber(registerDto.getPhoneNumber())
                .semester(registerDto.getSemester())
                .build();

        Student savedStudent = studentRepository.save(student);

        return student.toResponseStudentRegisterDto(savedStudent);
    }

    public ResponseStudentListDto getList() {
        List<Student> studentList = studentRepository.findAll();

        return ResponseStudentListDto.builder()
                .studentList(studentList.stream().map(student -> student.toResponseStudentDto(student))
                        .collect(Collectors.toList())).build();
    }

    public ResponseStudentListDto getListBySemester(int semester) {
        List<Student> studentList = studentRepository.getAllBySemester(semester);

        return ResponseStudentListDto.builder()
                .studentList(studentList.stream().map(student -> student.toResponseStudentDto(student))
                        .collect(Collectors.toList())).build();
    }

    @Transactional
    public void edit(Long studentId, RequestStudentUpdateDto requestStudentUpdateDto) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException());

        student.update(requestStudentUpdateDto);
    }
}