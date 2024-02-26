package com.example.githubapiexercise.github;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
class BranchInfo {
    private String branchName;
    private String lastCommitSha;
}
