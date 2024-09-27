package com.rajdeep.allinoneapp;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Objects;

public class ocr_ResultActivity extends AppCompatActivity {

    LottieAnimationView ocrReselectImg, ocrEditTxt, ocrCopyTxt, ocrCopyTxtTickMark;
    TextView textView, txtTakeAction, back_stack_counter;
    Animation scaleAnime;
    public int initializer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_ocr_result);

        ocr_ButtonClickAnimationClass obj = new ocr_ButtonClickAnimationClass();    // Object of button animation class is created...

        String value = getIntent().getExtras().getString("TextResult");
        initializer = getIntent().getExtras().getInt("TabCounter");
        if(value.equals("")){
            Intent intent = new Intent(ocr_ResultActivity.this, ocr_KuchBhiActivity.class);
            intent.putExtra("TabCounter", initializer);
            startActivity(intent);
            finish();
        }
        else {
            ocrReselectImg = findViewById(R.id.ocrReselectImg);
            ocrEditTxt = findViewById(R.id.ocrEditTxt);
            ocrCopyTxt = findViewById(R.id.ocrCopyTxt);
            textView = findViewById(R.id.resultTextView);
            txtTakeAction = findViewById(R.id.txtTakeAction);
            ocrCopyTxtTickMark = findViewById(R.id.ocrCopyTxtTickMark);
            back_stack_counter = findViewById(R.id.back_stack_counter);

            scaleAnime = AnimationUtils.loadAnimation(ocr_ResultActivity.this, R.anim.scale_anim2);
            txtTakeAction.startAnimation(scaleAnime);

            ocrReselectImg.playAnimation();
            ocrEditTxt.playAnimation();
            ocrCopyTxt.playAnimation();

            textView.setText(value);
            back_stack_counter.setText(String.valueOf(initializer));

            // Clicked On Copy Button...
            ocrCopyTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*------- Animation Work------*/
                    obj.ButtonClickAnimation(R.color.light_blue_for_copy_text, R.color.green_color_when_anything_clicked, ocrCopyTxt, ocr_ResultActivity.this);

                        // Tick-Mark Animation...
                    ocrCopyTxtTickMark.playAnimation();
                    ocrCopyTxtTickMark.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ocrCopyTxtTickMark.setVisibility(View.INVISIBLE);
                        }
                    }, 2720);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ocr_ResultActivity.this, "Text Copied Successfully!", Toast.LENGTH_SHORT).show();
                        }
                    }, 400);
                    /*------- Animation Work End------*/


                    // Actual Work...
                    CopyToClipBoard(value, ocr_ResultActivity.this);
                }
            });     // end of ocrCopyTxt.setOnClickListener...


            // Clicked on Edit Text button...
            ocrEditTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*------- Animation Work------*/
                    obj.ButtonClickAnimation(R.color.light_yellow_for_edit_text, R.color.green_color_when_anything_clicked, ocrEditTxt, ocr_ResultActivity.this);
                    /*------- Animation Work End------*/
                    Intent intent = new Intent(ocr_ResultActivity.this, ocr_EditActivity.class);
                    intent.putExtra("textToBeEdited", value);
                    initializer++;
                    intent.putExtra("TabCounter", initializer);
                    startActivity(intent);

                }
            });

            // When reselect button is clicked...
            ocrReselectImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*------- Animation Work------*/
                    obj.ButtonClickAnimation(R.color.light_blue_for_copy_text, R.color.green_color_when_anything_clicked, ocrReselectImg, ocr_ResultActivity.this);
                    /*------- Animation Work End------*/

                    // Create the object of AlertDialog Builder class
                    AlertDialog.Builder builder = new AlertDialog.Builder(ocr_ResultActivity.this);

                    /*------------ALERT BOX CODE START-----------*/
                    // Set the message show for the Alert time
                    builder.setMessage("Do you really want to Re-Capture Image ?\n\nYour current 'Text-Extracted' will be lost!");

                    // Set Alert Title
                    builder.setTitle("Alert !");

                    // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
                    builder.setCancelable(false);

                    // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
                    builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                        // IMPORTANT WORK---
                        Intent intent = new Intent(ocr_ResultActivity.this, OCRAppMainActivity_02.class);
                        intent.putExtra("TabCounter", initializer);
                        intent.putExtra("flag2", true);
                        startActivity(intent);
                        finish();   // Pops all activities from backstack...
                    });

                    // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
                    builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                        // If user click no then dialog box is canceled.
                        dialog.cancel();
                    });

                    // Create the Alert dialog
                    AlertDialog alertDialog = builder.create();
                    // Show the Alert Dialog box
                    alertDialog.show();
                    /*------------ALERT BOX CODE END-----------*/
                }
            });

        }   // End of else...


        /*----------- USING TOOLBAR --------------*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.ocrResultActivityToolbar);
        ImageView backArrow = findViewById(R.id.back_arrow);    // Finding back arrow id...
        setSupportActionBar(toolbar);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        /*---------------- END TOOLBAR -------------*/

    }       // End of onCreate method...

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        In Activity2, use setResult for sending data back:
        Intent intent = new Intent();
        initializer--;
//        Toast.makeText(this, String.valueOf(initializer), Toast.LENGTH_SHORT).show();
        intent.putExtra("TabCounter", initializer);
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

        finish();
    }

    // Copy Method
    private void CopyToClipBoard(String text, Context context){
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(context.CLIPBOARD_SERVICE);   // Interface to global information about an application environment. This is an abstract class whose implementation is provided by the Android system. It allows access to application-specific resources and classes, as well as up-calls for application-level operations such as launching activities, broadcasting and receiving intents, etc.
        ClipData clip = ClipData.newPlainText("Data to be copied", text);
        clipboardManager.setPrimaryClip(clip);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int temp = initializer;
        initializer = getIntent().getExtras().getInt("TabCounter");
        if(String.valueOf(initializer) == null){
            initializer = temp;
        }
        back_stack_counter.setText(String.valueOf(initializer));
    }
}       // end of ocr_ResultActivity class...