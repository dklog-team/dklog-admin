package kr.dklog.admin.dklogadmin.controller;

import kr.dklog.admin.dklogadmin.dto.request.RequestLoginDto;
import kr.dklog.admin.dklogadmin.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody RequestLoginDto requestDto) {
        authService.signIn(requestDto);

        return ResponseEntity.ok().build();
    }
}
