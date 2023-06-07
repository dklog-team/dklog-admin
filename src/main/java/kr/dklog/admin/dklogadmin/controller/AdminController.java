package kr.dklog.admin.dklogadmin.controller;

import kr.dklog.admin.dklogadmin.common.exception.AdminNotFoundException;
import kr.dklog.admin.dklogadmin.entity.Admin;
import kr.dklog.admin.dklogadmin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminRepository adminRepository;

    @GetMapping("{adminId}")
    public ResponseEntity<String> getUsername(@PathVariable Long adminId) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(AdminNotFoundException::new);

        return ResponseEntity.ok(admin.getUsername());
    }
}
