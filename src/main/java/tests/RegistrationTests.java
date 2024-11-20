package tests;

import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class RegistrationTests {

    Faker faker = new Faker();

    @Test
    @Disabled // bug - cause in Swagger 200 code is expected but actual result is 201
    public void successfulRegistrationOfTheUser() {
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(8, 20);
        Response registerResponse = Helper.registerUser(email, password, "user", 200);
        Helper.verifyTokens(registerResponse);
    }

    @Test
    @Disabled // bug - cause in Swagger 200 code is expected but actual result is 201
    public void successfulRegistrationOfTheAdmin() {
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(8, 20);
        Response registerResponse = Helper.registerUser(email, password, "admin", 200);
        Helper.verifyTokens(registerResponse);
    }

    @Test
    public void registrationWithInvalidEmailWithoutAtSign() {
        String email = "tatsenko.tetiana.gmail.com";
        String password = faker.internet().password(8, 20);
        Response registerResponse = Helper.registerUser(email, password, "user", 400);
        Helper.verifyErrorMessage(registerResponse, "email", "Email is not valid.");
    }

    @Test
    public void registrationWithPasswordLessThanEightSymbols() {
        String email = faker.internet().emailAddress();
        String password = "Qwerty1";
        Response registerResponse = Helper.registerUser(email, password, "user", 400);
        Helper.verifyErrorMessage(registerResponse, "password", "Password must contain at least 8 characters");
    }
}
