package users;

import java.util.Random;

public class User {

    private String email;
    private String password;
    private String confirmPassword;
    private UserRole role;

    public User(String email, String password, String confirmPassword, UserRole role) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.role = role;
    }

    public User(UserRole role) {
        this.email = generateRandomEmail();
        this.password = generatePassword();
        this.confirmPassword = this.password;
        this.role = role;
    }

    private String generateRandomEmail() {
        String baseEmail = "testuser";
        String uniquePart = String.valueOf(System.currentTimeMillis() + new Random().nextInt(1000)); // Добавляем случайное число
        return baseEmail + uniquePart + "@mail.com";
    }

    private String generatePassword() {
        String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_+=<>?";
        StringBuilder password = new StringBuilder();
        Random random = new Random();

        int passwordLength = 20;
        for (int i = 0; i < passwordLength; i++) {
            int randomIndex = random.nextInt(allowedChars.length());
            password.append(allowedChars.charAt(randomIndex));
        }

        return password.toString();
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRole getRole() {
        return role;
    }
}
