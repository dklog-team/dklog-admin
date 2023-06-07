package kr.dklog.admin.dklogadmin.controller;

import kr.dklog.admin.dklogadmin.common.data.AdminData;
import kr.dklog.admin.dklogadmin.dto.response.ResponseMemberDto;
import kr.dklog.admin.dklogadmin.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/{studentId}")
    public ResponseEntity<ResponseMemberDto> getMember(AdminData adminData, @PathVariable Long studentId) {
        ResponseMemberDto responseMemberDto = memberService.getOne(studentId);

        return ResponseEntity.ok(responseMemberDto);
    }

    @PostMapping("/resources")
    public ResponseEntity<?> deleteMember(AdminData adminData, @RequestBody Long memberId) {
        memberService.removeMember(memberId);

        return ResponseEntity.noContent().build();
    }
}
