package lavatoryreservation.member.controller;

import lavatoryreservation.member.domain.Member;
import lavatoryreservation.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member/")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<Long> addMember(Member member) {
        Long memberId = memberService.addMember(member);
        return ResponseEntity.ok(memberId);
    }
}
