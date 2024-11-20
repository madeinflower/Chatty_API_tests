package tests;
import com.github.javafaker.Faker;
import dto.*;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class Helper {
    static Faker faker = new Faker();
    public static String userRegistration() {
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(8, 20);
        String role = "user";
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail(email);
        registerRequest.setPassword(password);
        registerRequest.setConfirmPassword(password);
        registerRequest.setRole(role);
        Response response = BaseTest.postRequestWithoutToken("/api/auth/register", 201, registerRequest);
        String token = response.path("accessToken");
        return token;
    }
    public static RegisterResponse userRegistrationFullResponse() {
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(8, 20);
        String role = "user";
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail(email);
        registerRequest.setPassword(password);
        registerRequest.setConfirmPassword(password);
        registerRequest.setRole(role);
        Response response = BaseTest.postRequestWithoutToken("/api/auth/register", 201, registerRequest);
        return response.as(RegisterResponse.class);
    }
    public static String adminRegistration() {
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(8, 20);
        String role = "admin";
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail(email);
        registerRequest.setPassword(password);
        registerRequest.setConfirmPassword(password);
        registerRequest.setRole(role);
        Response response = BaseTest.postRequestWithoutToken("/api/auth/register", 201, registerRequest);
        String token = response.path("accessToken");
        return token;
    }
    public static String userLogin(String email, String password) {
        LoginRequest loginRequest = new LoginRequest(email, password);
        Response loginResponse = BaseTest.postRequestWithoutToken("/api/auth/login", 200, loginRequest);
        return loginResponse.path("accessToken");
    }
    public static String randomTitleGenerator() {
        String title = faker.lorem().sentence(5);
        return title.substring(0, Math.min(title.length(), 40));
    }
    public static String randomDescriptionGenerator() {
        String description = faker.lorem().sentence(10);
        return description.substring(0, Math.min(description.length(), 100));
    }
    public static String randomContentGenerator() {
        String content = faker.lorem().paragraph(20);
        return content.substring(0, Math.min(content.length(), 1000));
    }
    public static Map<String, String> createPostAndGetIds(String token) {
        PostCreationRequest postCreationRequest = new PostCreationRequest();
        postCreationRequest.setTitle(randomTitleGenerator());
        postCreationRequest.setDescription(randomDescriptionGenerator());
        postCreationRequest.setBody(randomContentGenerator());
        Response response = BaseTest.postRequestWithToken(token, "/api/posts", 201, postCreationRequest);
        String postId = response.jsonPath().getString("id");
        String userId = response.jsonPath().getString("userId");
        Map<String, String> ids = new HashMap<>();
        ids.put("postId", postId);
        ids.put("userId", userId);
        return ids;
    }

    public static void verifyTokens(Response response) {
        String token = response.path("accessToken");
        assertFalse(token.isEmpty(), "The access token cannot be empty");
        String refreshToken = response.path("refreshToken");
        assertFalse(refreshToken.isEmpty(), "The refresh token cannot be empty");
        int expiration = response.path("expiration");
        assertTrue(expiration > 0, "The expiration should be greater than 0");
    }

    public static void verifyErrorMessages(Response response, String field, List<String> expectedMessages) {
        List<String> actualMessages = response.path(field);
        for (String message : expectedMessages) {
            assertTrue(actualMessages.contains(message), "Missing expected error message: " + message);
        }
    }

    public static Response registerUser(String email, String password, String role, int expectedStatusCode) {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail(email);
        registerRequest.setPassword(password);
        registerRequest.setConfirmPassword(password);
        registerRequest.setRole(role);
        return BaseTest.postRequestWithoutToken("/api/auth/register", expectedStatusCode, registerRequest);
    }

    public static void verifyErrorMessage(Response response, String field, String expectedMessage) {
        assertEquals(expectedMessage, response.path(field + "[0]"),
                "Expected error message for field '" + field + "' is missing or incorrect.");
    }

    public static String getUserId(String token) {
        Response userInfoResponse = BaseTest.getRequest(token, "/api/me", 200);
        return userInfoResponse.jsonPath().getString("id");
    }

    public static Response updateUserProfile(String token, String id, String name, int expectedStatusCode) {
        UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest();
        updateProfileRequest.setName(name);
        String endPoint = "/api/users/" + id;
        return BaseTest.putRequest(token, endPoint, expectedStatusCode, updateProfileRequest);
    }
}
