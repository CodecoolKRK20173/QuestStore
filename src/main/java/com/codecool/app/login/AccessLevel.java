package com.codecool.app.login;

import com.codecool.app.messages.ErrorMessages;

public enum  AccessLevel {
    ADMIN, MENTOR, CODECOOLER, NOBODY;

    public static String getAccessLevelsString(){
        StringBuilder stringBuilder = new StringBuilder();

        for (int i=0; i<values().length-1; i++){
            AccessLevel value = values()[i];
            if (value != NOBODY){
                stringBuilder.append(value.name().toLowerCase());
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append(values()[values().length-1]);
        return stringBuilder.toString();
    }

    public static AccessLevel getAccessLevel(String accessLevelString){
        for (AccessLevel value : values()){
            if (value.name().toLowerCase().equals(accessLevelString.toLowerCase())){
                return value;
            }
        }

        return NOBODY;
    }

    @Override
    public String toString() {
        return name().toUpperCase();
    }
}
