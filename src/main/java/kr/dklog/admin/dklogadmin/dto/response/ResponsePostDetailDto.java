package kr.dklog.admin.dklogadmin.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponsePostDetailDto {
    private String title;
    private String contentHtml;
    private String createdDate;
    private String modifiedDate;
    private Integer views;
    private String username;
    private String picture;

    @Builder
    public ResponsePostDetailDto(String title, String contentHtml, String createdDate, String modifiedDate, Integer views, String username, String picture) {
        this.title = title;
        this.contentHtml = contentHtml;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.views = views;
        this.username = username;
        this.picture = picture;
    }
}
