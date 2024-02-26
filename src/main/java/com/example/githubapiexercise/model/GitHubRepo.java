package com.example.githubapiexercise.model;

public record GitHubRepo(String name, boolean fork, GitHubOwner owner) {
}
