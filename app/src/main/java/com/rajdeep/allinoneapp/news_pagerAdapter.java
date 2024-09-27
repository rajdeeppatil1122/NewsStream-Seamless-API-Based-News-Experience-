package com.rajdeep.allinoneapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class news_pagerAdapter extends FragmentPagerAdapter {

    int tabcount;

    public news_pagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabcount = behavior;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){

            case 0 : {
                    return new news_home_fragment();    // The adapter will pass an object of  a fragment...
                // We are not needed to write a break statement as the return statement will terminate the program.
            }

            case 1 : {
                return new news_sports_fragment();
            }

            case 2 : {
                return new news_health_fragment();
            }

            case 3 : {
                return new news_science_fragment();
            }

            case 4 : {
                return new news_entertainment_fragment();
            }

            case 5 : {
                return new news_technology_fragment();
            }
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return tabcount;
    }
}
