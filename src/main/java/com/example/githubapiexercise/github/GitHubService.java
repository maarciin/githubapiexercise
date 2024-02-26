package com.example.githubapiexercise.github;

import com.example.githubapiexercise.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
class GitHubService {

    private final RestTemplate restTemplate;

    private static final String GITHUB_REPOS_URL = "https://api.github.com/users/%s/repos";
    private static final String GITHUB_BRANCHES_URL = "https://api.github.com/repos/%s/%s/branches";

    public List<RepositoryInfo> listNonForkRepos(String username) {
        List<Map<String, Object>> repositories = fetchRepositories(username);
        return extractNonForkRepositoriesWithBranches(repositories, username);
    }

    private List<Map<String, Object>> fetchRepositories(String username) {
        try {
            String url = String.format(GITHUB_REPOS_URL, username);
            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    }
            );
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException("GitHub user not found");
        }
    }

    private List<RepositoryInfo> extractNonForkRepositoriesWithBranches(List<Map<String, Object>> repositories, String username) {
        List<RepositoryInfo> nonForkRepos = new ArrayList<>();
        if (repositories != null) {
            for (Map<String, Object> repo : repositories) {
                if (!(Boolean) repo.get("fork")) {
                    RepositoryInfo repoInfo = createRepositoryInfo(repo);
                    repoInfo.setBranches(fetchBranches(username, repoInfo.getRepositoryName()));
                    nonForkRepos.add(repoInfo);
                }
            }
        }
        return nonForkRepos;
    }

    private RepositoryInfo createRepositoryInfo(Map<String, Object> repo) {
        RepositoryInfo info = new RepositoryInfo();
        info.setRepositoryName((String) repo.get("name"));
        info.setOwnerLogin((String) ((Map<?, ?>) repo.get("owner")).get("login"));
        return info;
    }

    private List<BranchInfo> fetchBranches(String owner, String repoName) {
        String url = String.format(GITHUB_BRANCHES_URL, owner, repoName);
        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        List<Map<String, Object>> branches = response.getBody();
        return extractBranchesInfo(branches);
    }

    private List<BranchInfo> extractBranchesInfo(List<Map<String, Object>> branches) {
        List<BranchInfo> branchInfos = new ArrayList<>();
        if (branches != null) {
            for (Map<String, Object> branch : branches) {
                String name = (String) branch.get("name");
                Map<String, Object> commit = (Map<String, Object>) branch.get("commit");
                String sha = (String) commit.get("sha");
                branchInfos.add(new BranchInfo(name, sha));
            }
        }
        return branchInfos;
    }
}
