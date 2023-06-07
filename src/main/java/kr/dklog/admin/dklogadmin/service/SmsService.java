package kr.dklog.admin.dklogadmin.service;

import kr.dklog.admin.dklogadmin.common.util.NcpSmsUtil;
import kr.dklog.admin.dklogadmin.common.util.PagingUtil;
import kr.dklog.admin.dklogadmin.dto.request.RequestSmsDataListDto;
import kr.dklog.admin.dklogadmin.dto.response.ResponseSmsResultDataDto;
import kr.dklog.admin.dklogadmin.dto.response.ResponseSmsSendRequestDataListDto;
import kr.dklog.admin.dklogadmin.dto.response.ResponseSmsSendRequestDto;
import kr.dklog.admin.dklogadmin.entity.SmsSendResponse;
import kr.dklog.admin.dklogadmin.repository.SmsSendResponseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SmsService {

    private final NcpSmsUtil ncpSmsUtil;

    private final SmsSendResponseRepository smsSendResponseRepository;

    public ResponseSmsSendRequestDataListDto getSmsDataList(RequestSmsDataListDto requestDto) {
        if(requestDto.getColumn() == null || "".equals(requestDto.getColumn())){
            requestDto.setColumn("smsSendResponseId");
        }
        PageRequest pageRequest = PageRequest.of(requestDto.getPage(), requestDto.getPageSize(), requestDto.getSortDirection(), requestDto.getColumn());
        Specification<SmsSendResponse> smsSendResponseSpecification = getSmsSendResponseSpecification(requestDto);
        Page<SmsSendResponse> smsSendResponseList = smsSendResponseRepository.findAll(smsSendResponseSpecification, pageRequest);
        PagingUtil pagingUtil = new PagingUtil(smsSendResponseList.getTotalElements(), smsSendResponseList.getTotalPages(), smsSendResponseList.getNumber(), smsSendResponseList.getSize());

        List<ResponseSmsSendRequestDto> responseSmsSendRequestDtoList = smsSendResponseList.stream().map(smsSendResponse -> {
            try {
                Map<String, Object> smsRequestData = ncpSmsUtil.getSmsRequestData(smsSendResponse.getRequestId());
                List<Object> messagesList = (List<Object>) smsRequestData.get("messages");
                Map<String, String> messages = (Map<String, String>) messagesList.get(0);
                ResponseSmsSendRequestDto responseSmsSendRequestDto = ResponseSmsSendRequestDto.builder()
                        .statusCode((String) smsRequestData.get("statusCode"))
                        .statusName((String) smsRequestData.get("statusName"))
                        .requestId(messages.get("requestId"))
                        .messageId(messages.get("messageId"))
                        .requestTime(messages.get("requestTime"))
                        .type(messages.get("type"))
                        .from(messages.get("from"))
                        .to(messages.get("to"))
                        .build();

                return responseSmsSendRequestDto;
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (InvalidKeyException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());

        ResponseSmsSendRequestDataListDto response = ResponseSmsSendRequestDataListDto.builder()
                .pagingUtil(pagingUtil)
                .smsSendRequestDataList(responseSmsSendRequestDtoList)
                .build();

        return response;
    }

    public ResponseSmsResultDataDto getSmsResultData(String messageId) {
        try {
            Map smsResultData = ncpSmsUtil.getSmsResultData(messageId);
            List<Object> messagesList = (List<Object>) smsResultData.get("messages");
            Map<String, String> messages = (Map<String, String>) messagesList.get(0);
            ResponseSmsResultDataDto responseSmsResultDataDto = ResponseSmsResultDataDto.builder()
                    .statusCode((String) smsResultData.get("statusCode"))
                    .statusName((String) smsResultData.get("statusName"))
                    .requestTime(messages.get("requestTime"))
                    .type(messages.get("type"))
                    .content(messages.get("content"))
                    .from(messages.get("from"))
                    .to(messages.get("to"))
                    .completeTime(messages.get("completeTime"))
                    .statusMessage(messages.get("statusMessage"))
                    .build();

            return responseSmsResultDataDto;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    private Specification<SmsSendResponse> getSmsSendResponseSpecification(RequestSmsDataListDto requestDto) {

        Specification<SmsSendResponse> specifications = Specification.where(null);

        log.info(requestDto.getStartDate());
        log.info(requestDto.getEndDate());

        if (Strings.isNotEmpty(requestDto.getStartDate()) && Strings.isNotEmpty(requestDto.getEndDate())) {
            DateTimeFormatter formatterAtLocalDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            specifications = specifications.and(
                    (root, query, criteriaBuilder) ->
                            criteriaBuilder.between(
                                    root.get("requestTime"),
                                    LocalDateTime.of(LocalDate.parse(requestDto.getStartDate(), formatterAtLocalDate), LocalTime.of(0, 0, 0)).toString(),
                                    LocalDateTime.of(LocalDate.parse(requestDto.getEndDate(), formatterAtLocalDate), LocalTime.of(23, 59, 59)).toString()
                            )
            );
        }

        return specifications;
    }
}
