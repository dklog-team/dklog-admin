package kr.dklog.admin.dklogadmin.service;

import kr.dklog.admin.dklogadmin.dto.response.ResponseMemberDto;
import kr.dklog.admin.dklogadmin.entity.Member;
import kr.dklog.admin.dklogadmin.repository.MemberRepository;
import kr.dklog.admin.dklogadmin.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final StudentRepository studentRepository;

    public ResponseMemberDto getOne(Long studentId) {
        Member member = memberRepository.findByStudentStudentId(studentId)
                .orElseThrow(RuntimeException::new);

        return member.toResponseMemberDto(member);
    }

    @Transactional
    public void removeMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(RuntimeException::new);

        member.getStudent().changeAuthStatus("N");

        member.changeStudentIdNull();

        memberRepository.delete(member);

    }
}
