package com.rajdeep.allinoneapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class news_APIUtilities {        // For creating the RETROFIT object...
    private static Retrofit retrofit=null;

    public static news_API_Interface getApiInterface(){     // Retrofit adapts a Java interface to HTTP calls by using annotations on the declared methods to define how requests are made.
                                                            // Create instances using the builder and pass your interface to 'create'(method) to generate an implementation.

        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(news_API_Interface.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(news_API_Interface.class);
    }

}
