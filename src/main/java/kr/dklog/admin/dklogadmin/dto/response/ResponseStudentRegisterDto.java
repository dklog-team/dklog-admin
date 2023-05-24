package kr.dklog.admin.dklogadmin.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseStudentRegisterDto {
    private Integer savedSemester;

    @Builder
    public ResponseStudentRegisterDto(Integer savedSemester) {
        this.savedSemester = savedSemester;
    }
}