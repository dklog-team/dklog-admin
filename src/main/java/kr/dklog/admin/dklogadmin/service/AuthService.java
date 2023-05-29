package kr.dklog.admin.dklogadmin.service;

import kr.dklog.admin.dklogadmin.common.crypto.SCryptPasswordEncoder;
import kr.dklog.admin.dklogadmin.dto.common.ResponseSavedIdDto;
import kr.dklog.admin.dklogadmin.dto.request.RequestLoginDto;
import kr.dklog.admin.dklogadmin.dto.request.RequestSignUpDto;
import kr.dklog.admin.dklogadmin.entity.Admin;
import kr.dklog.admin.dklogadmin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AdminRepository adminRepository;

    private final SCryptPasswordEncoder passwordEncoder;

    @Transactional
    public ResponseSavedIdDto signUp(RequestSignUpDto requestDto) {
        Optional<Admin> adminOptional = adminRepository.findByUsername(requestDto.getUsername());

        if (adminOptional.isPresent()) {
            throw new RuntimeException("이미 존재하는 아이디입니다");
        }

        String encryptedPassword = passwordEncoder.encrypt(requestDto.getPassword());

        Admin savedAdmin = adminRepository.save(Admin.builder()
                .username(requestDto.getUsername())
                .password(encryptedPassword)
                .build());

        return ResponseSavedIdDto.builder().savedId(savedAdmin.getAdminId()).build();
    }

    @Transactional
    public Long signIn(RequestLoginDto requestDto) {
        Admin admin = adminRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new RuntimeException("아이디가 올바르지 않습니다"));

        boolean matches = passwordEncoder.matches(requestDto.getPassword(), admin.getPassword());

        if (!matches) {
            throw new RuntimeException("비밀번호가 올바르지 않습니다");
        }

        return admin.getAdminId();
    }
}
