package kr.dklog.admin.dklogadmin.service;

import kr.dklog.admin.dklogadmin.dto.request.RequestLoginDto;
import kr.dklog.admin.dklogadmin.entity.Admin;
import kr.dklog.admin.dklogadmin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AdminRepository adminRepository;

    @Transactional
    public Long signIn(RequestLoginDto requestDto) {
        Admin admin = adminRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new RuntimeException("아이디가 올바르지 않습니다"));

        return admin.getAdminId();
    }
}
