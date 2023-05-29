package kr.dklog.admin.dklogadmin.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestSignUpDto {

    private String username;

    private String password;

    @Builder
    public RequestSignUpDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
