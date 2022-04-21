package com.polyrepo.analyzer.service;

import com.polyrepo.analyzer.dao.TrendRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class TrendService {
    private TrendRepository trendRepository;

    @Autowired
    public TrendService(TrendRepository trendRepository) {
        this.trendRepository = trendRepository;
    }
}
