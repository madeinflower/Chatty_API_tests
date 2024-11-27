package tests;

import api.ApiRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import users.UserRole;
import utils.UrlUtil;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class UserAdminAuth extends BaseTest {

    @Test
    public void userLogin() {
        setTokensAfterUserRegistration(UserRole.USER);

        Response loginResponse = loginRegisteredUser(UserRole.USER);

        assertEquals(200, loginResponse.getStatusCode());
        assertTrue(loginResponse.getBody().asString().contains("accessToken"));
    }

    @Test
    public void adminLogin() {
        setTokensAfterUserRegistration(UserRole.ADMIN);

        Response loginResponse = loginRegisteredUser(UserRole.ADMIN);

        assertEquals(200, loginResponse.getStatusCode());
        assertTrue(loginResponse.getBody().asString().contains("accessToken"));
    }

    @Test
    public void refreshToken() {
        setTokensAfterUserRegistration(UserRole.USER);

        Response refreshResponse = refreshTokens(UserRole.USER);

        System.out.println("Response body: " + refreshResponse.getBody().asString());

        assertEquals(201, refreshResponse.getStatusCode());
        assertNotNull(refreshResponse.jsonPath().getString("accessToken"));
        assertNotNull(refreshResponse.jsonPath().getString("refreshToken"));
    }
}
