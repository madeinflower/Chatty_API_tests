package tests;

import dto.LoginRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

public class LoginTests {

    @Test
    public void successfulLoginOfTheUser() {
        LoginRequest loginRequest = new LoginRequest("tatsenko.tetiana@gmail.com", "Qwerty12345");
        Response loginResponse = BaseTest.postRequestWithoutToken("/api/auth/login", 200, loginRequest);
        Helper.verifyTokens(loginResponse);
    }

    @Test
    public void successfulLoginOfTheAdmin() {
        LoginRequest loginRequest = new LoginRequest("tetiana.admin@gmail.com", "Qwerty12345admin");
        Response loginResponse = BaseTest.postRequestWithoutToken("/api/auth/login", 200, loginRequest);
        Helper.verifyTokens(loginResponse);
    }

    @Test
    public void loginWithoutEmail() {
        LoginRequest loginRequest = new LoginRequest("", "Qwerty12345");
        Response loginResponse = BaseTest.postRequestWithoutToken("/api/auth/login", 400, loginRequest);
        Helper.verifyErrorMessages(loginResponse, "email", List.of("Email cannot be empty", "Invalid email format"));
    }

    @Test
    public void loginWithMissingPasswordAttempt() {
        LoginRequest loginRequest = new LoginRequest("tatsenko.tetiana@gmail.com", "");
        Response loginResponse = BaseTest.postRequestWithoutToken("/api/auth/login", 400, loginRequest);
        Helper.verifyErrorMessages(loginResponse, "password", List.of(
                "Password cannot be empty",
                "Password must contain at least 8 characters"
        ));
    }
}
