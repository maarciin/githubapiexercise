package com.example.githubapiexercise.service;

import com.example.githubapiexercise.exception.UserNotFoundException;
import com.example.githubapiexercise.model.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class GitHubService {

    private static final String GITHUB_API_URL = "https://api.github.com";
    private final RestClient restClient;

    public GitHubService() {
        restClient = RestClient.builder()
                .baseUrl(GITHUB_API_URL)
                .build();
    }

    public List<ApiResponseRepoDto> listNonForkReposWithBranches(String username) {
        var repos = fetchNonForkRepos(username);

        return repos.stream()
                .map(repo -> {
                    var branches = fetchBranchesByUsernameAndRepoName(username, repo.getRepositoryName());
                    repo.setBranches(branches);
                    return repo;
                }).toList();
    }

    private List<ApiResponseRepoDto> fetchNonForkRepos(String username) {
        var responseRepos = restClient.get()
                .uri("/users/{username}/repos", username)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (request, response) -> {
                    throw new UserNotFoundException("GitHub user not found");
                })
                .body(new ParameterizedTypeReference<List<GitHubRepo>>() {
                });

        return responseRepos.stream()
                .filter(repo -> !repo.fork())
                .map(ApiResponseRepoDtoMapper::map)
                .toList();
    }

    private List<ApiResponseBranchDto> fetchBranchesByUsernameAndRepoName(String username, String repoName) {
        var responseBranches = restClient.get()
                .uri("/repos/{username}/{repoName}/branches", username, repoName)
                .retrieve()
                .body(new ParameterizedTypeReference<List<GitHubBranch>>() {
                });
        return responseBranches.stream()
                .map(ApiResponseBranchDtoMapper::map)
                .toList();

    }

}
