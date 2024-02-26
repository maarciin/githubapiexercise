package com.example.githubapiexercise.github;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/github")
@RequiredArgsConstructor
class GitHubRestController {

    private final GitHubService gitHubService;

    @GetMapping("/users/{username}/repos")
    public ResponseEntity<?> getUserRepos(@PathVariable String username) {
        List<RepositoryInfo> repos = gitHubService.listNonForkRepos(username);
        return ResponseEntity.ok(repos);
    }

}
