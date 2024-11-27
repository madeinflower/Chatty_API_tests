package api;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ApiRequest {

    public static Response sendPostRequest(String endpoint, String body) {
        return io.restassured.RestAssured
                .given()
                .contentType("application/json")
                .body(body)
                .post(endpoint);
    }

    public static Response sendPutRequest(String endpoint, String body) {
        return io.restassured.RestAssured
                .given()
                .contentType("application/json")
                .body(body)
                .put(endpoint);
    }

    public static Response sendGetRequest(String endpoint, RequestSpecification requestSpec) {
        return requestSpec
                .get(endpoint);
    }

    public static Response sendGetRequest(String endpoint) {
        return io.restassured.RestAssured
                .given()
                .get(endpoint);
    }
}
