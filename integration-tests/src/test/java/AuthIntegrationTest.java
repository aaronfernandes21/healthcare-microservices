import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsNull.notNullValue;

public class AuthIntegrationTest {

    @BeforeAll
    public static void setup(
    ) {
        RestAssured.baseURI = "http://localhost:4004";
    }

    @Test
    public void testAuthGetValidToken() {

        String input = """
                    {
                        "email" : "brave@gmail.com",
                        "password": "password123"
                    }
                
                """;

        Response response = given()
                .contentType("application/json")
                .body(input)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue())
                .extract().response();
        System.out.println("Generated token: " + response.jsonPath().getString("token"));
    }
    @Test
    public void testAuthGetInValidToken() {

        String input = """
                    {
                        "email" : "google@gmail.com",
                        "password": "password678"
                    }
                
                """;

       given()
                .contentType("application/json")
                .body(input)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(401);

    }
}
