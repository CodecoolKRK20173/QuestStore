package com.codecool.app.messages;

public class ErrorMessages {
    private final String NO_ACCESS_MESSAGE = "Given access level is not valid";
    private final String NO_OPTION_MESSAGE = "Option not found";
    private final String EMPTY_INPUT_MESSAGE = "Please enter something";
    private final String USER_NOT_LOGGED_MESSAGE = "No user is logged in";
    private final String USER_NOT_REGISTERED_MESSAGE = "Entered nickname and passwords doesn\'t matches";

    public String getNO_ACCESS_MESSAGE() {
        return NO_ACCESS_MESSAGE;
    }

    public String getNO_OPTION_MESSAGE() {
        return NO_OPTION_MESSAGE;
    }

    public String getEMPTY_INPUT_MESSAGE() {
        return EMPTY_INPUT_MESSAGE;
    }

    public String getUSER_NOT_LOGGED_MESSAGE() {
        return USER_NOT_LOGGED_MESSAGE;
    }

    public String getUSER_NOT_REGISTERED_MESSAGE() {
        return USER_NOT_REGISTERED_MESSAGE;
    }
}
