package com.example.pupscan_dogbreedscanner;

import static com.example.pupscan_dogbreedscanner.helpers.ImageClassifierHelper.TAG;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.pupscan_dogbreedscanner.helpers.ImageClassifierHelper;
import com.google.common.util.concurrent.ListenableFuture;

import org.tensorflow.lite.support.label.Category;
import org.tensorflow.lite.task.vision.classifier.Classifications;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class Camera_Preview extends AppCompatActivity {

    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private PreviewView previewView;
    private LottieAnimationView captureButton;
    private ImageClassifierHelper imageClassifierHelper;
    private ConstraintLayout loadinglayout;
    private String modelvalue;
    private ImageButton ibGallery;
    private ActivityResultLauncher<Intent> galleryLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_preview);

        ibGallery = findViewById(R.id.ibGallery);
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                // Handle the selected image
                Bitmap selectedImage = getBitmapFromUri(result.getData().getData());

                // Perform your image processing or analysis here
                if (selectedImage != null) {
                    imageClassifierHelper = ImageClassifierHelper.create(getApplicationContext(), modelvalue, new ImageClassifierHelper.ClassifierListener() {
                        @Override
                        public void onError(String error) {

                        }

                        @Override
                        public void onResults(List<Classifications> results, long inferenceTime) {
                            List<Category> categories = results.get(0).getCategories();

                            // Extract labels and scores into separate arrays
                            String[] labels = new String[categories.size()];
                            float[] scores = new float[categories.size()];

                            for (int i = 0; i < categories.size(); i++) {
                                labels[i] = categories.get(i).getLabel();
                                scores[i] = categories.get(i).getScore();
                            }


                            loadinglayout.setVisibility(View.VISIBLE);
                            // Pass labels and scores through the Intent
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("model", modelvalue);
                            resultIntent.putExtra("labels", labels);
                            resultIntent.putExtra("scores", scores);

                            new Handler().postDelayed(() -> {
                                setResult(Activity.RESULT_OK, resultIntent);
                                finish();
                            }, 5000);
                        }
                    });
                    imageClassifierHelper.classify(selectedImage, 0);
                }
            }
        });

        ibGallery.setOnClickListener(view -> openGallery());

        modelvalue = getIntent().getStringExtra("model");

        loadinglayout = findViewById(R.id.loadinglayout);

        cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        captureButton = findViewById(R.id.captureButton);
        previewView = findViewById(R.id.previewView);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                // No errors need to be handled for this Future.
                // This should never be reached.
            }
        }, ContextCompat.getMainExecutor(this));

    }


    void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder()
                .build();

        ImageCapture imageCapture = new ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);

        captureButton.setOnClickListener(view -> {
            LottieAnimationView lottieAnimationView = findViewById(R.id.captureButton);
            lottieAnimationView.playAnimation();
            // Take a picture
            imageCapture.takePicture(ContextCompat.getMainExecutor(Camera_Preview.this), new ImageCapture.OnImageCapturedCallback() {

                @Override
                public void onCaptureSuccess(@NonNull ImageProxy image) {
                    // Convert ImageProxy to Bitmap
                    Bitmap bitmap = imageProxyToBitmap(image);
                    //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.labrador);
                    // Release the ImageProxy
                    image.close();

                    imageClassifierHelper = ImageClassifierHelper.create(getApplicationContext(), modelvalue, new ImageClassifierHelper.ClassifierListener() {
                        @Override
                        public void onError(String error) {

                        }

                        @Override
                        public void onResults(List<Classifications> results, long inferenceTime) {
                            List<Category> categories = results.get(0).getCategories();

                            // Extract labels and scores into separate arrays
                            String[] labels = new String[categories.size()];
                            float[] scores = new float[categories.size()];

                            for (int i = 0; i < categories.size(); i++) {
                                labels[i] = categories.get(i).getLabel();
                                scores[i] = categories.get(i).getScore();
                            }


                            loadinglayout.setVisibility(View.VISIBLE);
                            // Pass labels and scores through the Intent
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("model", modelvalue);
                            resultIntent.putExtra("labels", labels);
                            resultIntent.putExtra("scores", scores);

                            new Handler().postDelayed(() -> {
                                setResult(Activity.RESULT_OK, resultIntent);
                                finish();
                            }, 5000);
                        }
                    });
                    imageClassifierHelper.classify(bitmap, 0);
                }

                @Override
                public void onError(@NonNull ImageCaptureException error) {
                    // Image capture error callback
                    Toast.makeText(Camera_Preview.this, "Capture failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(galleryIntent);
    }

    // Helper method to convert Uri to Bitmap
    private Bitmap getBitmapFromUri(Uri uri) {
        try {
            return MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Bitmap imageProxyToBitmap(ImageProxy image) {
        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
    }


}