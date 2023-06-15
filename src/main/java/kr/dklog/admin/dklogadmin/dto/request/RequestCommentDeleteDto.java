package kr.dklog.admin.dklogadmin.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestCommentDeleteDto {
    private List<Long> commentIds;

    @Builder
    public RequestCommentDeleteDto(List<Long> commentIds) { this.commentIds = commentIds; }
}