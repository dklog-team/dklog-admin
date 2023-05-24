package kr.dklog.admin.dklogadmin.service;

import kr.dklog.admin.dklogadmin.dto.request.RequestStudentRegisterDto;
import kr.dklog.admin.dklogadmin.dto.response.ResponseStudentRegisterDto;
import kr.dklog.admin.dklogadmin.entity.Student;
import kr.dklog.admin.dklogadmin.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}