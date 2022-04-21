package com.polyrepo.analyzer.service;

import com.polyrepo.analyzer.dao.UserHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserHistoryService {
    private UserHistoryRepository userHistoryRepository;

    @Autowired
    public UserHistoryService(UserHistoryRepository userHistoryRepository) {
        this.userHistoryRepository = userHistoryRepository;
    }
}
