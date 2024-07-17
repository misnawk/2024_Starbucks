package com.example.starbucks.service;

public enum Status {
    SUCCESS(200, "Success"),
    ERROR(500,"Error"),
    NOT_FOUND(404,"Not Found"),
    UNAUTHORIZED(401,"Unauthorized");

    private int code;
    private String description;

    Status(int code, String description){
        this.code=code;
        this.description=description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
