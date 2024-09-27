package com.rajdeep.allinoneapp;

import java.util.ArrayList;

public class news_MainNews {
    private String status;
    private String totalResults;
    private ArrayList<news_ModelClass> articles;

    public news_MainNews(String status, String totalResults, ArrayList<news_ModelClass> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public ArrayList<news_ModelClass> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<news_ModelClass> articles) {
        this.articles = articles;
    }
}
