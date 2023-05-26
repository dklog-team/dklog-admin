package kr.dklog.admin.dklogadmin.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestStudentRegisterDto {

    private String name;

    private String phoneNumber;

    private Integer semester;

    @Builder
    public RequestStudentRegisterDto(String name, String phoneNumber, Integer semester) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.semester = semester;
    }

}