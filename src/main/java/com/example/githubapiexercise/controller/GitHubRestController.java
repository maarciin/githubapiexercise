package com.example.githubapiexercise.controller;

import com.example.githubapiexercise.service.GitHubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/github")
@RequiredArgsConstructor
public class GitHubRestController {

    private final GitHubService gitHubService;

    @GetMapping("/repos/{username}")
    public ResponseEntity<?> getReposWithBranches(@PathVariable String username) {
        return ResponseEntity.ok(gitHubService.listNonForkReposWithBranches(username));
    }

}
