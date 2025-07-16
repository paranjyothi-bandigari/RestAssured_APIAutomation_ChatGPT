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
 * API Test Script for PUT Method using RestAssured + TestNG
 * URL: https://reqres.in/api/users/2
 *
 * Scenario:
 *  - Send valid PUT request to update user
 *  - Validate Status Code 200
 *  - Validate Response Body has correct name/job/updatedAt
 */

public class PUTUserAPITest {

    final static Logger logger = Logger.getLogger(PUTUserAPITest.class);
    final static String API_KEY = "reqres-free-v1"; // ✅ API key

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test(description = "Test: Update user with PUT request")
    public void testUpdateUser() {
        logger.info("Starting Test: Update user with PUT request");

        String requestBody = "{ \"name\": \"morpheus\", \"job\": \"zion resident\" }";

        Response response = RestAssured
                .given()
                .header("x-api-key", API_KEY)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put("/users/2");

        logger.info("Response Body: " + response.getBody().asString());

        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 OK");

        String name = response.jsonPath().getString("name");
        String job = response.jsonPath().getString("job");
        String updatedAt = response.jsonPath().getString("updatedAt");

        Assert.assertEquals(name, "morpheus", "Name should match");
        Assert.assertEquals(job, "zion resident", "Job should match");
        Assert.assertNotNull(updatedAt, "updatedAt should not be null");

        logger.info("PUT Test Passed ✅");
    }

    @DataProvider(name = "negativeTestData")
    public Object[][] negativeTestData() {
        return new Object[][] {
                { "/users/2", "{ \"name\": \"\" }", 200 },   // Bad payload - still returns 200 with Reqres
                { "/users/2", "{ invalid-json }", 400 },     // Malformed JSON - gives 400
                { "/users/9999", "{ \"name\": \"neo\", \"job\": \"the one\" }", 200 }, // Non-existent user - Reqres still returns 200
                { "/invalidusers/2", "{ \"name\": \"neo\", \"job\": \"the one\" }", 404 } // Invalid endpoint - real 404
        };
    }

    @Test(dataProvider = "negativeTestData", description = "Negative Test: PUT with invalid data or user")
    public void testUpdateUserNegative(String endpoint, String requestBody, int expectedStatusCode) {
        logger.info("Starting Negative Test: PUT with endpoint: " + endpoint + ", expected status: " + expectedStatusCode);

        Response response = RestAssured
                .given()
                .header("x-api-key", API_KEY)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put(endpoint);

        logger.info("Response Body: " + response.getBody().asString());
        logger.info("Actual Status Code: " + response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), expectedStatusCode, "Status code should match expected");

        logger.info("Negative Test Passed ✅");
    }

}
