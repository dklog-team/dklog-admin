package kr.dklog.admin.dklogadmin.service;

import kr.dklog.admin.dklogadmin.common.crypto.SCryptPasswordEncoder;
import kr.dklog.admin.dklogadmin.common.exception.DuplicateAdminUsernameException;
import kr.dklog.admin.dklogadmin.common.exception.InvalidAdminPasswordException;
import kr.dklog.admin.dklogadmin.common.exception.InvalidAdminUsernameException;
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
            throw new DuplicateAdminUsernameException(  );
        }

        String encryptedPassword = passwordEncoder.encrypt(requestDto.getPassword());

        Admin savedAdmin = adminRepository.save(Admin.builder()
                .username(requestDto.getUsername())
                .password(encryptedPassword)
                .build());

        return ResponseSavedIdDto.builder().savedId(savedAdmin.getAdminId()).build();
    }

    public Long signIn(RequestLoginDto requestDto) {
        Admin admin = adminRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(InvalidAdminUsernameException::new);

        boolean matches = passwordEncoder.matches(requestDto.getPassword(), admin.getPassword());

        if (!matches) {
            throw new InvalidAdminPasswordException();
        }

        return admin.getAdminId();
    }
}
