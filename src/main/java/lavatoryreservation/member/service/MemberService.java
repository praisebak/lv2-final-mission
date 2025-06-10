package lavatoryreservation.member.service;

import java.util.Optional;
import lavatoryreservation.exception.MemberException;
import lavatoryreservation.member.domain.Member;
import lavatoryreservation.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void addMember(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        Optional<Member> existMember = memberRepository.findByEmail(member.getEmail());
        if (existMember.isPresent()) {
            throw new MemberException("중복된 이메일을 가진 유저가 존재합니다");
        }
    }
}
