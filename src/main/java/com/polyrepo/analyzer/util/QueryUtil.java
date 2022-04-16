package com.polyrepo.analyzer.util;

import com.polyrepo.analyzer.model.RepoName;
import com.polyrepo.analyzer.model.RepoNamesList;

public class QueryUtil {
    private QueryUtil() {
    }

    public static StringBuilder getRepositoryListForQuery(RepoNamesList repoNamesList, String orgUserName) {
        StringBuilder repoNamesString = new StringBuilder();
        for (RepoName r :
                repoNamesList.getRepoNames()) {
            repoNamesString.append("repo:").append(orgUserName).append("/").append(r.getName()).append(" ");
        }
        if (repoNamesList.getRepoNames().isEmpty()) {
            repoNamesString.append("org:").append(orgUserName);
        }
        return repoNamesString;
    }
}
