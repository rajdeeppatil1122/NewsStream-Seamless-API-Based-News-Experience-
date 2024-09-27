package com.rajdeep.allinoneapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class NewsAppMainActivity_01 extends AppCompatActivity {

    TabLayout newsTabLayout;
    TabItem newsHome, newsSports, newsHealth, newsEntertainment, newsScience, newsTech;
    news_pagerAdapter newsPagerAdapter;
    Toolbar newsToolbar;
    ViewPager newsViewPager;

    String newsApi = "bfec234ad74e48728626a8d94c4d4627";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_app_main01);
//        getSupportActionBar().hide();         <--- We cannot use this as a replacement, cause this will just hide the default toolbar, it will not remove it.

        newsTabLayout = findViewById(R.id.newsTabLayout);
        newsHome = findViewById(R.id.newsHome);
        newsSports = findViewById(R.id.newsSports);
        newsHealth = findViewById(R.id.newsHealth);
        newsEntertainment = findViewById(R.id.newsEntertainment);
        newsScience = findViewById(R.id.newsScience);
        newsTech = findViewById(R.id.newsTechnology);

        newsTabLayout = findViewById(R.id.newsTabLayout);
        newsToolbar = findViewById(R.id.newsToolbar);
        newsViewPager = findViewById(R.id.newsViewPager);

        // Setting the toolbar...
        setSupportActionBar(newsToolbar);

        // Giving the context of fragmentManager to set the fragments automatically...
        newsPagerAdapter = new news_pagerAdapter(getSupportFragmentManager(), 6);
        newsViewPager.setAdapter(newsPagerAdapter);

//        newsTabLayout.setupWithViewPager(newsViewPager);  <--- Do not use this method while using the below tabSelectedListner & pageChangeListner

        newsTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                newsViewPager.setCurrentItem(tab.getPosition());        // To change the fragments when the tab is changed by clicking on it.
                if(tab.getPosition() == 0 || tab.getPosition() == 1 || tab.getPosition() == 2 || tab.getPosition() == 3 || tab.getPosition() == 4 ||
                        tab.getPosition() == 5){
                    newsPagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
            newsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(newsTabLayout));     // To change the tabs when the fragment is changed by swiping with fingers.

    }       // onCreate ends here...


    @Override
    public void onBackPressed() {
        if (newsViewPager.getCurrentItem() == 0) {

            super.onBackPressed();
            finish();
        }
        else{
            //If any other tab is open, then switch to first tab
            newsViewPager.setCurrentItem(0);
        }
    }


}