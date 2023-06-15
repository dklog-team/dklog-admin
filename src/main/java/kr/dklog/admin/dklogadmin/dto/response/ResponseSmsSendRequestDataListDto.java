package kr.dklog.admin.dklogadmin.dto.response;

import kr.dklog.admin.dklogadmin.dto.common.ResponseListDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseSmsSendRequestDataListDto extends ResponseListDto {

    private List<ResponseSmsSendRequestDto> smsSendRequestDataList;
}
