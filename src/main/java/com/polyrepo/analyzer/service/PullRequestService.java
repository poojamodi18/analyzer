package com.polyrepo.analyzer.service;

import com.polyrepo.analyzer.client.GraphQLClient;
import com.polyrepo.analyzer.constant.StringConstants;
import com.polyrepo.analyzer.model.RepoNamesList;
import com.polyrepo.analyzer.util.DateUtil;
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
public class PullRequestService {

    @Autowired
    private GraphQLClient client;

    @Value("${getPullRequestNotUpdatedByDaysQuery}")
    private String getPullRequestNotUpdatedByDaysQuery;

    @Value("${getUnMergedPullRequestByDayQuery}")
    private String getUnMergedPullRequestByDayQuery;

    @Value("${graphQLAccessPrefix}")
    private String graphQLAccessPrefix;

    private final Logger logger = LoggerFactory.getLogger(PullRequestService.class);

    /**
     * This method will return the list of pull requests without activity since x days from the selected
     * repositories by user
     *
     * @param orgUserName   GitHub Organization login name
     * @param repoNamesList List of Repositories selected by user
     * @param days          Number of days without activity in pull request
     * @return List of pull requests without activity since x days
     * @throws FeignException FeignException.Unauthorized if token is invalid, FeignException.BadRequest if FeignClient returns 400 Bad Request
     * @throws JSONException if JSON parsing is invalid
     */
    public Map<String, Object> getPRNotUpdatedByDays(String orgUserName, RepoNamesList repoNamesList, int days) throws FeignException, JSONException {
        StringBuilder repoNamesString = QueryUtil.getRepositoryListForQuery(repoNamesList,orgUserName);

        String queryDateString = DateUtil.calculateDateFromDays(days);
        logger.info("Getting list of pull requests without activity since {} from organization: {}", queryDateString, orgUserName);
        logger.info("List of selected repositories : {}", repoNamesList);

        String query = String.format(getPullRequestNotUpdatedByDaysQuery, repoNamesString, queryDateString);
        ResponseEntity<String> response;

        response = client.getQuery(StringConstants.AUTH_HEADER_PREFIX + graphQLAccessPrefix, query);
        JSONObject result = new JSONObject(Objects.requireNonNull(response.getBody())).getJSONObject(StringConstants.JSON_DATA_KEY);
        return result.toMap();
    }

    /**
     * This method will return list of pull requests which are not merged since x days
     *
     * @param orgUserName   GitHub Organization login name
     * @param repoNamesList List of Repositories selected by user
     * @param days          Number of days without merged in pull requests
     * @return List of pull requests which are not merged since x days
     * @throws FeignException FeignException.Unauthorized if token is invalid, FeignException.BadRequest if FeignClient returns 400 Bad Request
     * @throws JSONException if JSON parsing is invalid
     */
    public Map<String, Object> getUnMergedPullRequestByDays(String orgUserName, RepoNamesList repoNamesList, int days) throws FeignException, JSONException {
        StringBuilder repoNamesString = QueryUtil.getRepositoryListForQuery(repoNamesList,orgUserName);

        String queryDateString = DateUtil.calculateDateFromDays(days);
        logger.info("Getting list of pull requests not merged since {} from organization: {}",queryDateString, orgUserName);
        logger.info("List of selected repositories : {}" , repoNamesList);

        String query = String.format(getUnMergedPullRequestByDayQuery, repoNamesString, queryDateString);
        ResponseEntity<String> response;

        response = client.getQuery(StringConstants.AUTH_HEADER_PREFIX + graphQLAccessPrefix, query);
        JSONObject result = new JSONObject(Objects.requireNonNull(response.getBody())).getJSONObject(StringConstants.JSON_DATA_KEY);
        return result.toMap();

    }
}