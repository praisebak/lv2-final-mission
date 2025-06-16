package lavatoryreservation.auth;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.restassured.RestAssured;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AuthTest extends IntegratedRestAssuredTest {

    @Test
    void 유저는_회원가입_할_수_있다() {
        Map<String, String> signupDto = new HashMap<>();
        signupDto.put("email", "prasebak@naver.com");
        signupDto.put("sex", "남자");
        signupDto.put("name", "prasebak");
        int statusCode = RestAssured
                .given()
                .body(signupDto)
                .when().post("/api/member/")
                .then()
                .extract()
                .statusCode();
        assertThat(statusCode).isEqualTo(200);
    }

    @Test
    void 유저는_로그인할_수_있다() {
        Map<String, String> signupDto = new HashMap<>();
        signupDto.put("email", "prasebak2@naver.com");
        signupDto.put("sex", "남자");
        signupDto.put("name", "prasebak");

        RestAssured
                .given()
                .body(signupDto)
                .when().post("/api/member/");

        Map<String, String> loginDto = Map.of("email", "prasebak2@naver.com");

        Map<String, String> cookies = RestAssured
                .given()
                .params(loginDto)
                .when().get("/api/member/")
                .then()
                .extract()
                .cookies();
        assertThat(cookies.get("token")).isNotEmpty();
    }
}
