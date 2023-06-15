package kr.dklog.admin.dklogadmin.dto.common;

import kr.dklog.admin.dklogadmin.common.util.PagingUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class ResponseListDto {

    private PagingUtil pagingUtil;
}
