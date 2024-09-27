package com.rajdeep.allinoneapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

public class ocr_EditActivity extends AppCompatActivity {
    EditText editText;
    LottieAnimationView copyLottie;
    TextView back_stack_counter;
    int initializer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        setContentView(R.layout.activity_ocr_edit);

        editText = findViewById(R.id.editText);
        copyLottie = findViewById(R.id.copyLottie);
        back_stack_counter = findViewById(R.id.back_stack_counter);

        String value = getIntent().getExtras().getString("textToBeEdited");
        initializer = getIntent().getExtras().getInt("TabCounter");

        editText.setText(value);
        back_stack_counter.setText(String.valueOf(initializer));


        copyLottie.playAnimation();

        copyLottie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Animation Work */
                ocr_ButtonClickAnimationClass obj = new ocr_ButtonClickAnimationClass();
                obj.ButtonClickAnimation(R.color.light_blue_for_copy_text, R.color.green_color_when_anything_clicked, copyLottie, ocr_EditActivity.this);
                /* AW Done */

                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(ocr_EditActivity.this.CLIPBOARD_SERVICE);   // Interface to global information about an application environment. This is an abstract class whose implementation is provided by the Android system. It allows access to application-specific resources and classes, as well as up-calls for application-level operations such as launching activities, broadcasting and receiving intents, etc.
                ClipData clip = ClipData.newPlainText("Data to be copied", editText.getText().toString());
                clipboardManager.setPrimaryClip(clip);

                Toast.makeText(ocr_EditActivity.this, "Edited Text Copied To Clip-Board!", Toast.LENGTH_SHORT).show();
            }
        });


        /*----------- USING TOOLBAR --------------*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.ocrEditActivityToolbar);
        ImageView backArrow = findViewById(R.id.back_arrow);    // Finding back arrow id...
        setSupportActionBar(toolbar);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();    // This also works fine...
            }
        });
        /*---------------- END TOOLBAR -------------*/


    }   // onCreate Method ends here...

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        In Activity2, use setResult for sending data back:
        Intent intent = new Intent();
        initializer--;
        intent.putExtra("TabCounter", initializer);
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

        finish();
    }

}