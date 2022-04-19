package com.polyrepo.analyzer.dao;

import com.polyrepo.analyzer.model.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHistoryRepository extends JpaRepository<UserHistory,Integer> {
}
