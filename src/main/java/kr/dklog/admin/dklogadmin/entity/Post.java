package kr.dklog.admin.dklogadmin.entity;

import kr.dklog.admin.dklogadmin.common.util.DateFormatUtil;
import kr.dklog.admin.dklogadmin.dto.response.ResponsePostDto;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private List<PostHashtag> postHashtags = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    @Builder
    public Post(Long postId, String title, String contentMd, String contentHtml, Integer views, LocalDateTime createdDate, LocalDateTime modifiedDate, Member member) {
        this.postId = postId;
        this.title = title;
        this.contentMd = contentMd;
        this.contentHtml = contentHtml;
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
                .username(post.getMember().getGithubUsername())
                .createdDate(DateFormatUtil.toDateTime(post.getCreatedDate()))
                .modifiedDate(DateFormatUtil.toDateTime(post.getModifiedDate()))
                .contentHtml(post.getContentHtml())
                .contentMd(post.getContentMd())
                .views(post.getViews())
                .build();
        return responsePostDto;
    }
}
