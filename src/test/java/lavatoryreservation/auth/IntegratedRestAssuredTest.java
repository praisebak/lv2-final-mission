package lavatoryreservation.auth;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.web.server.LocalServerPort;

public abstract class IntegratedRestAssuredTest {

    @LocalServerPort
    protected int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }
}
