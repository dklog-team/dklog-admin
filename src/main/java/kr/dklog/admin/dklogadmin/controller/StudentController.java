package kr.dklog.admin.dklogadmin.controller;

import kr.dklog.admin.dklogadmin.dto.request.RequestStudentRegisterDto;
import kr.dklog.admin.dklogadmin.dto.response.ResponseStudentRegisterDto;
import kr.dklog.admin.dklogadmin.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<ResponseStudentRegisterDto> registerStudent(@RequestBody RequestStudentRegisterDto registerDto){
        ResponseStudentRegisterDto responseStudentRegisterDto = studentService.register(registerDto);


        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{semester}")
                .buildAndExpand(responseStudentRegisterDto.getSavedSemester())
                .toUri();

        return ResponseEntity.created(location).body(responseStudentRegisterDto);
    }

}
