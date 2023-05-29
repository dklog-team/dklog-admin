package kr.dklog.admin.dklogadmin.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import kr.dklog.admin.dklogadmin.common.data.AdminData;
import kr.dklog.admin.dklogadmin.config.AppConfig;
import kr.dklog.admin.dklogadmin.dto.common.ResponseSavedIdDto;
import kr.dklog.admin.dklogadmin.dto.request.RequestLoginDto;
import kr.dklog.admin.dklogadmin.dto.request.RequestSignUpDto;
import kr.dklog.admin.dklogadmin.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.crypto.SecretKey;
import java.net.URI;
import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AppConfig appConfig;

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
        Long adminId = authService.signIn(requestDto);

        log.info("jwt key: {}", appConfig.getJwtKey());

        SecretKey key = Keys.hmacShaKeyFor(appConfig.getJwtKey());

        String jws = Jwts.builder()
                .setSubject(String.valueOf(adminId))
                .signWith(key)
                .setIssuedAt(new Date())
                .compact();

        return ResponseEntity.ok(jws);
    }

    @GetMapping("/test")
    public ResponseEntity<?> test(AdminData adminData) {
        log.info("adminId: {}", adminData.getAdminId());
        return ResponseEntity.ok(adminData);
    }
}
