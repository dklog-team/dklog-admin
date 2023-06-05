package kr.dklog.admin.dklogadmin.service;

import kr.dklog.admin.dklogadmin.common.util.NcpSmsUtil;
import kr.dklog.admin.dklogadmin.dto.response.ResponseSmsSendResponseDto;
import kr.dklog.admin.dklogadmin.entity.SmsSendResponse;
import kr.dklog.admin.dklogadmin.repository.SmsSendResponseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SmsService {

    private final NcpSmsUtil ncpSmsUtil;

    private final SmsSendResponseRepository smsSendResponseRepository;

    public List<ResponseSmsSendResponseDto> getSmsData() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        List<SmsSendResponse> smsSendResponseList = smsSendResponseRepository.findAllByOrderByRequestId();

        List<ResponseSmsSendResponseDto> responseSmsSendResponseDtoList = smsSendResponseList.stream().map(smsSendResponse -> {
            try {
                Map<String, Object> smsRequestData = ncpSmsUtil.getSmsRequestData(smsSendResponse.getRequestId());
                List<Object> messagesList = (List<Object>) smsRequestData.get("messages");
                Map<String, String> messages = (Map<String, String>) messagesList.get(0);
                ResponseSmsSendResponseDto responseSmsSendResponseDto = ResponseSmsSendResponseDto.builder()
                        .statusCode((String) smsRequestData.get("statusCode"))
                        .statusName((String) smsRequestData.get("statusName"))
                        .requestId(messages.get("requestId"))
                        .messageId(messages.get("messageId"))
                        .requestTime(messages.get("requestTime"))
                        .type(messages.get("type"))
                        .from(messages.get("from"))
                        .to(messages.get("to"))
                        .build();

                return responseSmsSendResponseDto;
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (InvalidKeyException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
//        Map smsRequestData = ncpSmsUtil.getSmsRequestData();
        return responseSmsSendResponseDtoList;
    }
}
