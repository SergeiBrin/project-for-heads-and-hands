package com.example.projectforheadsandhands.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiError {
    private String exception;
    private String message;
}
