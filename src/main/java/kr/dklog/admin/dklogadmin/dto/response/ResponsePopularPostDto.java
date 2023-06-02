package kr.dklog.admin.dklogadmin.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponsePopularPostDto {

    private Long postId;

    private String title;

    private Integer views;

    private Integer commentCount;

    private Integer point;

    private String postUrl;

    @Builder
    public ResponsePopularPostDto(Long postId, String title, Integer views, Integer commentCount, Integer point, String postUrl) {
        this.postId = postId;
        this.title = title;
        this.views = views;
        this.commentCount = commentCount;
        this.point = point;
        this.postUrl = postUrl;
    }
}
