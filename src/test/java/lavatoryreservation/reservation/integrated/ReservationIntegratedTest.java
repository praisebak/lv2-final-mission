package lavatoryreservation.reservation.integrated;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import lavatoryreservation.auth.IntegratedRestAssuredTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ReservationIntegratedTest extends IntegratedRestAssuredTest {

    private Map<String, String> loginCookies;

    @BeforeEach
    public void init() {
        Map<String, String> signupDto = new HashMap<>();

        signupDto.put("email", "prasebak222@naver.com");
        signupDto.put("sex", "MEN");
        signupDto.put("name", "prasebak");

        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(signupDto)
                .post("/api/member/")
                .then();

        Map<String, String> loginDto = new HashMap<>();
        loginDto.put("email", "prasebak222@naver.com");

        loginCookies = RestAssured
                .given()
                .params(loginDto)
                .when().get("/api/member/")
                .then()
                .extract()
                .cookies();
    }

    @Test
    void 유저는_자신의_예약_정보를_확인할_수_있다() {
        RestAssured
                .given().log().all()
                .cookies(loginCookies)
                .post("api/toilet/")
                .body()
                .then().log().all()
                .extract()
                .statusCode();

//        Long memberId, Long toiletId, LocalDateTime startTime, LocalDateTime endTime
//        Map.of()
        RestAssured
                .given().log().all()
                .cookies(loginCookies)
                .post("api/reservation/")
                .body()
                .then().log().all()
                .extract()
                .statusCode();

        int statusCode = RestAssured
                .given().log().all()
                .cookies(loginCookies)
                .get("api/reservation/my")
                .then().log().all()
                .extract()
                .statusCode();
        assertThat(statusCode).isEqualTo(200);
    }
}
