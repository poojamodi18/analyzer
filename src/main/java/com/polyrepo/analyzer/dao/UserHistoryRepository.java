package com.polyrepo.analyzer.dao;

import com.polyrepo.analyzer.model.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserHistoryRepository extends JpaRepository<UserHistory,Integer> {
    List<UserHistory> getByUserId(int userId);
}
