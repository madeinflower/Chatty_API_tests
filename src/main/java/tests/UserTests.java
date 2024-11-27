package tests;

import api.ApiRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import users.UserRole;
import io.restassured.specification.RequestSpecification;
import io.restassured.RestAssured;
import utils.UrlUtil;

import static org.junit.jupiter.api.Assertions.*;

public class UserTests extends BaseTest {

    @Test
    public void getUser() {
        setTokensAfterUserRegistration(UserRole.USER);

        RequestSpecification requestSpec = RestAssured.given()
                .header("Authorization", "Bearer " + accessToken);

        Response response = ApiRequest.sendGetRequest(UrlUtil.BASE_URL + UrlUtil.ME_ENDPOINT, requestSpec);

        System.out.println("Response body: " + response.getBody().asString());

        assertEquals(200, response.getStatusCode());

        assertNotNull(response.jsonPath().getString("id"));
        assertNotNull(response.jsonPath().getString("email"));
        assertEquals("USER", response.jsonPath().getString("role"));

        assertNull(response.jsonPath().getString("name"));
        assertNull(response.jsonPath().getString("surname"));
        assertNull(response.jsonPath().getString("phone"));
        assertNull(response.jsonPath().getString("gender"));
        assertNull(response.jsonPath().getString("birthDate"));
        assertNull(response.jsonPath().getString("avatarUrl"));
        assertNull(response.jsonPath().getString("backgroundUrl"));

        String birthDate = response.jsonPath().getString("birthDate");
        if (birthDate != null) {
            assertTrue(birthDate.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z"));
        }
    }
}
