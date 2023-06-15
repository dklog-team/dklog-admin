package kr.dklog.admin.dklogadmin.dto.request;

import kr.dklog.admin.dklogadmin.dto.common.RequestListDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RequestPostDto extends RequestListDto {
    private String keyword;
    private String keywordType;
    private String startDate;
    private String endDate;
}
