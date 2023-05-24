package kr.dklog.admin.dklogadmin.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponsePostDto {

    private Long postId;

    private String title;

    private String contentMd;

    private String contentHtml;

    private Integer views;

    private String createdDate;

    private String modifiedDate;

    private String username;

    private String picture;

    @Builder
    public ResponsePostDto(Long postId, String title, String contentMd, String contentHtml, Integer views, String createdDate, String modifiedDate, String username, String picture) {
        this.postId = postId;
        this.title = title;
        this.contentMd = contentMd;
        this.contentHtml = contentHtml;
        this.views = views;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.username = username;
        this.picture = picture;
    }
}
