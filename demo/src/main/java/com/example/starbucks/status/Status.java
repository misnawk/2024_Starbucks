package com.example.starbucks.status;

public enum Status {
    SUCCESS( "Success"),
    FAIL("FAIL"),
    ERROR( "Error");



    private String result;

    Status(String result) {
        this.result = result;
    }

    public String getResult(){return result;}
}
