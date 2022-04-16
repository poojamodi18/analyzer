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
public class OrganizationService {

    @Autowired
    private GraphQLClient client;

    @Value("${getOrganizationListQuery}")
    private String getOrganizationListQuery;

    @Value("${getOrganizationProfileQuery}")
    private String getOrganizationProfileQuery;

    @Value("${graphQLAccessPrefix}")
    private String graphQLAccessPrefix;

    private final Logger logger = LoggerFactory.getLogger(OrganizationService.class);

    /**
     * This method with fetch and returns the list of organizations that have the same name as mentioned name
     *
     * @param name  GitHub Organization login name
     * @return List of organization
     * @throws FeignException FeignException.Unauthorized if token is invalid, FeignException.BadRequest if FeignClient returns 400 Bad Request
     * @throws JSONException if JSON parsing is invalid
     */
    public Map<String, Object> getOrganizationList(String name) throws FeignException, JSONException {
        String query = String.format(getOrganizationListQuery, name);
        ResponseEntity<String> response;
        logger.info("Getting list of organizations with \" {} \" in name",name);
        response = client.getQuery(StringConstants.AUTH_HEADER_PREFIX + graphQLAccessPrefix, query);
        JSONObject result = new JSONObject(Objects.requireNonNull(response.getBody())).getJSONObject(StringConstants.JSON_DATA_KEY).getJSONObject(StringConstants.JSON_SEARCH_KEY);
        return result.toMap();
    }

    /**
     * This method will fetch the name, url and avatar image url of the specified organization
     *
     * @param orgUserName GitHub Organization login name
     * @param token       GitHub personal access token
     * @return Profile details of specified organization
     * @throws FeignException FeignException.Unauthorized if token is invalid, FeignException.BadRequest if FeignClient returns 400 Bad Request
     * @throws JSONException if JSON parsing is invalid
     */
    public Map<String, Object> getOrganizationProfile(String orgUserName, String token) throws FeignException, JSONException {
        String query = String.format(getOrganizationProfileQuery, orgUserName);
        ResponseEntity<String> response;
        logger.info("Getting Organization profile of : {}" , orgUserName);
        response = client.getQuery(StringConstants.AUTH_HEADER_PREFIX + token, query);
        JSONObject result = new JSONObject(Objects.requireNonNull(response.getBody())).getJSONObject(StringConstants.JSON_DATA_KEY);
        return result.toMap();
    }
}