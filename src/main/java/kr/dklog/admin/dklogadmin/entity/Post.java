package kr.dklog.admin.dklogadmin.entity;

import kr.dklog.admin.dklogadmin.common.util.DateFormatUtil;
import kr.dklog.admin.dklogadmin.dto.response.ResponsePostDto;
import kr.dklog.admin.dklogadmin.dto.response.ResponseRecentPostDto;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@ToString
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private String title;

    private String contentMd;

    private String contentHtml;

    @Column(columnDefinition = "TEXT")
    private String contentText;

    @Column(columnDefinition = "int default 0")
    private Integer views;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    @Builder
    public Post(Long postId, String title, String contentMd, String contentHtml, String contentText, Integer views, LocalDateTime createdDate, LocalDateTime modifiedDate, Member member) {
        this.postId = postId;
        this.title = title;
        this.contentMd = contentMd;
        this.contentHtml = contentHtml;
        this.contentText = contentText;
        this.views = views;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.member = member;
    }

    public static ResponsePostDto toResponsePostDto(Post post){
        ResponsePostDto responsePostDto = ResponsePostDto.builder()
                .postId(post.getPostId())
                .title(post.getTitle())
                .picture(post.getMember().getPicture())
                .username(post.getMember().getUsername())
                .createdDate(DateFormatUtil.toDateTime(post.getCreatedDate()))
                .preCreatedDate(post.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .modifiedDate(DateFormatUtil.toDateTime(post.getModifiedDate()))
                .previewContent(post.getContentText())
                .previewImage(post.getPreviewImage(post.getContentHtml()))
                .views(post.getViews())
                .contentHtml(post.getContentHtml())
                .build();
        return responsePostDto;
    }

    public String getPreviewImage(String contentHtml) {
        Pattern pattern = Pattern.compile("(<img src=\")(.*?)(\")");
        Matcher matcher = pattern.matcher(contentHtml);
        String imageSrc = null;
        if (matcher.find()) {
            imageSrc = matcher.group(2);
        }
        return imageSrc;
    }

    public ResponseRecentPostDto toResponseRecentPostDto(String postUrl) {
        return ResponseRecentPostDto.builder()
                .postId(this.postId)
                .title(this.title)
                .previewImage(getPreviewImage(this.contentHtml))
                .views(this.views)
                .createdDate(this.createdDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)))
                .username(this.member.getUsername())
                .picture(this.member.getPicture())
                .postUrl(postUrl)
                .commentCount(this.comments.size())
                .build();
    }
}
