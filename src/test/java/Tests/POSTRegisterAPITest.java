package Tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.http.ContentType;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * API Test Script for POST Register Method using RestAssured + TestNG
 * URL: https://reqres.in/api/register
 *
 * Positive Scenario:
 *  - Valid credentials should return 200 with id and token
 *
 * Negative Scenario:
 *  - Invalid payload (missing email/password) -> 400
 *  - Wrong endpoint -> 404
 */

public class POSTRegisterAPITest {
    final static Logger logger = Logger.getLogger(POSTRegisterAPITest.class);
    final static String API_KEY = "reqres-free-v1"; // Optional header if your account needs it

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test(description = "Positive Test: Register user with valid credentials")
    public void testRegisterUserPositive() {
        logger.info("Starting Positive Test: Register user with valid credentials");

        String requestBody = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }";

        Response response = RestAssured
                .given()
                .header("x-api-key", API_KEY) // if needed
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/register");

        logger.info("Response Body: " + response.getBody().asString());

        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 OK");

        int id = response.jsonPath().getInt("id");
        String token = response.jsonPath().getString("token");

        Assert.assertEquals(id, 4, "ID should be 4");
        Assert.assertNotNull(token, "Token should not be null");

        logger.info("POST Register Positive Test Passed ✅");
    }

    @DataProvider(name = "negativeRegisterTestData")
    public Object[][] negativeRegisterTestData() {
        return new Object[][] {
                // Invalid payloads → expect 400
                { "/register", "{ \"email\": \"eve.holt@reqres.in\" }", 400 },     // Missing password
                { "/register", "{ \"password\": \"pistol\" }", 400 },              // Missing email
                { "/register", "{ \"email\": \"wrong@reqres.in\", \"password\": \"pistol\" }", 400 }, // Invalid email
                { "/register", "{ invalid-json }", 400 },                          // Malformed JSON

                // Invalid endpoint → expect 404
                { "/registeruser", "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }", 404 },
                { "/register/invalid", "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }", 404 }
        };
    }

    @Test(dataProvider = "negativeRegisterTestData", description = "Negative Tests: Register with invalid payload or wrong endpoint")
    public void testRegisterUserNegative(String endpoint, String requestBody, int expectedStatusCode) {
        logger.info("Starting Negative Test: POST Register with endpoint: " + endpoint + ", expecting status: " + expectedStatusCode);

        Response response = RestAssured
                .given()
                .header("x-api-key", API_KEY) // if needed
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(endpoint);

        logger.info("Response Body: " + response.getBody().asString());
        logger.info("Actual Status Code: " + response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), expectedStatusCode, "Status code should match expected");

        logger.info("POST Register Negative Test Passed ✅");
    }
}
