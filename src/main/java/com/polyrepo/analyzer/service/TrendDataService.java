package com.polyrepo.analyzer.service;

import com.polyrepo.analyzer.dao.TrendDataRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class TrendDataService {
    private TrendDataRepository trendDataRepository;

    @Autowired
    public TrendDataService(TrendDataRepository trendDataRepository) {
        this.trendDataRepository = trendDataRepository;
    }
}
