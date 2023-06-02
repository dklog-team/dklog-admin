package kr.dklog.admin.dklogadmin.entity;

import kr.dklog.admin.dklogadmin.dto.request.RequestStudentUpdateDto;
import kr.dklog.admin.dklogadmin.dto.response.ResponseNoAuthStudentDto;
import kr.dklog.admin.dklogadmin.dto.response.ResponseStudentDto;
import kr.dklog.admin.dklogadmin.dto.response.ResponseStudentRegisterDto;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Builder
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    private String name;

    private String phoneNumber;

    private Integer semester;

    private String authCode;

    private String githubUsername;

    @Builder.Default
    private String authStatus = "N";

    public ResponseStudentRegisterDto toResponseStudentRegisterDto(Student savedStudent) {
        return ResponseStudentRegisterDto.builder()
                .savedId(savedStudent.getStudentId())
                .build();
    }

    public ResponseStudentDto toResponseStudentDto(Student student) {
        return ResponseStudentDto.builder()
                .studentId(student.getStudentId())
                .name(student.getName())
                .phoneNumber(student.getPhoneNumber())
                .semester(student.getSemester())
                .authStatus(student.getAuthStatus())
                .build();
    }

    public void update(RequestStudentUpdateDto requestStudentUpdateDto) {
        this.name = requestStudentUpdateDto.getName();
        this.phoneNumber = requestStudentUpdateDto.getPhoneNumber();
        this.semester = requestStudentUpdateDto.getSemester();
        this.authStatus = requestStudentUpdateDto.getAuthStatus();
    }

    public ResponseNoAuthStudentDto toResponseNoAuthStudentDto() {
        return ResponseNoAuthStudentDto.builder()
                .semester(this.semester)
                .name(this.name)
                .build();
    }
}
