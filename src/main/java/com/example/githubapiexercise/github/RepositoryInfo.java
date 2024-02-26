package com.example.githubapiexercise.github;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
class RepositoryInfo {
    private String repositoryName;
    private String ownerLogin;
    private List<BranchInfo> branches;
}
