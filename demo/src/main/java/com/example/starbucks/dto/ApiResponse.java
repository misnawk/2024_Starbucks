package com.example.starbucks.dto;

import com.example.starbucks.status.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private Status status;
    private String message;
    private T data;
}
