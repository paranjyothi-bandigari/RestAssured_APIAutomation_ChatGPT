package Tests;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class Test001 {

    private static final Logger LOGGER = Logger.getLogger(Test001.class);

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test(description = "Test positive scenario for POST method")
    public void testPostMethodPositiveScenario() {
        // Request body
        String requestBody = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";

        LOGGER.info("Sending POST request with request body: " + requestBody);

        // Send POST request and capture the response
        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/users");

        // Get response status code
        int statusCode = response.getStatusCode();
        LOGGER.info("Status code: " + statusCode);

        assertEquals(statusCode, 201, "Incorrect status code");

        // Get response body
        String responseBody = response.getBody().asString();
        LOGGER.info("Response body: " + responseBody);

        // Add assertions as per your requirements
        // For example, to assert the response body contains the expected values
        //assertEquals(response.jsonPath().getString("name"), "morpheus", "Incorrect name value");
        //assertEquals(response.jsonPath().getString("job"), "leader", "Incorrect job value");
//        assertEquals(response.jsonPath().getString("id"), "874", "Incorrect id value");
//        assertEquals(response.jsonPath().getString("createdAt"), "2023-06-22T21:30:51.352Z", "Incorrect createdAt value");
    }

    @Test(description = "Test negative scenario for POST method with invalid request body")
    public void testPostMethodNegativeScenario() {
        // Request body with invalid format
        String invalidRequestBody = "InvalidRequestBody";

        LOGGER.info("Sending POST request with invalid request body: " + invalidRequestBody);

        // Send POST request and capture the response
        Response response = given()
                .contentType(ContentType.JSON)
                .body(invalidRequestBody)
                .when()
                .post("/users");

        // Get response status code
        int statusCode = response.getStatusCode();
        LOGGER.info("Status code: " + statusCode);

        assertEquals(statusCode, 400, "Incorrect status code");
    }
}