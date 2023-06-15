package kr.dklog.admin.dklogadmin.dto.common;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseCountDto {

    private Long count;

    @Builder
    public ResponseCountDto(Long count) {
        this.count = count;
    }
}
