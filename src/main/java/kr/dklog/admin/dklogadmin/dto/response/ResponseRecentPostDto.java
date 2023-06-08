package kr.dklog.admin.dklogadmin.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseRecentPostDto {

    private Long postId;

    private String title;

    private String previewImage;

    private Integer views;

    private String createdDate;

    private String username;

    private String picture;

    private String postUrl;

    private Integer commentCount;

    @Builder
    public ResponseRecentPostDto(Long postId, String title, String previewImage, Integer views, String createdDate, String username, String picture, String postUrl, Integer commentCount) {
        this.postId = postId;
        this.title = title;
        this.previewImage = previewImage;
        this.views = views;
        this.createdDate = createdDate;
        this.username = username;
        this.picture = picture;
        this.postUrl = postUrl;
        this.commentCount = commentCount;
    }
}
