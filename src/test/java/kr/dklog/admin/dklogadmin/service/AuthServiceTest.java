package kr.dklog.admin.dklogadmin.service;

import kr.dklog.admin.dklogadmin.common.crypto.SCryptPasswordEncoder;
import kr.dklog.admin.dklogadmin.dto.common.ResponseSavedIdDto;
import kr.dklog.admin.dklogadmin.dto.request.RequestSignUpDto;
import kr.dklog.admin.dklogadmin.entity.Admin;
import kr.dklog.admin.dklogadmin.repository.AdminRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private SCryptPasswordEncoder passwordEncoder;

    @Test
    @Transactional
    void 회원가입() {
        // given
        RequestSignUpDto requestSignUpDto = RequestSignUpDto.builder()
                .username("admin")
                .password("admin1!")
                .build();

        // when
        ResponseSavedIdDto responseSavedIdDto = authService.signUp(requestSignUpDto);

        // then
        Admin admin = adminRepository.findById(responseSavedIdDto.getSavedId())
                .orElseThrow(() -> new RuntimeException("관리자 계정이 존재하지 않습니다."));
        assertEquals("admin", admin.getUsername());
        assertTrue(passwordEncoder.matches("admin1!", admin.getPassword()));
    }
}