package com.example.pupscan_dogbreedscanner;

import static com.example.pupscan_dogbreedscanner.helpers.ImageClassifierHelper.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.example.pupscan_dogbreedscanner.R;
import com.example.pupscan_dogbreedscanner.helpers.DatabaseHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Scanner;

public class Activity_DogResult extends AppCompatActivity {

    float[] scores;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_result);

        LottieAnimationView savetoHistory = findViewById(R.id.savetoHistory);

        ImageButton ibBack = findViewById(R.id.ibBack);
        ibBack.setOnClickListener(v -> {
            finish();
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("label")) {
            String[] labels = extras.getStringArray("label");
            scores = extras.getFloatArray("scores");
            boolean history = extras.getBoolean("history");
            if(history){
                savetoHistory.setVisibility(View.GONE);
            }

            Toast.makeText(this, String.valueOf(scores[0]), Toast.LENGTH_SHORT).show();
            // Identify the index with the highest score
            int maxIndex = 0;
            float maxScore = scores[0];
            for (int i = 1; i < scores.length; i++) {
                if (scores[i] > maxScore) {
                    maxIndex = i;
                    maxScore = scores[i];
                }
            }

            // Get the breed name from the label
            String breedLabel = labels[0];
            String[] parts = breedLabel.split("\\.");
            String breedNumber = parts[0];
            String jsonData = readRawResource(R.raw.breeds);
            Toast.makeText(this,   breedNumber, Toast.LENGTH_SHORT).show();

            try {
                JSONObject jsonBreeds = new JSONObject(jsonData);
                JSONObject breedData = jsonBreeds.getJSONObject("breeds");
                Log.d("JSONObject", String.valueOf(breedData)); // Log the entire JSON data

                // Check if the breed data exists in the JSON
                if (breedData.has(breedNumber)) {
                    JSONObject breedIds = breedData.getJSONObject(breedNumber);

                    // Update UI elements with breed data
                    updateUI(breedIds);
                } else {
                    Toast.makeText(this, "Breed data not found", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error reading JSON", Toast.LENGTH_SHORT).show();
            }

            savetoHistory.setOnClickListener(v -> {
                DatabaseHelper dbHelper = new DatabaseHelper(this);
                long newRowId = dbHelper.insertModelLabelScore("breedmodel.tflite", labels[0], scores[0]);
                if (newRowId != -1) {
                    Toast.makeText(this, "Data inserted successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Failed to insert data!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        Button btn_tryAgain = findViewById(R.id.btn_tryAgain);
        btn_tryAgain.setOnClickListener(v -> {
            finish();
        });
    }

    private String readRawResource(int resourceId) {
        InputStream inputStream = getResources().openRawResource(resourceId);
        Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }

    private void updateUI(JSONObject breedData) throws JSONException {
        // Update TextViews with breed details
        TextView tvDogName = findViewById(R.id.tv_dogName);
        tvDogName.setText(breedData.getString("name"));

        // Assuming scores[0] is the confidence score for the top label
        float confidenceScore = scores[0];

        LinearLayout LLresult = findViewById(R.id.LLresult);
        if(confidenceScore <= 0.3){
            LLresult.setVisibility(View.VISIBLE);
        }else {
            LLresult.setVisibility(View.GONE);
        }

        // Display the confidence score as a percentage
        TextView tvPercentage = findViewById(R.id.tv_percentage);
        tvPercentage.setText(String.format("%.1f Match", confidenceScore * 100));

        TextView tvHeightNum = findViewById(R.id.tv_height_num);
        tvHeightNum.setText(breedData.getJSONObject("details").getString("height"));

        TextView tvWeightNum = findViewById(R.id.tv_weight_num);
        tvWeightNum.setText(breedData.getJSONObject("details").getString("weight"));

        TextView tvLifespanNum = findViewById(R.id.tv_lifespan_num);
        tvLifespanNum.setText(breedData.getJSONObject("details").getString("lifespan"));

        TextView tvGoodWith = findViewById(R.id.tv_goodWith);
        tvGoodWith.setText(breedData.getJSONObject("details").getString("goodWith"));

        TextView tvTemperament = findViewById(R.id.tv_temperament);
        tvTemperament.setText(breedData.getJSONObject("details").getString("temperament"));

        // Assuming breedData.getString("image") contains the filename of the image
        String imageName = breedData.getString("image");

        // Get the resource ID of the image using the filename
        int imageResourceId = getResources().getIdentifier(imageName, "drawable", getPackageName());

        // Check if the resource ID is valid
        if (imageResourceId != 0) {
            // Set the image resource for the ImageView
            ImageView iv_dogIcon = findViewById(R.id.iv_dogIcon);
            iv_dogIcon.setImageResource(imageResourceId);
        } else {
            // Handle the case where the resource ID is not valid (image not found)
            Toast.makeText(this, "Image not found", Toast.LENGTH_SHORT).show();
        }
    }

}