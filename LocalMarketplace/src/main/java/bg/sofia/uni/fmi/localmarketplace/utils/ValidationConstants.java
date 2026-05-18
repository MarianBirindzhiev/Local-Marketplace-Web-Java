package bg.sofia.uni.fmi.localmarketplace.utils;

public class ValidationConstants {

    public static class User {
        public static final String BLANK_FIRST_NAME = "First name should not be blank or empty!";
        public static final String BLANK_LAST_NAME = "Last name should not be blank or empty!";
        public static final String BLANK_USERNAME = "Username should not be blank or empty!";
        public static final String BLANK_EMAIL = "Email should not be blank or empty!";
        public static final String BLANK_PASSWORD = "Password should not be blank or empty!";
        public static final String BLANK_PHONE = "Phone number should not be blank or empty!";

        public static final String LENGTH_FIRST_NAME = "First name should be at most 50 characters long!";
        public static final String LENGTH_LAST_NAME = "Last name should be at most 50 characters long!";
        public static final String LENGTH_USERNAME = "Username should be at most 50 characters long!";
        public static final String LENGTH_EMAIL = "Email should be at most 255 characters long!";
        public static final String INVALID_EMAIL = "Invalid email!";
        public static final String LENGTH_PASSWORD = "Password should be between 6 and 50 characters long!";
        public static final String INVALID_PHONE = "Invalid number format! Must start with + and include only number.";
    }
}
