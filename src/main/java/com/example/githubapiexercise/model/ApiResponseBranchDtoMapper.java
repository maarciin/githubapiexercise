package com.example.githubapiexercise.model;

public class ApiResponseBranchDtoMapper {

    public static ApiResponseBranchDto map(GitHubBranch branch) {
        return new ApiResponseBranchDto.ApiResponseBranchDtoBuilder()
                .name(branch.name())
                .lastCommitSha(branch.commit().sha())
                .build();
    }
}
