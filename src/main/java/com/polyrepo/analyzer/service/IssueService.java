package com.polyrepo.analyzer.service;

import com.polyrepo.analyzer.client.GraphQLClient;
import com.polyrepo.analyzer.constant.StringConstants;
import com.polyrepo.analyzer.model.RepoNamesList;
import com.polyrepo.analyzer.util.AverageUtil;
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

import java.util.*;

@Service
public class IssueService {

    @Value("${getPriority1IssuesOpenedBeforeXDaysQuery}")
    private String getPriority1IssuesOpenedBeforeXDaysQuery;

    @Value("${getClosedP1IssuesTimeQuery}")
    private String getClosedP1IssuesTimeQuery;

    @Value("${getClosedP2IssuesTimeQuery}")
    private String getClosedP2IssuesTimeQuery;

    @Value("${graphQLAccessPrefix}")
    private String graphQLAccessPrefix;

    @Autowired
    private GraphQLClient client;

    private final Logger logger = LoggerFactory.getLogger(IssueService.class);

    /**
     * This method returns the list of priority-1 issues of the selected repositories by user
     * and the date they were created with the name of repository they belong to
     *
     * @param orgUserName   GitHub Organization login name
     * @param repoNamesList List of Repositories selected by user
     * @param days          Number of days since before priority-1 issues are open
     * @return List of priority-1 issues open since before x date from selected repositories
     * @throws FeignException FeignException.Unauthorized if token is invalid, FeignException.BadRequest if FeignClient returns 400 Bad Request
     * @throws JSONException if JSON parsing is invalid
     */
    public Map<String, Object> getPriority1IssuesOpenedBeforeXDays(String orgUserName, RepoNamesList repoNamesList, int days) throws FeignException, JSONException {
        StringBuilder repoNamesString = QueryUtil.getRepositoryListForQuery(repoNamesList,orgUserName);

        String queryDateString = DateUtil.calculateDateFromDays(days);
        String query = String.format(getPriority1IssuesOpenedBeforeXDaysQuery, repoNamesString, queryDateString);
        logger.info("Getting priority-1 issues from selected repositories open since {}  from organization: {}", queryDateString, orgUserName);
        logger.info("List of selected repositories : {}", repoNamesList);

        ResponseEntity<String> response;

        response = client.getQuery(StringConstants.AUTH_HEADER_PREFIX + graphQLAccessPrefix, query);
        JSONObject result = new JSONObject(Objects.requireNonNull(response.getBody())).getJSONObject(StringConstants.JSON_DATA_KEY).getJSONObject(StringConstants.JSON_SEARCH_KEY);
        return result.toMap();
    }

    /**
     * This method returns the average time taken to resolve priority-1 issues
     *
     * @param orgUserName GitHub Organization login name
     * @return Average time taken to resolve priority-1 issues
     * @throws FeignException FeignException.Unauthorized if token is invalid, FeignException.BadRequest if FeignClient returns 400 Bad Request
     * @throws JSONException if JSON parsing is invalid
     */
    public Map<String, String> getAverageResolvingTimeOfP1Issues(String orgUserName) throws FeignException, JSONException {
        String query = String.format(getClosedP1IssuesTimeQuery, orgUserName);
        logger.info("Getting creation and closing time of priority-1 issues of organization : {}", orgUserName);

        ResponseEntity<String> response = client.getQuery(StringConstants.AUTH_HEADER_PREFIX + graphQLAccessPrefix, query);
        return AverageUtil.getAverageTime(response);
    }

    /**
     * This method returns the average time taken to resolve priority-2 issues
     *
     * @param orgUserName GitHub Organization login name
     * @return Average time taken to resolve priority-2 issues
     * @throws FeignException FeignException.Unauthorized if token is invalid, FeignException.BadRequest if FeignClient returns 400 Bad Request
     * @throws JSONException if JSON parsing is invalid
     */
    public Map<String, String> getAverageResolvingTimeOfP2Issues(String orgUserName) throws FeignException, JSONException {
        String query = String.format(getClosedP2IssuesTimeQuery, orgUserName);
        logger.info("Getting creation and closing time of priority-2 issues of organization : {}", orgUserName);

        ResponseEntity<String> response = client.getQuery(StringConstants.AUTH_HEADER_PREFIX + graphQLAccessPrefix, query);
        return AverageUtil.getAverageTime(response);
    }
}
