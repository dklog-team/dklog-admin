package kr.dklog.admin.dklogadmin.entity;

import kr.dklog.admin.dklogadmin.dto.response.ResponseCommentDto;
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
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private String content;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public Comment(Long commentId, String content, LocalDateTime createdDate, LocalDateTime modifiedDate, Member member, Post post) {
        this.commentId = commentId;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.member = member;
        this.post = post;
    }

    public static ResponseCommentDto toResponseCommentDto(Comment comment) {
        Member member = comment.getMember();

        return ResponseCommentDto.builder()
                .commentId(comment.getCommentId())
                .content(comment.getContent())
                .createdDate(comment.getCreatedDate())
                .modifiedDate(comment.getModifiedDate())
                .memberId(comment.getMember().getMemberId())
                .postId(comment.getPost().getPostId())
                .studentName(member.getStudent().getName())
                .picture(member.getPicture())
                .studentSemester(member.getStudent().getSemester())
                .build();

    }
}