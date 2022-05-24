package com.polyrepo.analyzer.service;

import com.polyrepo.analyzer.client.GraphQLClient;
import com.polyrepo.analyzer.constant.StringConstants;
import com.polyrepo.analyzer.dao.UserRepository;
import com.polyrepo.analyzer.model.User;
import feign.FeignException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class UserHomeService{
    private UserRepository userRepository;

    @Autowired
    private GraphQLClient client;

    @Value("${getCommonTrendQuery}")
    private String getCommonTrendQuery;

    @Value("${getCommonTrend}")
    private String getCommonTrend;

    @Value("${getTodayCommonTrend}")
    private String getTodayCommonTrend;

    @Value("${graphQLAccessPrefix}")
    private String graphQLAccessPrefix;

    @Autowired
    public UserHomeService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save (User user){
       return userRepository.save(user);
    }

    public User getUser (String login){
        List<User> users = userRepository.findByLogin(login);
        if(users.isEmpty()){
            return null;
        }else{
            return userRepository.findByLogin(login).get(0);
        }

    }

    public User getUserDetails(String username, String login, String avatarUrl, String url) {
        User userObj = getUser(login);
        if(userObj==null){
            userObj = new User(username,login,avatarUrl,url);
            save(userObj);
        }
        else{
            userObj.setName(username);
            userObj.setLogin(login);
            userObj.setAvatarUrl(avatarUrl);
            userObj.setUrl(url);
            save(userObj);
        }
        return userObj;
    }

    public Map<String, Object> getTrendData() throws FeignException, JSONException {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();

        DateTimeFormatter monthYearFormatter = DateTimeFormatter.ofPattern("MMM");
        DateTimeFormatter numberMonthYearFormatter = DateTimeFormatter.ofPattern("MM");
        DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yyyy");

        YearMonth thisMonth    = YearMonth.now();

        List<String> queryday = new ArrayList<>();
        for (int i = 0; i <month; i++) {
            YearMonth lastMonth = thisMonth.minusMonths(i);
            queryday.add(lastMonth.format(monthYearFormatter));
            queryday.add(lastMonth.format(yearFormatter)+"-"+ lastMonth.format(numberMonthYearFormatter)+"-01");
        }
        StringBuilder finalQuery = new StringBuilder();
        int index = 0;
        for (int i = 0; i <month; i++) {
            String query = String.format(getCommonTrend, queryday.get(index), queryday.get(index+1),
                    queryday.get(index), queryday.get(index+1), queryday.get(index), queryday.get(index+1),
                    queryday.get(index), queryday.get(index+1), queryday.get(index), queryday.get(index+1));
            index +=2;
            finalQuery.append(query);
        }
        String lastQuery = String.format(getCommonTrendQuery,finalQuery+getTodayCommonTrend);
        ResponseEntity<String> response;

        response = client.getQuery(StringConstants.AUTH_HEADER_PREFIX + graphQLAccessPrefix, lastQuery);
        JSONObject result = new JSONObject(Objects.requireNonNull(response.getBody())).getJSONObject(StringConstants.JSON_DATA_KEY);
        return result.toMap();
    }
}
