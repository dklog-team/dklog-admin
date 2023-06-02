package kr.dklog.admin.dklogadmin.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseStudentAuthDataDto {

    private List<ResponseNoAuthStudentDto> noAuthStudentList;

    private Long totalCount;

    private Long yesCount;

    private Long noCount;

    private Double percent;

    @Builder
    public ResponseStudentAuthDataDto(List<ResponseNoAuthStudentDto> noAuthStudentList, Long totalCount, Long yesCount, Long noCount, Double percent) {
        this.noAuthStudentList = noAuthStudentList;
        this.totalCount = totalCount;
        this.yesCount = yesCount;
        this.noCount = noCount;
        this.percent = percent;
    }
}
