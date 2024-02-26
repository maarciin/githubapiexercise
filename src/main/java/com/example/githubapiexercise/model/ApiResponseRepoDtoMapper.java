package com.example.githubapiexercise.model;

public class ApiResponseRepoDtoMapper {

    public static ApiResponseRepoDto map(GitHubRepo repo) {
        return ApiResponseRepoDto.builder()
                .repositoryName(repo.name())
                .ownerLogin(repo.owner().login())
                .build();
    }
}
