package kr.dklog.admin.dklogadmin.controller;

import kr.dklog.admin.dklogadmin.common.data.AdminData;
import kr.dklog.admin.dklogadmin.dto.request.RequestStudentDeleteDto;
import kr.dklog.admin.dklogadmin.dto.request.RequestStudentDto;
import kr.dklog.admin.dklogadmin.dto.request.RequestStudentRegisterDto;
import kr.dklog.admin.dklogadmin.dto.request.RequestStudentUpdateDto;
import kr.dklog.admin.dklogadmin.dto.response.ResponseStudentAuthDataDto;
import kr.dklog.admin.dklogadmin.dto.response.ResponseStudentListDto;
import kr.dklog.admin.dklogadmin.dto.response.ResponseStudentRegisterDto;
import kr.dklog.admin.dklogadmin.service.StatisticsService;
import kr.dklog.admin.dklogadmin.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    private final StatisticsService statisticsService;

    @PostMapping
    public ResponseEntity<ResponseStudentRegisterDto> registerStudent(AdminData adminData, @RequestBody RequestStudentRegisterDto registerDto) {
        ResponseStudentRegisterDto responseStudentRegisterDto = studentService.register(registerDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{studentId}")
                .buildAndExpand(responseStudentRegisterDto.getSavedId())
                .toUri();

        return ResponseEntity.created(location).body(responseStudentRegisterDto);
    }

    @GetMapping
    public ResponseEntity<ResponseStudentListDto> getStudentList(AdminData adminData, @ModelAttribute RequestStudentDto requestStudentDto) {
        ResponseStudentListDto responseStudentListDto = studentService.getList(requestStudentDto);

        return ResponseEntity.ok(responseStudentListDto);
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<?> updateStudent(AdminData adminData, @PathVariable Long studentId, @RequestBody RequestStudentUpdateDto requestStudentUpdateDto) {
        studentService.edit(studentId, requestStudentUpdateDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/resources")
    public ResponseEntity<?> deleteStudent(AdminData adminData, @RequestBody RequestStudentDeleteDto requestStudentDeleteDto) {
        studentService.remove(requestStudentDeleteDto);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/auth/ratio")
    public ResponseEntity<?> authRatio() {
        ResponseStudentAuthDataDto studentAuthData = statisticsService.getStudentAuthRatio();

        return ResponseEntity.ok(studentAuthData);
    }
}
