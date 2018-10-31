package com.example.aditya.quantumeye.camera;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aditya.quantumeye.R;
import com.example.aditya.quantumeye.api.ApiManager;
import com.example.aditya.quantumeye.api.ServerResponse;
import com.google.android.material.button.MaterialButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CameraActivity extends AppCompatActivity {

    private ImageView mCapturedPhotoImageView;
    private MaterialButton mCaptureButton;
    private MaterialButton mUploadButton;

    private Bitmap mCapturedImageBitmap;

    private static ApiManager mApiManager;

    private final static int TAKE_PICTURE = 1001;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        mApiManager = ApiManager.getInstance();

        mCapturedPhotoImageView = (ImageView) findViewById(R.id.camera_activity_captured_photo);
        mCaptureButton = (MaterialButton) findViewById(R.id.camera_activity_capture);
        mUploadButton = (MaterialButton) findViewById(R.id.camera_activity_upload);
        mUploadButton.setEnabled(false);

        mCaptureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        mUploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File imageFile = null;
                try {
                    imageFile = createFileForImage();
                } catch (IOException e) {
                    Log.d("CameraActivity", "IO Exception : " + e.toString());
                }

                if (imageFile != null){
                    sendImageToServer(imageFile);
                }
            }
        });


    }


    private void dispatchTakePictureIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            mCapturedImageBitmap = (Bitmap) extras.get("data");
            mCapturedPhotoImageView.setImageBitmap(mCapturedImageBitmap);
            mUploadButton.setEnabled(true);
        }
    }

    private File createFileForImage() throws IOException{
        File file = new File(CameraActivity.this.getCacheDir(), System.currentTimeMillis() + "");
        file.createNewFile();

        Bitmap image = mCapturedImageBitmap;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 0, byteArrayOutputStream);
        byte[] bitmapData = byteArrayOutputStream.toByteArray();

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(bitmapData);
        fileOutputStream.flush();
        fileOutputStream.close();

        return file;

    }

    private void sendImageToServer(File file) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        mApiManager.uploadImageToServer(fileToUpload, filename, new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse serverResponse = response.body();
                if (response.isSuccessful() && serverResponse != null){
                    Toast.makeText(CameraActivity.this, "onResponse : successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CameraActivity.this, "Response is : " + String.valueOf(response.code()), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(CameraActivity.this, "onFailure " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
