# GitHub Repositories Listing API

### Overview
This project provides a simple REST API to list GitHub repositories for a given user, excluding forked repositories. 
For each repository, it also lists the branches along with the last commit SHA. 
This application is built using Spring Boot and utilizes the GitHub REST API v3 for fetching data.

## API Usage

### List Non-Fork Repositories

**Request:**

`GET /api/v1/github/users/{username}/repos`

**Response:**

```json
[
    {
        "repositoryName": "repo-name",
        "ownerLogin": "owner-login",
        "branches": [
            {
                "name": "branch-name",
                "lastCommitSha": "commit-sha"
            }
        ]
    }
]
```

Replace {username} with the GitHub username whose repositories you want to list.

### Handling Errors

If the GitHub user does not exist, the API returns a 404 response with the following format:

```json
{
    "status": 404,
    "message": "GitHub user not found"
}
```
