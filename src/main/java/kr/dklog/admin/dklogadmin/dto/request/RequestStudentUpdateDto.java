package kr.dklog.admin.dklogadmin.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class RequestStudentUpdateDto {
    private String name;

    private String phoneNumber;

    private Integer semester;

    private String authStatus;
    @Builder
    public RequestStudentUpdateDto(String name, String phoneNumber, Integer semester, String authStatus) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.semester = semester;
        this.authStatus = authStatus;
    }
}
