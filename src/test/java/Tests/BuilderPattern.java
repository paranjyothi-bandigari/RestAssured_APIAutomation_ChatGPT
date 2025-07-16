package Tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
public class BuilderPattern {
@BeforeClass
public void setup() {
    // ✅ Set base URI
    RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
}

    @Test(description = "Positive Test: Get posts for userId=1")
    public void testGetPostsByUserId() {
        // ✅ Send GET request with query param userId=1
        Response response = RestAssured
                .given()
                .queryParam("userId", 1)
                .when()
                .get("/posts");

        // ✅ Log response body
        System.out.println("Response Body:\n" + response.getBody().asString());

        // ✅ Validate status code is 200
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 OK");

        // ✅ Validate that the userId in the first post is 1
        int userId = response.jsonPath().getInt("[0].userId");
        Assert.assertEquals(userId, 1, "User ID should be 1 in the response");
    }
}