package kr.dklog.admin.dklogadmin.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
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

    @Builder
    public Post(Long postId, String title, String contentMd, String contentHtml, LocalDateTime createdDate, LocalDateTime modifiedDate, Member member) {
        this.postId = postId;
        this.title = title;
        this.contentMd = contentMd;
        this.contentHtml = contentHtml;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.member = member;
    }
}
