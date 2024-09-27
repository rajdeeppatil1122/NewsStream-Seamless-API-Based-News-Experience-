package com.rajdeep.allinoneapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

public class news_WebView extends AppCompatActivity {
    Toolbar toolbar;
    WebView webView;
    LottieAnimationView lottieProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_web_view);

        // Setting custom toolbar...
        toolbar = findViewById(R.id.newsToolbar);
        setSupportActionBar(toolbar);

        // Finding other views...
        webView = findViewById(R.id.newsWebView);
        lottieProgressBar = findViewById(R.id.lottieProgressBar);

        // Setting invisible initially
        lottieProgressBar.setVisibility(View.VISIBLE);
        lottieProgressBar.playAnimation();

        // Getting Data using bundle passing...
        String value = getIntent().getExtras().getString("url");

        // Using a java thread to wait for some time and then load the url (so that the progress bar will be visible).
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                webView.loadUrl(value);
            /* We do not have to pause the animation here because it will be handled by onPageFinished method of setWebViewClient method. */
            }
        },1500);


        // For opening the web pages inside the same app instead of opening the browser...
        webView.setWebViewClient(new WebViewClient(){
            // For managing the progress bar.....***************
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                lottieProgressBar.setVisibility(View.VISIBLE);
                lottieProgressBar.playAnimation();
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                lottieProgressBar.setVisibility(View.GONE);
                lottieProgressBar.pauseAnimation();
                super.onPageFinished(view, url);
            }
            // ***************************************************
        });
    }

    // For managing the stack...
    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }
        else{
            super.onBackPressed();
            // finishAffinity();   //Optional...
        }
    }

}