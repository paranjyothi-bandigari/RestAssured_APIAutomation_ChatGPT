package Tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class GetUsersQueryParamEdgeCasesTest {

    final static Logger logger = Logger.getLogger(GetUsersQueryParamEdgeCasesTest.class);
    final static String API_KEY = "reqres-free-v1"; // Optional header if your account needs it
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @DataProvider(name = "pageDataProvider")
    public Object[][] pageDataProvider() {
        return new Object[][] {
                { 1, 200 },         // Normal page
                { 2, 200 },         // Normal page
                { -1, 200 },        // Negative number
                { -999, 200 },      // Large negative
                { 0, 200 },         // Zero page
                { "abc", 200 },     // Alphabetic string
                { "!!@@##", 200 },  // Special characters
                { "1.5", 200 },     // Decimal string
                { "", 200 },        // Empty string
                { null, 200 }       // No param
        };
    }

    @Test(dataProvider = "pageDataProvider", description = "Validate GET users with various page query param edge cases")
    public void testGetUsersWithPageQueryParam(Object page, int expectedStatusCode) {
        logger.info("Starting test for page param: " + page);

        Response response;

        if (page == null) {
            response = RestAssured
                    .given()
                    .header("x-api-key", API_KEY)
                    .when()
                    .get("/users");
        } else {
            response = RestAssured
                    .given()
                    .header("x-api-key", API_KEY)
                    .queryParam("page", page)
                    .when()
                    .get("/users");
        }

        logger.info("Response Body: " + response.getBody().asString());

        Assert.assertEquals(response.getStatusCode(), expectedStatusCode, "Status code should match expected");

        if (page instanceof Integer) {
            int actualPage = response.jsonPath().getInt("page");
            logger.info("Returned page: " + actualPage);
        }

        logger.info("Test Passed ✅");
    }
}

