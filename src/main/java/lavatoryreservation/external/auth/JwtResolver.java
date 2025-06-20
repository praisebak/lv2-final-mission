package lavatoryreservation.external.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import lavatoryreservation.exception.AuthenticationException;

public class JwtResolver {

    public static String getTokenFromCookie(HttpServletRequest request) {
        if (request == null || request.getCookies() == null) {
            throw new AuthenticationException("유효하지 않은 토큰입니다");
        }
        String jwtCookieKey = JwtTokenProvider.getCookieKey();

        return Arrays.stream(request.getCookies())
                .filter((cookie) -> jwtCookieKey.equals(cookie.getName()))
                .findAny()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationException("유효하지 않은 토큰입니다"));
    }
}
