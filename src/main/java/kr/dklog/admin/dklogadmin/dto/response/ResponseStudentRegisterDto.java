package kr.dklog.admin.dklogadmin.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseStudentRegisterDto {
    private Long savedId;

    @Builder
    public ResponseStudentRegisterDto(Long savedId) {
        this.savedId = savedId;
    }
}