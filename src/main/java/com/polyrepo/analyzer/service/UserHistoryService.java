package com.polyrepo.analyzer.service;

import com.polyrepo.analyzer.client.GraphQLClient;
import com.polyrepo.analyzer.constant.StringConstants;
import com.polyrepo.analyzer.dao.UserHistoryRepository;
import com.polyrepo.analyzer.model.UserHistory;
import com.polyrepo.analyzer.util.AverageUtil;
import feign.FeignException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserHistoryService {
    private UserHistoryRepository userHistoryRepository;

    @Autowired
    private GraphQLClient client;

    @Value("${getPullRequestNotUpdatedByDaysQuery}")
    private String getPullRequestNotUpdatedByDaysQuery;

    @Value("${getUnMergedPullRequestByDayQuery}")
    private String getUnMergedPullRequestByDayQuery;

    @Value("${getPriority1IssuesOpenedBeforeXDaysQuery}")
    private String getPriority1IssuesOpenedBeforeXDaysQuery;

    @Value("${getClosedP1IssuesTimeQuery}")
    private String getClosedP1IssuesTimeQuery;

    @Value("${getClosedP2IssuesTimeQuery}")
    private String getClosedP2IssuesTimeQuery;

    @Value("${getDefaultBranchQuery}")
    private String getDefaultBranchQuery;

    @Value("${graphQLAccessPrefix}")
    private String graphQLAccessPrefix;

    @Autowired
    public UserHistoryService(UserHistoryRepository userHistoryRepository) {
        this.userHistoryRepository = userHistoryRepository;
    }

    public void saveActivity(int userId, String title, String query, String filter, int save, int pin) {
        java.util.Date dt = new java.util.Date();

        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String currentTime = sdf.format(dt);
        UserHistory userHistory = new UserHistory(userId,title,query,filter,currentTime,save,pin);
        userHistoryRepository.save(userHistory);
    }


    public Map<String,UserHistory> getUserHistory(Integer userId) {
        List<UserHistory> userHistoryList = userHistoryRepository.getByUserId(userId);
        Map<String, UserHistory> userHistoryMap = new HashMap<>();
        int index=0;
        for (UserHistory userHistory : userHistoryList) {
            userHistoryMap.put(String.valueOf(index),userHistory);
            index++;
        }
        return userHistoryMap;
    }

    public Map<String,Object> getResultOfRecent(String type, String query) throws FeignException, JSONException {

        ResponseEntity<String> response;
        response = client.getQuery(StringConstants.AUTH_HEADER_PREFIX + graphQLAccessPrefix, query);
        if(type.equals("average")){
            String result= AverageUtil.getAverageTime(response).get(StringConstants.JSON_MESSAGE_KEY_STRING);
            return Collections.singletonMap(StringConstants.JSON_MESSAGE_KEY_STRING,result);
        }else{
            JSONObject result = new JSONObject(Objects.requireNonNull(response.getBody())).getJSONObject(StringConstants.JSON_DATA_KEY);
            return result.toMap();
        }

    }
}
