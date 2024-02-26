package com.example.githubapiexercise.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponseBranchDto {
    private String name;
    private String lastCommitSha;
}
