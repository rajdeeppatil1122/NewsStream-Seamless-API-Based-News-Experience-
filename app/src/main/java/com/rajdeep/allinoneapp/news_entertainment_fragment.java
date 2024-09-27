package com.rajdeep.allinoneapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class news_entertainment_fragment extends Fragment {

    private RecyclerView recyclerViewEntertainment;
    private news_RecyclerViewAdapter recyclerViewAdapter;
    private ArrayList<news_ModelClass> arrayList;

    String newsApiKey = "bfec234ad74e48728626a8d94c4d4627";
    private String country = "in";
    private String category = "entertainment";


    public news_entertainment_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_news_entertainment_fragment, null);

        arrayList = new ArrayList<>();
        recyclerViewEntertainment = v.findViewById(R.id.newsRecyclerViewEntertainment);
        recyclerViewEntertainment.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerViewAdapter = new news_RecyclerViewAdapter(getContext(), arrayList);
        recyclerViewEntertainment.setAdapter(recyclerViewAdapter);

        findNews();

        return  v;
    }

    private void findNews() {        // This is the IMPORTANT method which will fetch the data from API into the App. (This will get the retrofit object which will in turn gives us the API Interface.
        news_APIUtilities.getApiInterface().getCategoryNews(country, category, 10, newsApiKey).enqueue(new Callback<news_MainNews>() {
            @Override
            public void onResponse(Call<news_MainNews> call, Response<news_MainNews> response) {    //  Will store the science news data in news_MainNews objects of that class and only it will fetch the data of science category.
                if (response.isSuccessful()) {
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