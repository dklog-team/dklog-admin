package kr.dklog.admin.dklogadmin.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestStudentDto {
    private String name;

    private Integer semester;

    private String sortDirection;

    @Builder
    public RequestStudentDto(String name, Integer semester, String sortDirection) {
        this.name = name;
        this.semester = semester;
        this.sortDirection = sortDirection;
    }
}
