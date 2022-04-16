package com.polyrepo.analyzer.service;

import com.polyrepo.analyzer.client.GraphQLClient;
import com.polyrepo.analyzer.constant.StringConstants;
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
public class RepositoryService {


    @Autowired
    private GraphQLClient client;

    @Value("${getRepositoryCountQuery}")
    private String getRepositoryCountQuery;

    @Value("${getRepositoriesQueryUnder100}")
    private String getRepositoriesQueryUnder100;

    @Value("${getRepositoriesQueryOver100}")
    private String getRepositoriesQueryOver100;

    @Value("${getRepositoriesByCursorQuery}")
    private String getRepositoriesByCursorQuery;

    @Value("${getRepositoriesByNameQuery}")
    private String getRepositoriesByNameQuery;

    @Value("${graphQLAccessPrefix}")
    private String graphQLAccessPrefix;

    private final Logger logger = LoggerFactory.getLogger(RepositoryService.class);

    /**
     * Returns the total number of repositories the organization has
     *
     * @param orgUserName GitHub Organization login name
     * @param token       GitHub personal access token
     * @return Total count of repositories of specified organization
     */
    private int getRepositoryCount(String orgUserName, String token) {
        String query = String.format(getRepositoryCountQuery, orgUserName);
        ResponseEntity<String> response = client.getQuery(StringConstants.AUTH_HEADER_PREFIX + graphQLAccessPrefix, query);
        return new JSONObject(response.getBody()).getJSONObject("data").getJSONObject("organization").getJSONObject("repositories").getInt("totalCount");
    }

    /**
     * This method returns the repository list according to the number of repositories of the organization
     * if the total number of repositories is greater than a hundred then
     * the query with pagination will get executed and return the list of repositories with pagination
     *
     * @param orgUserName GitHub Organization login name
     * @return List of repositories of specified organization
     * @throws FeignException FeignException.Unauthorized if token is invalid, FeignException.BadRequest if FeignClient returns 400 Bad Request
     * @throws JSONException if JSON parsing is invalid
     */
    public Map<String, Object> getRepositories(String orgUserName) throws FeignException, JSONException {
        ResponseEntity<String> response;

        int count = getRepositoryCount(orgUserName, graphQLAccessPrefix);
        logger.info("Total count of repositories: {}" , count);
        if (count <= 100) {
            logger.info("Getting all the repositories");
            String query = String.format(getRepositoriesQueryUnder100, orgUserName, count);
            response = client.getQuery(StringConstants.AUTH_HEADER_PREFIX + graphQLAccessPrefix, query);
            JSONObject result = new JSONObject(Objects.requireNonNull(response.getBody())).getJSONObject(StringConstants.JSON_DATA_KEY).getJSONObject(StringConstants.JSON_ORGANIZATION_KEY).getJSONObject(StringConstants.JSON_REPOSITORIES_KEY);
            return result.toMap();
        } else {
            logger.info("Getting first 100 repositories");
            String query = String.format(getRepositoriesQueryOver100, orgUserName);
            response = client.getQuery(StringConstants.AUTH_HEADER_PREFIX + graphQLAccessPrefix, query);
            JSONObject result = new JSONObject(Objects.requireNonNull(response.getBody())).getJSONObject(StringConstants.JSON_DATA_KEY).getJSONObject(StringConstants.JSON_ORGANIZATION_KEY).getJSONObject(StringConstants.JSON_REPOSITORIES_KEY);
            return result.toMap();
        }
    }

    /**
     * This method fetches the continuing list of repositories of the specified organization using the end cursor of the
     * previous list of repositories and returns the fetched list with pagination
     *
     * @param orgUserName GitHub Organization login name
     * @param endCursor   End cursor of repository list json
     * @return List of Repository of specified organization with pagination
     * @throws FeignException FeignException.Unauthorized if token is invalid, FeignException.BadRequest if FeignClient returns 400 Bad Request
     * @throws JSONException if JSON parsing is invalid
     */
    public Map<String, Object> getRepositoriesByCursor(String orgUserName, String endCursor) throws FeignException, JSONException {
        String query = String.format(getRepositoriesByCursorQuery, orgUserName, endCursor);
        ResponseEntity<String> response;

        logger.info("Getting 100 repositories after the cursor \" {} \"",endCursor);
        response = client.getQuery(StringConstants.AUTH_HEADER_PREFIX + graphQLAccessPrefix, query);
        JSONObject result = new JSONObject(Objects.requireNonNull(response.getBody())).getJSONObject(StringConstants.JSON_DATA_KEY).getJSONObject(StringConstants.JSON_ORGANIZATION_KEY).getJSONObject(StringConstants.JSON_REPOSITORIES_KEY);
        return result.toMap();
    }

    /**
     * This method fetches the list of repositories which are having the same name as the mentioned name
     *
     * @param orgUserName GitHub Organization login name
     * @param repoName    Repository name
     * @return List of repository of specified organization having the same name as specified name
     * @throws feign.FeignException FeignException.Unauthorized if token is invalid, FeignException.BadRequest if FeignClient returns 400 Bad Request
     * @throws JSONException if JSON parsing is invalid
     */
    public Map<String, Object> getRepositoriesByName(String orgUserName, String repoName) throws FeignException, JSONException {
        String query = String.format(getRepositoriesByNameQuery, orgUserName, repoName);
        ResponseEntity<String> response;
        logger.info("Getting repositories by name : {}", repoName);
        response = client.getQuery(StringConstants.AUTH_HEADER_PREFIX + graphQLAccessPrefix, query);
        JSONObject result = new JSONObject(Objects.requireNonNull(response.getBody())).getJSONObject(StringConstants.JSON_DATA_KEY).getJSONObject(StringConstants.JSON_SEARCH_KEY);
        return result.toMap();
    }

}
