package com.riya.rms.utils;

public enum Role {
    ADMIN,
    TEACHER,
    STUDENT;

    public static Role toEnum(String value) {
        return Role.valueOf(value.toUpperCase());
    }

    public String toStringValue() {
        return name().toLowerCase();
    }
}
