package kr.dklog.admin.dklogadmin.controller;

import kr.dklog.admin.dklogadmin.dto.response.ResponseSmsSendResponseDto;
import kr.dklog.admin.dklogadmin.service.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/sms")
public class SmsController {

    private final SmsService smsService;

    @GetMapping("/data")
    public ResponseEntity<?> data() {
        try {
            List<ResponseSmsSendResponseDto> response = smsService.getSmsData();
            return ResponseEntity.ok(response);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}
