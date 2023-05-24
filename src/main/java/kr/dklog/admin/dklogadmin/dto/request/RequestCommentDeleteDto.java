package kr.dklog.admin.dklogadmin.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class RequestCommentDeleteDto {
    private List<Long> commentIds;
}