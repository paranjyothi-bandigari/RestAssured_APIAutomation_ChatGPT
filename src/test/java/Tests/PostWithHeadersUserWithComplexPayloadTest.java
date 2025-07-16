package Tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * API Test Script for POST Method using RestAssured + TestNG
 * URL: https://reqres.in/api/users
 *
 * Scenario:
 *  - Send POST request with nested JSON array/object payload
 *  - Add Content-Type and Authentication headers
 *  - Validate Status Code 201 Created
 *  - Validate Response Header: Server = cloudflare
 */

public class PostWithHeadersUserWithComplexPayloadTest {

    final static Logger logger = Logger.getLogger(PostWithHeadersUserWithComplexPayloadTest.class);
    final static String API_KEY = "reqres-free-v1";
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test(description = "Positive Test: Create user with nested JSON arrays/objects and custom headers")
    public void testCreateUserWithComplexPayload() {
        logger.info("Starting Test: Create user with nested JSON payload and Authentication header");

        String requestBody = "{\n" +
                "  \"name\": \"sidharth\",\n" +
                "  \"languages\": [\"Java\", \"Python\"],\n" +
                "  \"City\": [\n" +
                "    { \"Name\": \"bangalore\", \"Temperature\": \"30\" },\n" +
                "    { \"Name\": \"delhi\", \"Temperature\": \"40\" }\n" +
                "  ]\n" +
                "}";

        Response response = RestAssured
                .given()
                .header("x-api-key", API_KEY)
                .header("Content-Type", "application/json")
                .header("Authentication", "bearer gtywsf176534fd61ut4f")
                .body(requestBody)
                .when()
                .post("/users");

        logger.info("Response Body: " + response.getBody().asString());

        // ✅ Assert: Status Code
        Assert.assertEquals(response.getStatusCode(), 201, "Expected status code 201 Created");

        // ✅ Assert: Response Body value
        String name = response.jsonPath().getString("name");
        Assert.assertEquals(name, "sidharth", "Name should match input");

        // ✅ Assert: Response Header 'Server' = 'cloudflare'
        String serverHeader = response.getHeader("Server");
        logger.info("Response Header 'Server': " + serverHeader);
        Assert.assertNotNull(serverHeader, "Response should contain 'Server' header");
        Assert.assertEquals(serverHeader.toLowerCase(), "cloudflare", "Server header should be 'cloudflare'");

        logger.info("POST Test Passed ✅");
    }
}

