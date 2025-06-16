package lavatoryreservation.reservation.integrated;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import lavatoryreservation.auth.IntegratedRestAssuredTest;
import lavatoryreservation.reservation.dto.ReservationSpecificDto;
import lavatoryreservation.reservation.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ReservationIntegratedTest extends IntegratedRestAssuredTest {

    private Map<String, String> loginCookies;
    private Long memberId;
    @Autowired
    private ReservationRepository reservationRepository;

    @BeforeEach
    void init() {
        Map<String, String> signupDto = new HashMap<>();

        signupDto.put("email", "prasebak222@naver.com");
        signupDto.put("sex", "MEN");
        signupDto.put("name", "prasebak");

        memberId = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(signupDto)
                .post("/api/member/")
                .then()
                .extract()
                .body().as(Long.class);

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
        Map<String, String> addLavatory = Map.of("sex", "MEN", "description", "잠실1화장실");

        Long lavatoryId = RestAssured
                .given().log().all()
                .cookies(loginCookies)
                .body(addLavatory)
                .contentType("application/json")
                .post("api/lavatory/")
                .then().log().all()
                .extract()
                .body().as(Long.class);

        Long toiletId = RestAssured
                .given().log().all()
                .cookies(loginCookies)
                .body(Map.of("description", "hello", "isBidet", false, "lavatoryId", lavatoryId))
                .contentType("application/json")
                .post("api/toilet/")
                .then().log().all()
                .extract()
                .body().as(Long.class);

        Map<String, Object> addReservation = Map.of("memberId", memberId, "toiletId", toiletId, "startTime",
                LocalDateTime.now(), "endTime", LocalDateTime.now().plusHours(1L));

        RestAssured
                .given().log().all()
                .cookies(loginCookies)
                .body(addReservation)
                .contentType("application/json")
                .post("api/reservation/")
                .then().log().all()
                .extract()
                .statusCode();

        ReservationSpecificDto reservationSpecificDto = RestAssured
                .given().log().all()
                .cookies(loginCookies)
                .get("api/reservation/my")
                .then().log().all()
                .extract()
                .body()
                .as(ReservationSpecificDto.class);
        assertThat(reservationSpecificDto).isNotNull();
    }

    @Test
    void 유저는_자신의_예약을_삭제할_수_있다() {
        Map<String, String> addLavatory = Map.of("sex", "MEN", "description", "잠실1화장실");

        Long lavatoryId = RestAssured
                .given().log().all()
                .cookies(loginCookies)
                .body(addLavatory)
                .contentType("application/json")
                .post("api/lavatory/")
                .then().log().all()
                .extract()
                .body().as(Long.class);

        Long toiletId = RestAssured
                .given().log().all()
                .cookies(loginCookies)
                .body(Map.of("description", "hello", "isBidet", false, "lavatoryId", lavatoryId))
                .contentType("application/json")
                .post("api/toilet/")
                .then().log().all()
                .extract()
                .body().as(Long.class);

        Map<String, Object> addReservation = Map.of("memberId", memberId, "toiletId", toiletId, "startTime",
                LocalDateTime.now(), "endTime", LocalDateTime.now().plusHours(1L));

        RestAssured
                .given().log().all()
                .cookies(loginCookies)
                .body(addReservation)
                .contentType("application/json")
                .post("api/reservation/")
                .then().log().all()
                .extract()
                .statusCode();

        ReservationSpecificDto reservationSpecificDto = RestAssured
                .given().log().all()
                .cookies(loginCookies)
                .get("api/reservation/my")
                .then().log().all()
                .extract()
                .body()
                .as(ReservationSpecificDto.class);
        assertThat(reservationSpecificDto).isNotNull();
    }

    @Test
    void 유저는_다른사람의_예약을_삭제할_수_없다() {
        Map<String, String> addLavatory = Map.of("sex", "MEN", "description", "잠실1화장실");

        Long lavatoryId = RestAssured
                .given().log().all()
                .cookies(loginCookies)
                .body(addLavatory)
                .contentType("application/json")
                .post("api/lavatory/")
                .then().log().all()
                .extract()
                .body().as(Long.class);

        Long toiletId = RestAssured
                .given().log().all()
                .cookies(loginCookies)
                .body(Map.of("description", "hello", "isBidet", false, "lavatoryId", lavatoryId))
                .contentType("application/json")
                .post("api/toilet/")
                .then().log().all()
                .extract()
                .body().as(Long.class);

        Map<String, Object> addReservation = Map.of("memberId", memberId, "toiletId", toiletId, "startTime",
                LocalDateTime.now(), "endTime", LocalDateTime.now().plusHours(1L));

        RestAssured
                .given().log().all()
                .cookies(loginCookies)
                .body(addReservation)
                .contentType("application/json")
                .post("api/reservation/")
                .then().log().all()
                .extract()
                .statusCode();

        ReservationSpecificDto reservationSpecificDto = RestAssured
                .given().log().all()
                .cookies(loginCookies)
                .get("api/reservation/my")
                .then().log().all()
                .extract()
                .body()
                .as(ReservationSpecificDto.class);
        assertThat(reservationSpecificDto).isNotNull();
    }

    @Test
    void 현재_예약_현황을_확인할_수_있다() {
        Map<String, String> addLavatory = Map.of("sex", "MEN", "description", "잠실1화장실");

        Long lavatoryId = RestAssured
                .given().log().all()
                .cookies(loginCookies)
                .body(addLavatory)
                .contentType("application/json")
                .post("api/lavatory/")
                .then().log().all()
                .extract()
                .body().as(Long.class);

        Long toiletId = RestAssured
                .given().log().all()
                .cookies(loginCookies)
                .body(Map.of("description", "hello", "isBidet", false, "lavatoryId", lavatoryId))
                .contentType("application/json")
                .post("api/toilet/")
                .then().log().all()
                .extract()
                .body().as(Long.class);

        ReservationSpecificDto[] reservationSpecificDtos = RestAssured
                .given().log().all()
                .cookies(loginCookies)
                .contentType("application/json")
                .get("api/reservation/all")
                .then().log().all()
                .extract()
                .body()
                .as(ReservationSpecificDto[].class);

        long count = reservationRepository.count();
        assertThat(reservationSpecificDtos.length).isEqualTo(count);
    }
}
