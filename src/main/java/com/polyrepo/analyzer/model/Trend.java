package com.polyrepo.analyzer.model;

import javax.persistence.*;

@Entity
@Table(name = "trend")
public class Trend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "title")
    private String title;

    @Column(name = "graphql_query")
    private String graphqlQuery;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "date")
    private String date;

    public Trend() {
    }

    public Trend(int userId, String title, String graphqlQuery, String startDate, String date) {
        this.userId = userId;
        this.title = title;
        this.graphqlQuery = graphqlQuery;
        this.startDate = startDate;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGraphqlQuery() {
        return graphqlQuery;
    }

    public void setGraphqlQuery(String graphqlQuery) {
        this.graphqlQuery = graphqlQuery;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Trend{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", graphqlQuery='" + graphqlQuery + '\'' +
                ", startDate='" + startDate + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
