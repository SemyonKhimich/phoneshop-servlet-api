package com.es.phoneshop.model.review;

import java.io.Serializable;

public class Review implements Serializable {
    private String userName;
    private Integer rating;
    private String statement;
    private boolean isModerated;
    private Long id;

    public Review() {
    }

    public Review(String userName, Integer rating, String statement) {
        this.userName = userName;
        this.rating = rating;
        this.statement = statement;
        this.isModerated = false;
        this.id = -1L;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isModerated() {
        return isModerated;
    }

    public void setModerated(boolean moderated) {
        isModerated = moderated;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }
}
