package lavatoryreservation.member.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lavatoryreservation.external.auth.JwtTokenProvider;
import lavatoryreservation.member.domain.Member;
import lavatoryreservation.member.dto.LoginDto;
import lavatoryreservation.member.dto.SignupDto;
import lavatoryreservation.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member/")
public class MemberController {

    private static final int ONE_HOUR = 3600;

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    public MemberController(MemberService memberService, JwtTokenProvider jwtTokenProvider) {
        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping
    public ResponseEntity<Long> addMember(@RequestBody SignupDto signupDto) {
        Long memberId = memberService.addMember(signupDto);
        return ResponseEntity.ok(memberId);
    }

    @GetMapping
    public ResponseEntity<Void> login(LoginDto loginDto, HttpServletResponse response) {
        Member member = memberService.getByEmail(loginDto);
        Cookie cookie = new Cookie(JwtTokenProvider.getCookieKey(), jwtTokenProvider.createToken(member));
        cookie.setMaxAge(ONE_HOUR);
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }
}
