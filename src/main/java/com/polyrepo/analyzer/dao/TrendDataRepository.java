package com.polyrepo.analyzer.dao;

import com.polyrepo.analyzer.model.TrendData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrendDataRepository extends JpaRepository<TrendData,Integer> {
}
