package com.example.githubapiexercise.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ApiResponseException {
    private final int status;
    private final String message;
}
