package kr.dklog.admin.dklogadmin.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseStudentDto {

    private Long studentId;

    private String name;

    private String phoneNumber;

    private Integer semester;

    private String authStatus;

    @Builder
    public ResponseStudentDto(Long studentId, String name, String phoneNumber, Integer semester, String authStatus) {
        this.studentId = studentId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.semester = semester;
        this.authStatus = authStatus;
    }
}
