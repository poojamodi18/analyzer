package com.polyrepo.analyzer.dao;

import com.polyrepo.analyzer.model.Trend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrendRepository extends JpaRepository<Trend,Integer> {
}
