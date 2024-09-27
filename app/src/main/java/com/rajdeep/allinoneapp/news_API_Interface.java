package com.rajdeep.allinoneapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface news_API_Interface {
    String BASE_URL = "https://newsapi.org/v2/";

    @GET("top-headlines")
    Call<news_MainNews> getNews(                // An invocation of a Retrofit method that sends a request to a webserver and returns a response.
            @Query("country") String country,   // Each call yields its own HTTP request and response pair.
            @Query("pageSize") int pagesize,
            @Query("apiKey") String apikey
    );


    @GET("top-headlines")
    Call<news_MainNews> getCategoryNews(
            @Query("country") String country,
            @Query("category") String category,
            @Query("pageSize") int pagesize,
            @Query("apiKey") String apikey
    );


}
