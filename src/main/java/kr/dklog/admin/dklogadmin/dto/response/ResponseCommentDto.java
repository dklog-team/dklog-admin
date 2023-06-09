package kr.dklog.admin.dklogadmin.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseCommentDto {
    private Long commentId;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Long memberId;
    private Long postId;
    private String studentName;
    private String picture;
    private Integer studentSemester;

    @Builder
    public ResponseCommentDto(Long commentId,String content, LocalDateTime createdDate, LocalDateTime modifiedDate,
                              Long memberId, Long postId, String studentName, String picture,Integer studentSemester){
        this.commentId = commentId;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.memberId = memberId;
        this.postId = postId;
        this.studentName = studentName;
        this.picture = picture;
        this.studentSemester =studentSemester;
    }

}