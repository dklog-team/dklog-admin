package kr.dklog.admin.dklogadmin.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestCommentListDto {
    private Long memberId;
    private Long postId;
    private String name;
    private Integer semester;

    @Builder
    public RequestCommentListDto(Long memberId, Long postId,String name, Integer semester){
        this.memberId = memberId;
        this.postId = postId;
        this.name = name;
        this.semester = semester;
    }
}
