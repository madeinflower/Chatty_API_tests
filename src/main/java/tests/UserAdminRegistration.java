package tests;

import api.ApiRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import users.User;
import users.UserRole;
import utils.UrlUtil;

import static org.junit.jupiter.api.Assertions.*;

public class UserAdminRegistration extends BaseTest {

    @Test
    public void userRegistration() {
        User user = new User(UserRole.USER);

        String email = user.getEmail();
        String password = user.getPassword();
        String confirmPassword = user.getConfirmPassword();

        String requestBody = "{\n" +
                "\"email\": \"" + email + "\",\n" +
                "\"password\": \"" + password + "\",\n" +
                "\"confirmPassword\": \"" + confirmPassword + "\",\n" +
                "\"role\": \"" + UserRole.USER.getRole() + "\"\n" +
                "}";

        Response response = ApiRequest.sendPostRequest(UrlUtil.REGISTER_ENDPOINT, requestBody);

        System.out.println("Response body: " + response.getBody().asString());

        assertEquals(201, response.getStatusCode());
        assertNotNull(response.jsonPath().getString("accessToken"));
        assertNotNull(response.jsonPath().getString("refreshToken"));
    }

    @Test
    public void adminRegistration() {
        User admin = new User(UserRole.ADMIN);

        String email = admin.getEmail();
        String password = admin.getPassword();
        String confirmPassword = admin.getConfirmPassword();

        String requestBody = "{\n" +
                "\"email\": \"" + email + "\",\n" +
                "\"password\": \"" + password + "\",\n" +
                "\"confirmPassword\": \"" + confirmPassword + "\",\n" +
                "\"role\": \"" + UserRole.ADMIN.getRole() + "\"\n" +
                "}";

        Response response = ApiRequest.sendPostRequest(UrlUtil.REGISTER_ENDPOINT, requestBody);

        System.out.println("Response body: " + response.getBody().asString());

        assertEquals(201, response.getStatusCode());
        assertNotNull(response.jsonPath().getString("accessToken"));
        assertNotNull(response.jsonPath().getString("refreshToken"));
    }
}
