package kr.dklog.admin.dklogadmin.controller;

import kr.dklog.admin.dklogadmin.dto.request.RequestSmsDataListDto;
import kr.dklog.admin.dklogadmin.dto.response.ResponseSmsResultDataDto;
import kr.dklog.admin.dklogadmin.dto.response.ResponseSmsSendRequestDataListDto;
import kr.dklog.admin.dklogadmin.service.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/sms")
public class SmsController {

    private final SmsService smsService;

    @GetMapping("/request/data")
    public ResponseEntity<ResponseSmsSendRequestDataListDto> data(@ModelAttribute RequestSmsDataListDto requestDto) {
        ResponseSmsSendRequestDataListDto response = smsService.getSmsDataList(requestDto);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/result/data/{messageId}")
    public ResponseEntity<?> resultData(@PathVariable String messageId) {
        ResponseSmsResultDataDto response = smsService.getSmsResultData(messageId);

        return ResponseEntity.ok(response);
    }
}
