package Utils;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

/**
 * ✅ Builder Pattern for Rest Assured requests
 */
public class RequestBuilder {

    private String baseURI;
    private String resourcePath;
    private int userId;

    /**
     * ✅ Set Base URI
     */
    public RequestBuilder setBaseURI(String baseURI) {
        this.baseURI = baseURI;
        return this; // chaining
    }

    /**
     * ✅ Set resource path (endpoint)
     */
    public RequestBuilder setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
        return this; // chaining
    }

    /**
     * ✅ Set query param userId
     */
    public RequestBuilder setUserId(int userId) {
        this.userId = userId;
        return this; // chaining
    }

    /**
     * ✅ Build Rest Assured RequestSpecification
     */
    public RequestSpecification build() {
        RestAssured.baseURI = this.baseURI;

        RequestSpecification request = RestAssured
                .given()
                .queryParam("userId", this.userId);

        return request;
    }

    /**
     * ✅ Get resource path for executing request
     */
    public String getResourcePath() {
        return this.resourcePath;
    }
}
