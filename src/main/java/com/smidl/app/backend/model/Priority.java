package com.smidl.app.backend.model;

public enum Priority {
    LOW("Low"), HIGH("High");

    private String code;

    private Priority(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
