package kr.dklog.admin.dklogadmin.controller;

import kr.dklog.admin.dklogadmin.dto.common.ResponseSavedIdDto;
import kr.dklog.admin.dklogadmin.dto.request.RequestLoginDto;
import kr.dklog.admin.dklogadmin.dto.request.RequestSignUpDto;
import kr.dklog.admin.dklogadmin.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseSavedIdDto> signUp(@RequestBody RequestSignUpDto requestDto) {
        ResponseSavedIdDto responseSavedIdDto = authService.signUp(requestDto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{adminId}")
                .buildAndExpand(responseSavedIdDto.getSavedId())
                .toUri();

        return ResponseEntity.created(location).body(responseSavedIdDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody RequestLoginDto requestDto) {
        authService.signIn(requestDto);

        return ResponseEntity.ok().build();
    }
}
