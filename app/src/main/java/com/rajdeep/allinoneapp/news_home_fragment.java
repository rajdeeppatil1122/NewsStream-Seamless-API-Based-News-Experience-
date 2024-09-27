package com.rajdeep.allinoneapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class news_home_fragment extends Fragment {

    private RecyclerView recyclerViewHome;
    private news_RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<news_ModelClass> arrayList;

    String newsApiKey = "bfec234ad74e48728626a8d94c4d4627";
    String country = "in";


    public news_home_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_news_home_fragment, null);

        arrayList = new ArrayList<>();
        recyclerViewHome = v.findViewById(R.id.newsRecyclerViewHome);
        recyclerViewHome.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerViewAdapter = new news_RecyclerViewAdapter(getContext(), arrayList);
        recyclerViewHome.setAdapter(recyclerViewAdapter);

        findNews();

        return  v;
    }

    private void findNews(){        // This is the IMPORTANT method which will fetch the data from API into the App. (This will get the retrofit object which will in turn gives us the API Interface.
        news_APIUtilities.getApiInterface().getNews(country, 10, newsApiKey).enqueue(new Callback<news_MainNews>() {
            @Override
            public void onResponse(Call<news_MainNews> call, Response<news_MainNews> response) {    // This enqueue & onResponse method of retrofit will do HTTP request and will take response and it will set the response(data) on the object of news_MainNews.java class using its constructor.
                if(response.isSuccessful()){
                    arrayList.addAll(response.body().getArticles());    // Fetching articles from news_MainNews object which had set just upwards.
                    recyclerViewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<news_MainNews> call, Throwable t) {

            }
        });

    }

}