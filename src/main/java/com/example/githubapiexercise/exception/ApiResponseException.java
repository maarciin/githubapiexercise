package com.example.githubapiexercise.exception;

public record ApiResponseException(int status, String message) {
}
