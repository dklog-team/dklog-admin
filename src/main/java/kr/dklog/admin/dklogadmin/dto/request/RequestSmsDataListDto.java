package kr.dklog.admin.dklogadmin.dto.request;

import kr.dklog.admin.dklogadmin.dto.common.RequestListDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class RequestSmsDataListDto extends RequestListDto {

    private String startDate;

    private String endDate;

    @Builder
    public RequestSmsDataListDto(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
