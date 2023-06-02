package kr.dklog.admin.dklogadmin.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseNoAuthStudentDto {

    private Integer semester;

    private String name;

    @Builder
    public ResponseNoAuthStudentDto(Integer semester, String name) {
        this.semester = semester;
        this.name = name;
    }
}
