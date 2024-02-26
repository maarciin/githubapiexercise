package com.example.githubapiexercise.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ApiResponseRepoDto {
    private String repositoryName;
    private String ownerLogin;
    private List<ApiResponseBranchDto> branches;
}
