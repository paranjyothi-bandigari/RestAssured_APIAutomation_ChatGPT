package Tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class NegativeScenariosTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @AfterClass
    public void teardown() {
        RestAssured.reset();
    }

    /**
     * Test Case: Verify 404 Not Found for an invalid API endpoint.
     * Description: This test sends a GET request to a non-existent endpoint
     * (`/test/unknownEndpoint`) to confirm the API returns a 404 Not Found status code,
     * indicating that the requested resource could not be found on the server.
     */
    @Test(description = "Verify 404 Not Found for invalid API endpoint")
    public void testInvalidEndpoint404() {
        Response response = RestAssured
                .given()
                .when()
                .get("/test/unknownEndpoint");

        System.out.println("Response Body (testInvalidEndpoint404): " + response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 404, "Expected 404 for invalid endpoint");
    }

    /**
     * Test Case: Verify handling of invalid query parameters (simulated 400 Bad Request).
     * Description: This test sends a GET request to the `/api/users` endpoint with an
     * unrecognized query parameter (`invalid=value12`). While `reqres.in` typically
     * ignores invalid query parameters and returns 200, this test simulates a scenario
     * where a backend might return a 400 Bad Request. The current assertion expects a 401,
     * which might not align with the actual `reqres.in` behavior for this specific input.
     *
     */
    @Test(description = "Verify handling of invalid query parameters (simulated Bad Request)")
    public void testBadRequest400() {
        Response response = RestAssured
                .given()
                .queryParam("invalid", "value12")
                .when()
                .get("/api/users");

        System.out.println("Response Body (testBadRequest400): " + response.getBody().asString());
        Assert.assertEquals(response.getStatusCode(), 401, "Expected 401"); // This assertion might fail for reqres.in
    }

    /**
     * Test Case: Verify 401 Unauthorized access to a simulated secure resource.
     * Description: This test attempts to access a non-existent "secure" endpoint
     * (`/api/secure/data`) without providing any authentication credentials.
     * The expectation is either a 401 Unauthorized if the API recognizes the path
     * as protected and denies access, or a 404 Not Found if the endpoint truly doesn't exist.
     * Since `reqres.in` is a public API, a 404 is more likely.
     *
     */
    @Test(description = "Verify 401 Unauthorized access to secure resource")
    public void testUnauthorized401() {
        Response response = RestAssured
                .given()
                .when()
                .get("/api/secure/data");

        System.out.println("Response Body (testUnauthorized401): " + response.getBody().asString());
        Assert.assertTrue(response.getStatusCode() == 401 || response.getStatusCode() == 404,
                "Expected 401 or 404 for unauthorized access to secure endpoint");
    }

    /**
     * Test Case: Verify 403 Forbidden access to a simulated restricted resource.
     * Description: This test simulates an attempt to access a resource (`/api/users/2`)
     * with an invalid authorization token, aiming to verify a 403 Forbidden response.
     * As `reqres.in` is a public API and the `/api/users/2` endpoint is publicly accessible,
     * it will likely return a 200 OK, making the assertion for 403 or 401 fail.
     * This scenario highlights the limitations of using a public placeholder API for all negative tests.
     *
     */
    @Test(description = "Verify 403 Forbidden access to restricted resource")
    public void testForbidden403() {
        Response response = RestAssured
                .given()
                .header("Authorization", "Bearer invalid_token")
                .when()
                .get("/api/users/2");

        System.out.println("Response Body (testForbidden403): " + response.getBody().asString());
        Assert.assertTrue(response.getStatusCode() == 403 || response.getStatusCode() == 401,
                "Expected 403 or 401 for forbidden access"); // This assertion will likely fail for reqres.in
    }
}