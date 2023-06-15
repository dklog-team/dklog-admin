package kr.dklog.admin.dklogadmin.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseMemberDto {

    private Long memberId;

    private String githubUsername;

    private String email;

    private String picture;

    private String role;

    private String username;

    @Builder
    public ResponseMemberDto(Long memberId, String githubUsername, String email, String picture, String role, String username) {
        this.memberId = memberId;
        this.githubUsername = githubUsername;
        this.email = email;
        this.picture = picture;
        this.role = role;
        this.username = username;
    }
}
