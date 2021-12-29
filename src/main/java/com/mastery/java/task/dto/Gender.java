package com.mastery.java.task.dto;

public enum Gender {
    MALE("MALE"),
    FEMALE("FEMALE");

    private final String description;

    Gender(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
