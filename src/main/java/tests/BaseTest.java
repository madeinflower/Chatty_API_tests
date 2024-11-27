package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import users.User;
import users.UserRole;
import io.restassured.response.Response;
import api.ApiRequest;
import utils.UrlUtil;
import io.restassured.path.json.JsonPath;

public class BaseTest {

    protected String accessToken;
    protected String refreshToken;
    protected String accessAdminToken;
    protected String refreshAdminToken;
    protected User authRegisteredUser;

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = UrlUtil.BASE_URL;
    }

    public void setTokensAfterUserRegistration(UserRole role) {
        JsonPath jsonPath = registerValidUser(role).jsonPath();
        String accessToken = jsonPath.getString("accessToken");
        String refreshToken = jsonPath.getString("refreshToken");

        if (UserRole.ADMIN == role) {
            this.accessAdminToken = accessToken;
            this.refreshAdminToken = refreshToken;
        } else if (UserRole.USER == role) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        } else {
            throw new IllegalArgumentException("User with unknown role");
        }
    }

    private Response registerValidUser(UserRole role) {
        this.authRegisteredUser = new User(role);
        String requestBody = "{\n" +
                "\"email\": \"" + authRegisteredUser.getEmail() + "\",\n" +
                "\"password\": \"" + authRegisteredUser.getPassword() + "\",\n" +
                "\"confirmPassword\": \"" + authRegisteredUser.getConfirmPassword() + "\",\n" +
                "\"role\": \"" + authRegisteredUser.getRole().getRole() + "\"\n" +
                "}";

        return ApiRequest.sendPostRequest(UrlUtil.REGISTER_ENDPOINT, requestBody);
    }

    public Response refreshTokens(UserRole role) {
        if (UserRole.ADMIN == role) {
            return ApiRequest.sendPostRequest(UrlUtil.REFRESH_TOKEN_ENDPOINT, "{ \"refreshToken\": \"" + refreshAdminToken + "\" }");
        } else if (UserRole.USER == role) {
            return ApiRequest.sendPostRequest(UrlUtil.REFRESH_TOKEN_ENDPOINT, "{ \"refreshToken\": \"" + refreshToken + "\" }");
        }
        throw new IllegalArgumentException("User with unknown role");
    }

    public Response loginRegisteredUser(UserRole role) {
        String loginRequestBody = "{\n" +
                "\"email\": \"" + authRegisteredUser.getEmail() + "\",\n" +
                "\"password\": \"" + authRegisteredUser.getPassword() + "\"\n" +
                "}";

        if (UserRole.ADMIN == role) {
            return ApiRequest.sendPostRequest(UrlUtil.LOGIN_ENDPOINT, loginRequestBody);
        } else if (UserRole.USER == role) {
            return ApiRequest.sendPostRequest(UrlUtil.LOGIN_ENDPOINT, loginRequestBody);
        }
        throw new IllegalArgumentException("User with unknown role");
    }
}
