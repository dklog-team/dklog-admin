package kr.dklog.admin.dklogadmin.dto.request;

import kr.dklog.admin.dklogadmin.dto.common.RequestListDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class RequestCommentListDto extends RequestListDto {
    private Long memberId;
    private Long studentId;
    private Long postId;
    private String name;
    private Integer semester;
    private String content;
    private String startDate;
    private String endDate;
}