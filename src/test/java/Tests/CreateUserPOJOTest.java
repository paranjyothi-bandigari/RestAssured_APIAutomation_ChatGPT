package Tests;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.http.ContentType;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import Pojos.UserRequest;
import Pojos.City;

import java.util.Arrays;

public class CreateUserPOJOTest {

    final static String AUTH_TOKEN = "bearer gtywsf176534fd61ut4f";
    final static String API_KEY = "reqres-free-v1";
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test(description = "POST - Create User using POJO with Rest Assured & TestNG")
    public void createUserUsingPOJO() {

        // ✅ Create request body using POJOs
        UserRequest user = new UserRequest();
        user.setName("sidharth");
        user.setLanguages(Arrays.asList("Java", "Python"));
        user.setCity(Arrays.asList(
                new City("bangalore", "30"),
                new City("delhi", "40")
        ));

        // ✅ Send POST request with headers & body
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .header("x-api-key", API_KEY)
                .header("Authentication", AUTH_TOKEN)
                .body(user)
                .when()
                .post("/users");

        // ✅ Log response
        System.out.println("Response Body:\n" + response.getBody().asString());

        // ✅ Assert status code is 201
        Assert.assertEquals(response.getStatusCode(), 201, "Expected status code is 201 Created");

        // ✅ Assert response header contains Server = cloudflare
        String serverHeader = response.getHeader("Server");
        System.out.println("Server Header: " + serverHeader);
        Assert.assertEquals(serverHeader.toLowerCase(), "cloudflare", "Expected Server header value is cloudflare");
        // ✅ Assert response body name = sidharth
        String responseName = response.jsonPath().getString("name");
        System.out.println("Response Name: " + responseName);
        Assert.assertEquals(responseName, "sidharth", "Expected name in response body to be 'sidharth'");
    }
}
