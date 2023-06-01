package kr.dklog.admin.dklogadmin.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseVisitorStatisticsDto {

    private List<String> dateList;

    private List<Long> countList;

    @Builder
    public ResponseVisitorStatisticsDto(List<String> dateList, List<Long> countList) {
        this.dateList = dateList;
        this.countList = countList;
    }
}
