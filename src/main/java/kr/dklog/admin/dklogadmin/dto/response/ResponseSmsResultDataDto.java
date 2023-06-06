package kr.dklog.admin.dklogadmin.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseSmsResultDataDto {

    private String statusCode;

    private String statusName;

    private String requestTime;

    private String type;

    private String content;

    private String from;

    private String to;

    private String completeTime;

    private String statusMessage;

    @Builder
    public ResponseSmsResultDataDto(String statusCode, String statusName, String requestTime, String type, String content, String from, String to, String completeTime, String statusMessage) {
        this.statusCode = statusCode;
        this.statusName = statusName;
        this.requestTime = requestTime;
        this.type = type;
        this.content = content;
        this.from = from;
        this.to = to;
        this.completeTime = completeTime;
        this.statusMessage = statusMessage;
    }
}
