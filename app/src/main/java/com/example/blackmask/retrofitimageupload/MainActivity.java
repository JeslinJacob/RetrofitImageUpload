package com.example.blackmask.retrofitimageupload;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static int RESULT_LOAD_IMAGE = 1;
    public   ImageView myimage;
    public EditText imagetxt;
    public Button choosebtn,uploadbtn;
    public Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myimage = (ImageView) findViewById(R.id.imageview);
        imagetxt=(EditText)findViewById(R.id.imagetitle);
        choosebtn=(Button) findViewById(R.id.choosebn);
        uploadbtn=(Button) findViewById(R.id.uploadbn);
    }

    public void clickChooseImage(View v)
    {
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, RESULT_LOAD_IMAGE);

    }

    public void uploadImage(View v)
    {
        String image=ImageToString();
        String imgtitle=imagetxt.getText().toString().trim();

        Toast.makeText(this, ""+imgtitle, Toast.LENGTH_SHORT).show();
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<ImageClass> call = apiInterface.uploadImage(imgtitle,image);

        call.enqueue(new Callback<ImageClass>() {
            @Override
            public void onResponse(Call<ImageClass> call, Response<ImageClass> response) {
                ImageClass imageClass=response.body();
                Toast.makeText(MainActivity.this, "server  response : "+imageClass.getResponse(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ImageClass> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri path = data.getData();


            try {
                //putting the image from obtained path into a bitmap
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                //setting the bitmap on the image view
                myimage.setImageBitmap(bitmap);

                //making necessary element visible and invisible
                myimage.setVisibility(View.VISIBLE);
                imagetxt.setVisibility(View.VISIBLE);
                choosebtn.setEnabled(false);
                uploadbtn.setEnabled(true);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else {
            Toast.makeText(this, "error while selecting", Toast.LENGTH_SHORT).show();
        }


    }

    private String ImageToString(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //complressing the bitmap to a jpg file
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);

        //inserting the bytearrayoutputstream into byte array
        byte[] imagebyte = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imagebyte,Base64.DEFAULT);
    }



}
