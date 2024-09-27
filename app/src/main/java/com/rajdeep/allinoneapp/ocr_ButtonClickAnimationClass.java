package com.rajdeep.allinoneapp;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.airbnb.lottie.LottieAnimationView;

public class ocr_ButtonClickAnimationClass {

    Animation shake;

    // Button Animation method...
    public void ButtonClickAnimation(int colorFrom, int colorTo, LottieAnimationView lottieAnimationView, Context context){
        shake = AnimationUtils.loadAnimation(context, R.anim.shake);
        lottieAnimationView.startAnimation(shake);

        int colorFrom1 = context.getResources().getColor(colorFrom);
        int colorTo1 = context.getResources().getColor(colorTo);
        int colorFrom2 = context.getResources().getColor(colorTo);    // for reverse
        int colorTo2 = context.getResources().getColor(colorFrom);      // for reverse
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom1, colorTo1); // reverse
        ValueAnimator colorAnimation2 = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom2, colorTo2);    // reverse
        colorAnimation.setDuration(650); // milliseconds
        colorAnimation2.setDuration(650); // milliseconds (for reverse)

        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                lottieAnimationView.setBackgroundColor((int) animator.getAnimatedValue());
            }

        });
        colorAnimation.start();

        colorAnimation2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                lottieAnimationView.setBackgroundColor((int) animation.getAnimatedValue());
            }
        });
        colorAnimation2.start();
    }

}
