package Tests;

import Utils.RequestBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * ✅ TestNG class using the RequestBuilder utility
 */
public class NonBuilderPatternTest {

    @BeforeClass
    public void setup() {
        // No need for base URI here — handled by builder.
    }

    @Test(description = "Positive Test: GET posts for userId=1 using Builder Pattern in separate class")
    public void testGetPostsByUserId() {
        // ✅ Step 1: Use builder to configure request
        RequestBuilder builder = new RequestBuilder()
                .setBaseURI("https://jsonplaceholder.typicode.com")
                .setResourcePath("/posts")
                .setUserId(1);

        // ✅ Step 2: Build RequestSpecification
        RequestSpecification request = builder.build();

        // ✅ Step 3: Execute GET request using built config
        Response response = request.when().get(builder.getResourcePath());

        // ✅ Step 4: Log response
        System.out.println("Response Body:\n" + response.getBody().asString());

        // ✅ Step 5: Validate status code
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 OK");

        // ✅ Step 6: Validate response body content
        int userId = response.jsonPath().getInt("[0].userId");
        Assert.assertEquals(userId, 1, "User ID should be 1 in the response");
    }
}

