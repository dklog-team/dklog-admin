package kr.dklog.admin.dklogadmin.controller;

import kr.dklog.admin.dklogadmin.common.exception.DklogException;
import kr.dklog.admin.dklogadmin.dto.common.ResponseErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(DklogException.class)
    public ResponseEntity<ResponseErrorDto> dklogException(DklogException e) {
        int statusCode = e.getStatusCode();

        ResponseErrorDto body = ResponseErrorDto.builder()
                .code(String.valueOf(statusCode))
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(statusCode).body(body);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<ResponseErrorDto> dateTimeParseException(DateTimeParseException e) {
        int statusCode = 400;

        ResponseErrorDto body = ResponseErrorDto.builder()
                .code(String.valueOf(statusCode))
                .message("날짜 형식이 올바르지 않습니다.")
                .build();

        return ResponseEntity.status(statusCode).body(body);
    }
}
