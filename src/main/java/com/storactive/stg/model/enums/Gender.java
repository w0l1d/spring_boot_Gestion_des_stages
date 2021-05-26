package com.storactive.stg.model.enums;

public enum Gender {
    M("Male"), F("Female");

    private final String displayValue;

    private Gender(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
