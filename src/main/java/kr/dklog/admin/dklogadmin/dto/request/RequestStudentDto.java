package kr.dklog.admin.dklogadmin.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestStudentDto {
    private String name;

    private Integer semester;

    @Builder
    public RequestStudentDto(String name, Integer semester) {
        this.name = name;
        this.semester = semester;
    }
}
