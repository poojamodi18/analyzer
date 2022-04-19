package com.polyrepo.analyzer.service;

import com.polyrepo.analyzer.client.GraphQLClient;
import com.polyrepo.analyzer.constant.StringConstants;
import com.polyrepo.analyzer.model.RepoNamesList;
import com.polyrepo.analyzer.util.QueryUtil;
import feign.FeignException;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class BranchService {

    @Value("${getDefaultBranchQuery}")
    private String getDefaultBranchQuery;

    @Value("${graphQLAccessPrefix}")
    private String graphQLAccessPrefix;

    @Autowired
    private GraphQLClient client;

    private final Logger logger = LoggerFactory.getLogger(BranchService.class);

    public Map<String,Object> getDefaultBranch(String orgUserName, RepoNamesList repoNamesList)throws FeignException, JSONException {
        StringBuilder repoNamesString = QueryUtil.getRepositoryListForQuery(repoNamesList,orgUserName);

        logger.info("Getting default branch ref of selected repository from organization: {}", orgUserName);
        logger.info("List of selected repositories : {}", repoNamesList);

        String query = String.format(getDefaultBranchQuery, repoNamesString);
        ResponseEntity<String> response;

        response = client.getQuery(StringConstants.AUTH_HEADER_PREFIX + graphQLAccessPrefix, query);
        JSONObject result = new JSONObject(Objects.requireNonNull(response.getBody())).getJSONObject(StringConstants.JSON_DATA_KEY);
        return result.toMap();
    }
}
