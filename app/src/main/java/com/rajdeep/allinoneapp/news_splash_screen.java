
package com.rajdeep.allinoneapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class news_splash_screen extends AppCompatActivity {
    LottieAnimationView lottieAnimationView, loader;
    TextView splashText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_splash_screen);

        lottieAnimationView = findViewById(R.id.lottieAnimationView);
        loader = findViewById(R.id.loader);
        splashText = findViewById(R.id.splashScreenAppTitle);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent iHome = new Intent(news_splash_screen.this, MainActivity.class);
                startActivity(iHome);
                finish();       // finish() this will clear the backtrace of the SplashActivity Activity from the stack
                finishAffinity();
            }
        }, 200);

        lottieAnimationView.playAnimation();
        loader.playAnimation();

        Animation alpha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.spalsh_screen_text_animation);
        splashText.startAnimation(alpha);

    }
}