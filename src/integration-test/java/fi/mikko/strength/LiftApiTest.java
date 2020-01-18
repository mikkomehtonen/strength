package fi.mikko.strength;

import static io.restassured.RestAssured.with;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

import fi.mikko.strength.feature.lift.BasicLiftDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LiftApiTest {

  private static final String LIFT_NAME = "Deadlift";

  @LocalServerPort
  int port;

  @BeforeEach
  public void setUp() {
    RestAssured.baseURI = "http://localhost/api/v1";
    RestAssured.port = port;
  }

  @Test
  public void savingLiftReturns201() {
    with().body(
        BasicLiftDto.builder()
            .name(LIFT_NAME)
            .build())
        .given()
        .log().ifValidationFails()
        .contentType(ContentType.JSON)
        .when().request(Method.POST, "/lifts")
        .then()
        .contentType(ContentType.JSON)
        .statusCode(201)
        .body("name", equalTo(LIFT_NAME))
        .body("id", notNullValue());
  }
}
