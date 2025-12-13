package com.riya.rms.utils;

//This class is responsible to convert string values to their corresponding enum constants.
public class EnumUtil {

    public static <T extends Enum<T>> T toEnum(Class<T> enumClass, String value) {
        if (value == null) {
            throw new IllegalArgumentException("Enum value cannot be null");
        }
        return Enum.valueOf(enumClass, value.toUpperCase());
    }

    public static <T extends Enum<T>> String toString(T enumValue) {
        if (enumValue == null) {
            return null;
        }
        return enumValue.name().toLowerCase();
    }
}
