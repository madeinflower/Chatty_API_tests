package tests;

import api.ApiRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import users.UserRole;
import utils.UrlUtil;

import static org.junit.jupiter.api.Assertions.*;

public class AdminTests extends BaseTest {

    @Test
    public void getAllUsers() {
        setTokensAfterUserRegistration(UserRole.ADMIN);

        RequestSpecification requestSpec = RestAssured.given()
                .header("Authorization", "Bearer " + accessAdminToken);

        Response response = ApiRequest.sendGetRequest(
                UrlUtil.BASE_URL + UrlUtil.GET_ALL_USERS_ENDPOINT,
                requestSpec
        );

        System.out.println("Response body: " + response.getBody().asString());

        assertEquals(200, response.getStatusCode(), "Administrator should have access to /api/users");

        assertTrue(response.getBody().asString().startsWith("["),
                "The response should be an array of users");

        assertNotNull(response.jsonPath().getString("[0].id"), "The id field should not be null");
        assertNotNull(response.jsonPath().getString("[0].email"), "The email field should not be null");
        assertNotNull(response.jsonPath().getString("[0].role"), "The role field should not be null");
        assertNotNull(response.jsonPath().getString("[0].gender"), "The gender field should not be null");
    }
}
