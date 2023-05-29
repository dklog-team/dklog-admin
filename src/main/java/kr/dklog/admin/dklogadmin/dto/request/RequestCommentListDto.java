package kr.dklog.admin.dklogadmin.dto.request;

import kr.dklog.admin.dklogadmin.dto.common.RequestListDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestCommentListDto extends RequestListDto {
    private Long memberId;
    private Long studentId;
    private Long postId;
    private String name;
    private Integer semester;
    private String content;

}
