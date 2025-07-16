package Tests;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.http.ContentType;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * API Test Script for POST Method using RestAssured + TestNG
 * URL: https://reqres.in/api/users
 *
 * Positive Scenario:
 *  - Validates Status Code 201
 *  - Validates Response Body has correct name/job/id/createdAt
 *
 * Negative Scenario:
 *  - Send invalid payload or empty payload and check response
 *  - Note: Reqres dummy API returns 201 even for invalid payloads
 */

public class POSTCreateUserAPITest {
    final static Logger logger = Logger.getLogger(POSTCreateUserAPITest.class);
    final static String API_KEY = "reqres-free-v1"; // ✅ Add your API key here

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test(description = "Positive Test: Create user with valid payload")
    public void testCreateUserPositive() {
        logger.info("Starting Positive Test: Create user with valid payload");

        String requestBody = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";

        Response response = RestAssured
                .given()
                .header("x-api-key", API_KEY) // ✅ Add API key header
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/users");

        logger.info("Response Body: " + response.getBody().asString());

        Assert.assertEquals(response.getStatusCode(), 201, "Expected status code 201 Created");

        String name = response.jsonPath().getString("name");
        String job = response.jsonPath().getString("job");
        String id = response.jsonPath().getString("id");
        String createdAt = response.jsonPath().getString("createdAt");

        Assert.assertEquals(name, "morpheus", "Name should match");
        Assert.assertEquals(job, "leader", "Job should match");
        Assert.assertNotNull(id, "ID should not be null");
        Assert.assertNotNull(createdAt, "createdAt should not be null");

        logger.info("Positive Test Passed ✅");
    }

    @Test(description = "Negative Test: Create user with invalid payload")
    public void testCreateUserNegative() {
        logger.info("Starting Negative Test: Create user with invalid payload");

        String invalidRequestBody = "{ \"role\": \"leader\" }"; // missing 'name'

        Response response = RestAssured
                .given()
                .header("x-api-key", API_KEY) // ✅ Add API key header
                .contentType(ContentType.JSON)
                .body(invalidRequestBody)
                .when()
                .post("/users");

        logger.info("Response Body: " + response.getBody().asString());

        // Reqres still returns 201 for invalid payload
        Assert.assertEquals(response.getStatusCode(), 201, "Reqres returns 201 even for invalid payload");

        String name = response.jsonPath().getString("name");
        String job = response.jsonPath().getString("job");

        Assert.assertTrue(name == null || !name.equals("morpheus"), "Name should not be 'morpheus'");
        Assert.assertTrue(job == null || !job.equals("leader"), "Job should not be 'leader'");

        logger.info("Negative Test Passed ✅");
    }

    @Test(description = "Negative Test: Create user with empty payload")
    public void testCreateUserEmptyPayload() {
        logger.info("Starting Negative Test: Create user with empty payload");

        String emptyRequestBody = "{}";

        Response response = RestAssured
                .given()
                .header("x-api-key", API_KEY) // ✅ Add API key header
                .contentType(ContentType.JSON)
                .body(emptyRequestBody)
                .when()
                .post("/users");

        logger.info("Response Body: " + response.getBody().asString());

        Assert.assertEquals(response.getStatusCode(), 201, "Reqres returns 201 even for empty payload");

        String name = response.jsonPath().getString("name");
        String job = response.jsonPath().getString("job");

        Assert.assertNull(name, "Name should be null");
        Assert.assertNull(job, "Job should be null");

        logger.info("Negative Test (Empty Payload) Passed ✅");
    }
}