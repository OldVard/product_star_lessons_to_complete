package com.my_games.models;

public enum Status {
    ACQUIRED("ПРИОБРЕТЕНО"),
    NOT_ACQUIRED("НЕ ПРИОБРЕТЕНО");

    private final String description;

    Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    
}
