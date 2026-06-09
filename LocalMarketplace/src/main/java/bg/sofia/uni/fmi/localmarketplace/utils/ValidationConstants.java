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

    public static class Product {
        public static final String BLANK_NAME = "Product name should not be blank or empty!";
        public static final String BLANK_DESCRIPTION = "Product description should not be blank or empty!";
        public static final String NULL_TYPE = "Product type should not be null!";

        public static final String MIN_PRICE = "Minimum price should be 0!";
        public static final String MIN_QUANTITY = "Product quantity should be at least 1!";
        public static final String MIN_UPDATE_QUANTITY = "Product quantity should be at least 0!";
        public static final String LENGTH_DESCRIPTION = "Product description should not be over 100 characters long!";
    }

    public static class Review {
        public static final String LENGTH_TEXT = "Review text should not be over 500 characters long!";
        public static final String MIN_RATING = "Rating should be minimum 0 including!";
        public static final String MAX_RATING = "Rating should be max 5 including!";
    }

    public static class Cart {
        public static final String NULL_PRODUCT_ID = "Product ID should not be null!";
        public static final String MIN_QUANTITY = "Quantity should be at least 1!";
    }

    public static class Order {
        public static final String NULL_PAYMENT_METHOD = "Payment method should not be null!";
        public static final String NULL_CURRENCY = "Currency should not be null!";
        public static final String NULL_STATUS = "Order status should not be null!";
        public static final String EMPTY_CART = "Cannot place an order from an empty cart!";
        public static final String INSUFFICIENT_STOCK = "Insufficient stock to fulfil the order!";
        public static final String INVALID_STATUS_TRANSITION = "The requested status transition is not allowed!";
    }
}
