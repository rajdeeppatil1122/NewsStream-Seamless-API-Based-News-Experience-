package com.rajdeep.allinoneapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ocr_ImageActivity extends AppCompatActivity {
    ImageView ocrImageSelected;
    Bitmap bitmap;
    LottieAnimationView ocrCameraLottieImgActivity, ocrGalleryLottieImgActivity, lottieProcessing;
    Button button;
    TextView textView3, back_stack_counter;
    ocr_ButtonClickAnimationClass obj = new ocr_ButtonClickAnimationClass();
    int initializer = 0;

    private static final int REQUEST_CAMERA_CODE = 100;
    private static final int GALLERY_PIC_REQUEST = 120;
    Uri photoURI;
    File photoFile;
    int flag = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        setContentView(R.layout.activity_ocr_image);

        ocrImageSelected = findViewById(R.id.ocrImageSelected);
        ocrCameraLottieImgActivity = findViewById(R.id.ocrCameraLottieImgActivity);
        ocrGalleryLottieImgActivity = findViewById(R.id.ocrGalleryLottieImgActivity);
        lottieProcessing = findViewById(R.id.lottieProcessing);
        button = findViewById(R.id.button);
        textView3 = findViewById(R.id.textView3);
        back_stack_counter = findViewById(R.id.back_stack_counter);

        Animation scaleAnimation = AnimationUtils.loadAnimation(ocr_ImageActivity.this, R.anim.scale_anim2);
        /*
        Animation scaleAnimationFaster = AnimationUtils.loadAnimation(ocr_ImageActivity.this, R.anim.scale_anim_faster);
        Animation fadeAnimation = AnimationUtils.loadAnimation(ocr_ImageActivity.this, R.anim.fade_animation);
        */
        Animation slideBounceIn = AnimationUtils.loadAnimation(ocr_ImageActivity.this, R.anim.line_in_left_to_right_animation);
        button.startAnimation(scaleAnimation);
        textView3.startAnimation(slideBounceIn);

        ocrCameraLottieImgActivity.playAnimation();
        ocrGalleryLottieImgActivity.playAnimation();

        Intent CameraIntent = getIntent();
        initializer = getIntent().getExtras().getInt("TabCounter");
//        back_stack_counter.setText(String.valueOf(initializer));

        Uri imageURI = CameraIntent.getParcelableExtra("UriImageClicked");
        if(CameraIntent.getStringExtra("Flag").equals("111")) {
                Log.d("URI IS --->>>>>>", String.valueOf(imageURI));
                ocrImageSelected.setImageURI(imageURI);


                // Let's convert the Uri into Bitmap so that it will be used later for our API.
                bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageURI);
                } catch (IOException e) {
                    e.printStackTrace();
                }
//            Log.d("IMAGE BITMAP!!!! ->>>", String.valueOf(bitmap));


                // Deleting a file after its use as it will be saved in the data of our application which will increase the storage in turn of our application... So, deleting the file stored in memory is equally important...
                File f = new File(CameraIntent.getStringExtra("FileName"));     // <-- STRING PATHNAME>..
//            Toast.makeText(ocr_ImageActivity.this, CameraIntent.getStringExtra("FileName"), Toast.LENGTH_SHORT).show();
                f.delete();
        }


        Intent GalleryIntent = getIntent();
        Uri uri = GalleryIntent.getParcelableExtra("UriImageData");
        if(GalleryIntent.getStringExtra("Flag").equals("222")) {
            ocrImageSelected.setImageURI(uri);
            // Let's convert the Uri into Bitmap so that it will be used later for our API.
            bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }

//            Log.d("URI NEXT ->>>", String.valueOf(GalleryIntent));
        }



        ocrCameraLottieImgActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obj.ButtonClickAnimation(R.color.light_green_color_for_buttons, R.color.green_color_when_anything_clicked, ocrCameraLottieImgActivity, ocr_ImageActivity.this);

                dispatchTakePictureIntent();
                flag = 1;
            }
        });

        ocrGalleryLottieImgActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obj.ButtonClickAnimation(R.color.light_green_color_for_buttons, R.color.green_color_when_anything_clicked, ocrGalleryLottieImgActivity, ocr_ImageActivity.this);

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, GALLERY_PIC_REQUEST);
                flag = 0;
            }
        });


        // When Proceed Button Is CLICKED...
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lottieProcessing.setVisibility(View.VISIBLE);
                lottieProcessing.startAnimation(scaleAnimation);
                lottieProcessing.playAnimation();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lottieProcessing.clearAnimation();
                        lottieProcessing.setVisibility(View.INVISIBLE);
                        imageToTextMethod(bitmap);
                    }
                }, 4240);

            }
        });


        /*----------- USING TOOLBAR --------------*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.ocrImageActivityToolbar);
        ImageView backArrow = findViewById(R.id.back_arrow);    // Finding back arrow id...
        setSupportActionBar(toolbar);

        /* // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        } */
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        /*---------------- END TOOLBAR -------------*/


    }   // onCreate Method ends here...

    @Override
    public void onBackPressed() {

        // To pop-up an alert box!
        if(initializer == 0){

            new AlertDialog.Builder(ocr_ImageActivity.this)
                    .setTitle("Exit OCR Text-To-Image Generator?")
                    .setMessage("Are you sure you want to exit the application?")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton("No", null)
                    .show();
        }
        else{

            super.onBackPressed();

//        In Activity2, use setResult for sending data back:
        Intent intent = new Intent();
        initializer--;
//        Toast.makeText(this, String.valueOf(initializer), Toast.LENGTH_SHORT).show();
        intent.putExtra("TabCounter", initializer);
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

        finish();
        }
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
        Intent intent = new Intent(ocr_ImageActivity.this, ocr_ImageActivity.class);
        intent.putExtra("UriImageClicked", contentUri);
        intent.putExtra("Flag", "111");
        intent.putExtra("FileName", String.valueOf(photoFile));
//        Log.d("URIIII 1--->", String.valueOf(contentUri));        //  <--- For Debugging...
        intent.putExtra("TabCounter", initializer);
        startActivity(intent);
        finish();   // This will finish the current activity which successfully jumping to NEXT activity.
    }




    private void imageToTextMethod(Bitmap bitmap){
        TextRecognizer recognizer = new TextRecognizer.Builder(this).build();
        if(!recognizer.isOperational()){
            Toast.makeText(ocr_ImageActivity.this, "Something went wrong\n(An error occurred)", Toast.LENGTH_SHORT).show();
        }
        else{
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> textBlockSparseArray = recognizer.detect(frame);
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i<textBlockSparseArray.size(); i++){
                TextBlock textBlock = textBlockSparseArray.valueAt(i);
                stringBuilder.append(textBlock.getValue());
                stringBuilder.append("\n");
            }
            String str = stringBuilder.toString();
            Intent intent = new Intent(ocr_ImageActivity.this, ocr_ResultActivity.class);
            intent.putExtra("TextResult", str);
            initializer++;
            intent.putExtra("TabCounter", initializer);
            startActivity(intent);
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        // The resultCode == RESULT_OK is used to maintain the previous image if we do not click any image by opening the camera... It is like: Proceed further only if the image is clicked (that means only if the RESULT IS OK).
        if(requestCode == REQUEST_CAMERA_CODE && flag == 1 && resultCode == RESULT_OK){
            sendImageToImageActivity();
        }


        if(requestCode == GALLERY_PIC_REQUEST && flag == 0 && resultCode == RESULT_OK){
            Uri selectedImage = data.getData();

            Intent GalleryIntent = new Intent(ocr_ImageActivity.this, ocr_ImageActivity.class);
            GalleryIntent.putExtra("UriImageData", selectedImage);
            GalleryIntent.putExtra("Flag", "222");
            GalleryIntent.putExtra("TabCounter", initializer);
            startActivity(GalleryIntent);
            finish();       // This will finish the current activity which successfully jumping to NEXT activity.
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        int temp = initializer;
        initializer = getIntent().getExtras().getInt("TabCounter");
        if(String.valueOf(initializer) == null){
            initializer = temp;
        }
//        Toast.makeText(this, "THe value of initializer is : " + String.valueOf(initializer), Toast.LENGTH_SHORT).show();
        back_stack_counter.setText(String.valueOf(initializer));
    }


    @Override
    protected void onPause() {
        super.onPause();
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

   /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }*/



}