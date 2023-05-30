package kr.dklog.admin.dklogadmin.dto.request;

import kr.dklog.admin.dklogadmin.dto.common.RequestListDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class RequestStudentDto extends RequestListDto {
    private String name;

    private Integer semester;
}
