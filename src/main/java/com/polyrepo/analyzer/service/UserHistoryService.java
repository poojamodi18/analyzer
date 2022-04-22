package com.polyrepo.analyzer.service;

import com.polyrepo.analyzer.dao.UserHistoryRepository;
import com.polyrepo.analyzer.model.UserHistory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserHistoryService {
    private UserHistoryRepository userHistoryRepository;

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


    public Map<String,Object> getUserHistory(Integer userId) {
        List<UserHistory> userHistoryList = userHistoryRepository.getByUserId(userId);
        Map<String, UserHistory> userHistoryMap = new HashMap<>();
        String message = "";
        for (UserHistory history: userHistoryList) {
            if(history.getUserId()==userId){
                message = "Found";
                userHistoryMap.put(String.valueOf(history.getId()),history);
            }
            else{
                message = "No Recent History available";
            }
        }

        Map<String, Object> data = new HashMap<>();
        data.put("data",userHistoryMap);
        data.put("message",message);
        return data;
    }
}
