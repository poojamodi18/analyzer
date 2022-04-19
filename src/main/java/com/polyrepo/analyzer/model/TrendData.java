package com.polyrepo.analyzer.model;

import javax.persistence.*;

@Entity
@Table(name = "trend_data")
public class TrendData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "trend_id")
    private int trendId;

    @Column(name = "trend_data_count")
    private int trendDataCount;

    @Column(name = "repository")
    private String repository;

    @Column(name = "date")
    private String date;

    public TrendData() {
    }

    public TrendData(int trendId, int trendDataCount, String repository, String date) {
        this.trendId = trendId;
        this.trendDataCount = trendDataCount;
        this.repository = repository;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTrendId() {
        return trendId;
    }

    public void setTrendId(int trendId) {
        this.trendId = trendId;
    }

    public int getTrendDataCount() {
        return trendDataCount;
    }

    public void setTrendDataCount(int trendDataCount) {
        this.trendDataCount = trendDataCount;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "TrendData{" +
                "id=" + id +
                ", trendId=" + trendId +
                ", trendDataCount=" + trendDataCount +
                ", repository='" + repository + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
