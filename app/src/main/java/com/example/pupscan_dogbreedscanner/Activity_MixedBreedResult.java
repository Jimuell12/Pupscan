package com.example.pupscan_dogbreedscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.pupscan_dogbreedscanner.helpers.DatabaseHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.Scanner;

public class Activity_MixedBreedResult extends AppCompatActivity {

    float[] scores;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mixed_breed_result);

        ImageButton ibBack = findViewById(R.id.ibBack);
        ibBack.setOnClickListener(v -> {
            finish();
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("label")) {
            String[] labels = extras.getStringArray("label");
            scores = extras.getFloatArray("scores");
            boolean history = extras.getBoolean("history");

            Toast.makeText(this, String.valueOf(scores[0]), Toast.LENGTH_SHORT).show();

            // Ensure at least 2 labels are present before attempting to update UI
            if (labels.length >= 2) {
                String breedLabel1 = labels[0];
                String breedLabel2 = labels[1];
                String breedLabel3 = labels.length >= 3 ? labels[2] : null;

                String[] parts1 = breedLabel1.split("\\.");
                String breedNumber1 = parts1[0];

                String[] parts2 = breedLabel2.split("\\.");
                String breedNumber2 = parts2[0];

                String[] parts3 = breedLabel3 != null ? breedLabel3.split("\\.") : null;
                String breedNumber3 = parts3 != null ? parts3[0] : null;

                String jsonData = readRawResource(R.raw.breeds);
                Toast.makeText(this, breedNumber1, Toast.LENGTH_SHORT).show();

                try {
                    JSONObject jsonBreeds = new JSONObject(jsonData);
                    JSONObject breedData = jsonBreeds.getJSONObject("breeds");
                    Log.d("JSONObject", String.valueOf(breedData)); // Log the entire JSON data

                    if (breedData.has(breedNumber1)) {
                        JSONObject breedIds1 = breedData.getJSONObject(breedNumber1);
                        updateUI1(breedIds1, scores);
                    } else {
                        Toast.makeText(this, "Breed data not found", Toast.LENGTH_SHORT).show();
                    }

                    if (breedData.has(breedNumber2)) {
                        JSONObject breedIds2 = breedData.getJSONObject(breedNumber2);
                        updateUI2(breedIds2, scores);
                    } else {
                        Toast.makeText(this, "Breed data not found", Toast.LENGTH_SHORT).show();
                    }

                    // Update UI for the third dog only if it exists and its score is greater than or equal to 10%
                    if (breedData.has(breedNumber3) && scores.length >= 3 && scores[2] >= 0.1) {
                        JSONObject breedIds3 = breedData.getJSONObject(breedNumber3);
                        updateUI3(breedIds3, scores);
                    }else {
                        TextView tv_dogName_3 = findViewById(R.id.tv_dogName_3);
                        TextView tv_percentage_3 = findViewById(R.id.tv_percentage_3);
                        ImageView iv_dogIcon_3 = findViewById(R.id.iv_dogIcon_3);
                        tv_dogName_3.setVisibility(View.GONE);
                        tv_percentage_3.setVisibility(View.GONE);
                        iv_dogIcon_3.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Error reading JSON", Toast.LENGTH_SHORT).show();
                }
                LottieAnimationView savetoHistory = findViewById(R.id.savetoHistory);
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
        }
    }

    private String readRawResource(int resourceId) {
        InputStream inputStream = getResources().openRawResource(resourceId);
        Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }

    private void updateUI1(JSONObject breedData, float[] scores) throws JSONException {
        TextView tv_dogName_1 = findViewById(R.id.tv_dogName_1);
        TextView tv_percentage_1 = findViewById(R.id.tv_percentage_1);


        // Update UI elements with breed data
        tv_dogName_1.setText(breedData.getString("name"));

        // Update percentages with scores
        tv_percentage_1.setText(String.format("%.1f%% Match", scores[0] * 100));

        String imageName = breedData.getString("image");

        int imageResourceId = getResources().getIdentifier(imageName, "drawable", getPackageName());

        // Check if the resource ID is valid
        if (imageResourceId != 0) {
            // Set the image resource for the ImageView
            ImageView iv_dogIcon = findViewById(R.id.iv_dogIcon);
            ImageView iv_dogIcon_1 = findViewById(R.id.iv_dogIcon_1);
            iv_dogIcon_1.setImageResource(imageResourceId);
            iv_dogIcon.setImageResource(imageResourceId);
        } else {
            // Handle the case where the resource ID is not valid (image not found)
            Toast.makeText(this, "Image not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUI2(JSONObject breedData, float[] scores) throws JSONException {
        TextView tv_dogName_2 = findViewById(R.id.tv_dogName_2);
        TextView tv_percentage_2 = findViewById(R.id.tv_percentage_2);

        // Update UI elements with breed data
        tv_dogName_2.setText(breedData.getString("name"));

        // Update percentages with scores
        tv_percentage_2.setText(String.format("%.1f%% Match", scores[1] * 100));

        String imageName = breedData.getString("image");

        int imageResourceId = getResources().getIdentifier(imageName, "drawable", getPackageName());

        // Check if the resource ID is valid
        if (imageResourceId != 0) {
            // Set the image resource for the ImageView
            ImageView iv_dogIcon_2 = findViewById(R.id.iv_dogIcon_2);
            iv_dogIcon_2.setImageResource(imageResourceId);
        } else {
            // Handle the case where the resource ID is not valid (image not found)
            Toast.makeText(this, "Image not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUI3(JSONObject breedData, float[] scores) throws JSONException {
        TextView tv_dogName_3 = findViewById(R.id.tv_dogName_3);
        TextView tv_percentage_3 = findViewById(R.id.tv_percentage_3);

        // Update UI elements with breed data
        tv_dogName_3.setText(breedData.getString("name"));

        // Update percentages with scores
        tv_percentage_3.setText(String.format("%.1f%% Match", scores[2] * 100));

        String imageName = breedData.getString("image");

        int imageResourceId = getResources().getIdentifier(imageName, "drawable", getPackageName());

        // Check if the resource ID is valid
        if (imageResourceId != 0) {
            // Set the image resource for the ImageView
            ImageView iv_dogIcon_3 = findViewById(R.id.iv_dogIcon_3);
            iv_dogIcon_3.setImageResource(imageResourceId);
        } else {
            // Handle the case where the resource ID is not valid (image not found)
            Toast.makeText(this, "Image not found", Toast.LENGTH_SHORT).show();
        }
    }


}