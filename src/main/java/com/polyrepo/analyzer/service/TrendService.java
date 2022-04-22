package com.polyrepo.analyzer.service;

import com.polyrepo.analyzer.dao.TrendRepository;
import com.polyrepo.analyzer.model.Trend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TrendService {
    private TrendRepository trendRepository;

    @Autowired
    public TrendService(TrendRepository trendRepository) {
        this.trendRepository = trendRepository;
    }

    public Map<String, Object> getTrendData(int userId, String title) {

        List<Trend> trendList = trendRepository.getByUserId(userId);
        Trend trend = new Trend();
        String message = "Not Found";
        for (Trend t : trendList) {
            if(t.getTitle().equals(title)){
                trend = t;
                message = "Found";
                break;
            }
        }

        Map<String, Object> data = new HashMap<>();
        data.put("message",message);
        data.put("trend",trend);

        return data;
    }
}
