package Tests;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class GetUserTest {
    @Test
    public void testGetUsersPage2() {
        // Base URI
        RestAssured.baseURI = "https://reqres.in";

        // Send GET request
        Response response = RestAssured
                .given()
                .when()
                .get("/api/users?page=2")
                .then()
                .extract()
                .response();

        // Log full response (optional)
        response.prettyPrint();

        // Validate Status Code
        assertEquals(response.getStatusCode(), 200, "Expected HTTP Status 200");

        // Validate Content Type
        assertEquals(response.getContentType(), "application/json; charset=utf-8", "Expected Content-Type JSON");

        // Validate Response Body contains data
        int totalUsers = response.jsonPath().getList("data").size();
        assertNotNull(totalUsers, "User list should not be null");

        // Validate first user's email is not null
        String email = response.jsonPath().getString("data[0].email");
        assertNotNull(email, "First user's email should not be null");
    }
}
