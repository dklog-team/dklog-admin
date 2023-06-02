package kr.dklog.admin.dklogadmin.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponsePostDto {

    private Long postId;

    private String title;

    private String previewContent;

    private String previewImage;

    private String contentHtml;

    private Integer views;

    private String createdDate;

    private String modifiedDate;

    private String username;

    private String picture;

    @Builder
    public ResponsePostDto(Long postId, String title, String previewContent, String previewImage, String contentHtml,Integer views, String createdDate, String modifiedDate, String username, String picture) {
        this.postId = postId;
        this.title = title;
        this.previewContent = previewContent;
        this.previewImage = previewImage;
        this.contentHtml = contentHtml;
        this.views = views;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.username = username;
        this.picture = picture;
    }
}
