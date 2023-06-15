package kr.dklog.admin.dklogadmin.dto.common;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseErrorDto {

    private String code;

    private String message;

    @Builder
    public ResponseErrorDto(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
