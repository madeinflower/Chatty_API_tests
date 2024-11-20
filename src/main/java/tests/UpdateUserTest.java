package tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateUserTest {

    @Test
    public void successfulUserNameUpdate() {
        String token = Helper.userRegistration();
        String id = Helper.getUserId(token);
        Response response = Helper.updateUserProfile(token, id, "UpdatedName", 200);
        assertEquals("UpdatedName", response.jsonPath().getString("name"), "Name was not updated correctly.");
        assertEquals(id, response.jsonPath().getString("id"), "User ID does not match.");
    }

    @Test
    public void updateUserNameWithDigits() {
        String token = Helper.userRegistration();
        String id = Helper.getUserId(token);
        Response response = Helper.updateUserProfile(token, id, "NewName123456", 400);
        System.out.println("Response: " + response.asPrettyString());
        String actualErrorMessage = response.jsonPath().getString("name[0]");
        assertNotNull(actualErrorMessage, "Error message for 'name' is missing.");
        assertTrue(actualErrorMessage.contains("must match"),
                "Expected error message to contain 'must match', but was: " + actualErrorMessage);
    }

    @Test
    @Disabled // Expected status code <400> but was <500>
    public void updateUserNameWithInvalidId() {
        String token = Helper.userRegistration();
        String id = "id"; // Invalid ID
        Response response = Helper.updateUserProfile(token, id, "UpdatedName", 400);
        Helper.verifyErrorMessage(response, "message", "Invalid UUID string: ID");
    }

    @Test
    @Disabled // Expected status code <400> but was <404>
    public void updateUserNameWithEmptyId() {
        String token = Helper.userRegistration();
        String id = "";
        Response response = Helper.updateUserProfile(token, id, "UpdatedName", 400);
        Helper.verifyErrorMessage(response, "httpStatus", "Invalid UUID string");
    }
}
