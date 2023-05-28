package kr.dklog.admin.dklogadmin.common.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ErrorResponseDto {
    private int status;
    private String error;
    private String message;

    @Builder
    public ErrorResponseDto(int status,String error, String message){
        this.status = status;
        this.error = error;
        this.message = message;
    }
}
