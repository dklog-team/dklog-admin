package kr.dklog.admin.dklogadmin.dto.response;

import kr.dklog.admin.dklogadmin.dto.common.ResponseListDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class ResponseStudentListDto extends ResponseListDto {

    List<ResponseStudentDto> studentList;

}
