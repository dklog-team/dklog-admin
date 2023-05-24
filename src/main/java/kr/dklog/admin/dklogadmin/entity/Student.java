package kr.dklog.admin.dklogadmin.entity;

import kr.dklog.admin.dklogadmin.dto.response.ResponseStudentRegisterDto;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

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
                .savedSemester(savedStudent.getSemester())
                .build();
    }
}
