package com.rajdeep.allinoneapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ocr_KuchBhiActivity extends AppCompatActivity {
    ImageView imageView1;
    TextView back_stack_counter;
    int initializer;
    Intent cropIntent = new Intent("com.android.camera.action.CROP");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr_kuch_bhi);

        initializer = getIntent().getExtras().getInt("TabCounter");

        imageView1 = findViewById(R.id.imageView1);
        back_stack_counter = findViewById(R.id.back_stack_counter);

        back_stack_counter.setText(String.valueOf(initializer));

        int min = 0;
        int max = 3;
        int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);

        if(random_int == 2){
            imageView1.setImageResource(R.drawable.kuchbhi);
        }
        else if (random_int == 1){
            imageView1.setImageResource(R.drawable.carry_paisa_barbad);
        }
        else if(random_int == 0){
            imageView1.setImageResource(R.drawable.dontangryme);
        }
        else{
            imageView1.setImageResource(R.drawable.t_tu_mama);
        }


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


    }   // onCreate ends here...


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