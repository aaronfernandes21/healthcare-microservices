import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsNull.notNullValue;

public class PatientIntegrationTest {


    @BeforeAll
    public static void setup(){
        RestAssured.baseURI = "http://localhost:4004";
    }

    @Test
    public void testPatientGetRequestWithValidToken(){
        String input = """
                    {
                        "email" : "brave@gmail.com",
                        "password": "password123"
                    }
                
                """;

        String response = given()
                .contentType("application/json")
                .body(input)
                .when()
                .post("/auth/login")
                .then()
                .extract()
                .jsonPath()
                .get("token");

        given()
                .header("Authorization", "Bearer "+response)
                .when()
                .get("/api/patients")
                .then()
                .statusCode(200)
                .body("patients", notNullValue());
    }


}
