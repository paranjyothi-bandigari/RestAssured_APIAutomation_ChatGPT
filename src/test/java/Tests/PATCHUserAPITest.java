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
 * API Test Script for PATCH Method using RestAssured + TestNG
 * URL: https://reqres.in/api/users/2
 *
 * Positive Scenario:
 *  - Valid PATCH request returns 200
 *  - Validates name/job/updatedAt in response
 *
 * Negative Scenarios:
 *  - Malformed payload returns 400
 *  - Invalid endpoint returns 404
 */

public class PATCHUserAPITest {

    final static Logger logger = Logger.getLogger(PATCHUserAPITest.class);
    final static String API_KEY = "reqres-free-v1";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test(description = "Positive Test: Update user with PATCH request")
    public void testUpdateUserPatchPositive() {
        logger.info("Starting Positive Test: PATCH user with valid payload");

        String requestBody = "{ \"name\": \"morpheus\", \"job\": \"zion resident\" }";

        Response response = RestAssured
                .given()
                .header("x-api-key", API_KEY)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .patch("/users/2");

        logger.info("Response Body: " + response.getBody().asString());

        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 OK");

        String name = response.jsonPath().getString("name");
        String job = response.jsonPath().getString("job");
        String updatedAt = response.jsonPath().getString("updatedAt");

        Assert.assertEquals(name, "morpheus", "Name should match");
        Assert.assertEquals(job, "zion resident", "Job should match");
        Assert.assertNotNull(updatedAt, "updatedAt should not be null");

        logger.info("PATCH Positive Test Passed ✅");
    }

    @DataProvider(name = "negativePatchTestData")
    public Object[][] negativePatchTestData() {
        return new Object[][] {
                { "/users/2", "{ invalid-json }", 400 }, // Malformed JSON, should give 400
                { "/invalidusers/2", "{ \"name\": \"neo\" }", 404 },// Invalid endpoint, should give 404
                { "/userss/2", "{ \"name\": \"trinity\" }", 404 },           // Another typo
                { "/users/2/invalid", "{ \"name\": \"smith\" }", 404 },      // Extra segment
                { "/api/users/2/wrong", "{ \"job\": \"agent\" }", 404 },     // Extra path
                { "/nonexistent/endpoint", "{ \"name\": \"oracle\" }", 404 } // Fully invalid endpoint
        };
    }

    @Test(dataProvider = "negativePatchTestData", description = "Negative Tests: PATCH with invalid data or endpoint")
    public void testUpdateUserPatchNegative(String endpoint, String requestBody, int expectedStatusCode) {
        logger.info("Starting Negative Test: PATCH endpoint: " + endpoint + ", expecting status: " + expectedStatusCode);

        Response response = RestAssured
                .given()
                .header("x-api-key", API_KEY)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .patch(endpoint);

        logger.info("Response Body: " + response.getBody().asString());
        logger.info("Actual Status Code: " + response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), expectedStatusCode, "Status code should match expected");

        logger.info("PATCH Negative Test Passed ✅");
    }

}

