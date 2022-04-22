package com.polyrepo.analyzer.dao;

import com.polyrepo.analyzer.model.Trend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrendRepository extends JpaRepository<Trend,Integer> {
    List<Trend> getByUserId(Integer userId);
}
