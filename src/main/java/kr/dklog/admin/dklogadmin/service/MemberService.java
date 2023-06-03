package kr.dklog.admin.dklogadmin.service;

import kr.dklog.admin.dklogadmin.dto.response.ResponseMemberDto;
import kr.dklog.admin.dklogadmin.entity.Member;
import kr.dklog.admin.dklogadmin.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public ResponseMemberDto getOne(Long studentId) {
        Member member = memberRepository.findByStudentStudentId(studentId)
                .orElseThrow(RuntimeException::new);

        return member.toResponseMemberDto(member);
    }

    public void removeMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }
}
