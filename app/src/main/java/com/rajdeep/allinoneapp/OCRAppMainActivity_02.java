package com.rajdeep.allinoneapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OCRAppMainActivity_02 extends AppCompatActivity {

    ImageView imageView1, imageView2;
    TextView back_stack_counter;
    LottieAnimationView lottieAnimationCamera, lottieAnimationGallery, ocrIntroLottie;
    ocr_ButtonClickAnimationClass obj = new ocr_ButtonClickAnimationClass();
    int initializer;
    Intent intent;

    private static final int REQUEST_CAMERA_CODE = 100;
//    private static final int CAMERA_PIC_REQUEST = 112;
    private static final int GALLERY_PIC_REQUEST = 120;
    Uri photoURI;
    File photoFile;
    int flag = 0;
    boolean flag2 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        setContentView(R.layout.activity_ocrapp_main02);

        imageView1 = findViewById(R.id.ocrLine1);
        imageView2 = findViewById(R.id.ocrLine2);
        lottieAnimationCamera = findViewById(R.id.ocrCameraLottie);
        lottieAnimationGallery = findViewById(R.id.ocrGalleryLottie);
        ocrIntroLottie = findViewById(R.id.ocrIntroLottie);

        Animation lineAnimation = AnimationUtils.loadAnimation(OCRAppMainActivity_02.this, R.anim.line_in_left_to_right_animation);
        Animation scaleAnimation = AnimationUtils.loadAnimation(OCRAppMainActivity_02.this, R.anim.scale_animation_ocr_textview);

        // Custom Animations...
        imageView1.startAnimation(lineAnimation);
        imageView2.startAnimation(lineAnimation);
        lottieAnimationGallery.startAnimation(lineAnimation);
        lottieAnimationCamera.startAnimation(lineAnimation);
        ocrIntroLottie.startAnimation(scaleAnimation);

        // for lottie...
        lottieAnimationGallery.playAnimation();
        lottieAnimationCamera.playAnimation();
        ocrIntroLottie.playAnimation();


        lottieAnimationCamera = findViewById(R.id.ocrCameraLottie);
        lottieAnimationGallery = findViewById(R.id.ocrGalleryLottie);
        back_stack_counter = findViewById(R.id.back_stack_counter);

        //getting intent...
        initializer = getIntent().getIntExtra("TabCounter", 0);
        flag2 = getIntent().getBooleanExtra("flag2", false);
        back_stack_counter.setText(String.valueOf(initializer));

        // This will handle the runtime permissions...
        if(ContextCompat.checkSelfPermission(OCRAppMainActivity_02.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(OCRAppMainActivity_02.this, new String[]{
                    Manifest.permission.CAMERA
            }, REQUEST_CAMERA_CODE);
        }

        lottieAnimationCamera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                obj.ButtonClickAnimation(R.color.light_blue_for_copy_text, R.color.green_color_when_anything_clicked, lottieAnimationCamera, OCRAppMainActivity_02.this);

                dispatchTakePictureIntent();
                flag = 1;
            }
        });

        lottieAnimationGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obj.ButtonClickAnimation(R.color.light_blue_for_copy_text, R.color.green_color_when_anything_clicked, lottieAnimationGallery, OCRAppMainActivity_02.this);

                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, GALLERY_PIC_REQUEST);

            }
        });


        /*----------- USING TOOLBAR --------------*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.ocrMainActivityToolbar);
        ImageView backArrow = findViewById(R.id.back_arrow);    // Finding back arrow id...
        setSupportActionBar(toolbar);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
        /*---------------- END TOOLBAR -------------*/

    }   // End of onCreate...

    private void goBack() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        finish();
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_CAMERA_CODE);
            }
        }
    }





    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
//        currentPhotoPath = image.getAbsolutePath();    // <--- Currently not needed...
        return image;
    }


    // For ORIGINAL IMAGE...
    private void sendImageToImageActivity() {

        //File f = new File(currentPhotoPath);          // We can access the uri by opening the file or we can directly access the uri which we have in Uri datatype...
        Uri contentUri = photoURI;
        intent = new Intent(OCRAppMainActivity_02.this, ocr_ImageActivity.class);
        intent.putExtra("UriImageClicked", contentUri);
        intent.putExtra("Flag", "111");
        intent.putExtra("FileName", String.valueOf(photoFile));
//        Log.d("URIIII 1--->", String.valueOf(contentUri));        //  <--- For Debugging...
        intent.putExtra("TabCounter", initializer);
        startActivity(intent);
        finish();       // Will finish the starting activity...
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {   // Although the below animation is expected to see when back button is pressed, but it will not because onPause method will be called by default which will run its own animation.

        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

        if(initializer != 0 && flag2 == true){
            super.onBackPressed();
        }

        // This if statement will be executed only for one time if the user does not go ahead of the OCRAppMainActivity_02;
        if(initializer == 0 && flag2 == false) {
            // ALERT BOX :-
            new AlertDialog.Builder(OCRAppMainActivity_02.this)
                    .setTitle("Exit OCR Text-To-Image Generator?")
                    .setMessage("Are you sure you want to exit the application?")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            // or
                            // OCRAppMainActivity_02.super.onBackPressed();
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton("No", null)
                    .show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == REQUEST_CAMERA_CODE && flag == 1 & resultCode == RESULT_OK){
            sendImageToImageActivity();
        }


        if(requestCode == GALLERY_PIC_REQUEST && flag == 0 && resultCode == RESULT_OK){
            Uri selectedImage = data.getData();

            Intent GalleryIntent = new Intent(OCRAppMainActivity_02.this, ocr_ImageActivity.class);
            GalleryIntent.putExtra("UriImageData", selectedImage);
            GalleryIntent.putExtra("Flag", "222");
            GalleryIntent.putExtra("TabCounter", initializer);
            startActivity(GalleryIntent);
            finish();
        }





    }   // End of onCreate...

}