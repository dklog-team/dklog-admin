package kr.dklog.admin.dklogadmin.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseSmsSendRequestDto {

    private String statusCode;

    private String statusName;

    private String requestId;

    private String messageId;

    private String requestTime;

    private String type;

    private String from;

    private String to;

    @Builder
    public ResponseSmsSendRequestDto(String statusCode, String statusName, String requestId, String messageId, String requestTime, String type, String from, String to) {
        this.statusCode = statusCode;
        this.statusName = statusName;
        this.requestId = requestId;
        this.messageId = messageId;
        this.requestTime = requestTime;
        this.type = type;
        this.from = from;
        this.to = to;
    }
}
