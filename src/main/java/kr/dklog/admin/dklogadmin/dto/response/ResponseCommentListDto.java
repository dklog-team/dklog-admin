package kr.dklog.admin.dklogadmin.dto.response;

import kr.dklog.admin.dklogadmin.dto.common.ResponseListDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@AllArgsConstructor
public class ResponseCommentListDto extends ResponseListDto {
    private List<ResponseCommentDto> commentList;
}
