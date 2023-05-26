package kr.dklog.admin.dklogadmin.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RequestStudentDeleteDto {
    private List<Long> studentIds;

    @Builder
    public RequestStudentDeleteDto(List<Long> studentIds) {
        this.studentIds = studentIds;
    }
}
