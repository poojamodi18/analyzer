package com.polyrepo.analyzer.model;

import javax.persistence.*;

@Entity
@Table(name = "user_history")
public class UserHistory {

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

    @Column(name = "filter_parameter")
    private String filterParameter;

    @Column(name = "date")
    private String date;

    @Column(name = "save")
    private int save;

    @Column(name = "pin")
    private int pin;

    public UserHistory() {
    }

    public UserHistory(int userId, String title, String graphqlQuery, String filterParameter, String date, int save, int pin) {
        this.userId = userId;
        this.title = title;
        this.graphqlQuery = graphqlQuery;
        this.filterParameter = filterParameter;
        this.date = date;
        this.save = save;
        this.pin = pin;
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

    public String getFilterParameter() {
        return filterParameter;
    }

    public void setFilterParameter(String filterParameter) {
        this.filterParameter = filterParameter;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSave() {
        return save;
    }

    public void setSave(int save) {
        this.save = save;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    @Override
    public String toString() {
        return "UserHistory{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", graphqlQuery='" + graphqlQuery + '\'' +
                ", filterParameter='" + filterParameter + '\'' +
                ", date='" + date + '\'' +
                ", save=" + save +
                ", pin=" + pin +
                '}';
    }
}
