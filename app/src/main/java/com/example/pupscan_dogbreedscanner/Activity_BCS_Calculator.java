package com.example.pupscan_dogbreedscanner;

import static com.example.pupscan_dogbreedscanner.DogBreedBCS.BCSCalculator.calculateBCS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pupscan_dogbreedscanner.helpers.DatabaseHelper;

import java.util.ArrayList;

public class Activity_BCS_Calculator extends AppCompatActivity {

    private Spinner breedSpinner;
    private Spinner genderSpinner;
    private EditText weightEditText;
    private Button savetohistory;
    private TextView bcsscore;
    private ImageView bcsImageView;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bcs_calculator);

        dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();
        breedSpinner = findViewById(R.id.breedSpinner);
        genderSpinner = findViewById(R.id.genderSpinner);
        weightEditText = findViewById(R.id.weightEditText);
        savetohistory = findViewById(R.id.savetohistory);
        bcsscore = findViewById(R.id.bcsscore);
        bcsImageView = findViewById(R.id.bcsImageView);

        // Create an ArrayAdapter using the breedNames array from DogBreeds class
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, DogBreedBCS.breedNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        breedSpinner.setAdapter(adapter);

        String[] genderOptions = {"Male", "Female"};

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, genderOptions);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);

        weightEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // Get selected breed, gender, and weight
                String selectedBreed = breedSpinner.getSelectedItem().toString();
                String selectedGender = genderSpinner.getSelectedItem().toString();

                if (!weightEditText.getText().toString().isEmpty()) {
                    double weight = Double.parseDouble(weightEditText.getText().toString());

                    double bcs = calculateBCS(selectedBreed, selectedGender, weight);

                    int roundedBCS = (int) Math.round(bcs);

                    switch (roundedBCS) {
                        case 1:
                            bcsscore.setText("Your dog scored 1 in BCS");
                            bcsImageView.setImageResource(R.drawable.vector_bcs_1);
                            break;
                        case 2:
                            bcsscore.setText("Your dog scored 2 in BCS");
                            bcsImageView.setImageResource(R.drawable.vector_bcs_2);
                            break;
                        case 3:
                            bcsscore.setText("Your dog scored 3 in BCS");
                            bcsImageView.setImageResource(R.drawable.vector_bcs_3);
                            break;
                        case 4:
                            bcsscore.setText("Your dog scored 4 in BCS");
                            bcsImageView.setImageResource(R.drawable.vector_bcs_4);
                            break;
                        case 5:
                            bcsscore.setText("Your dog scored 5 in BCS");
                            bcsImageView.setImageResource(R.drawable.vector_bcs_5);
                            break;
                        default:
                            bcsscore.setText("No Result");
                            bcsImageView.setImageResource(R.drawable.vector_bcs_1);
                    }
                } else {
                    // Handle the case where the weight is empty
                    Toast.makeText(Activity_BCS_Calculator.this, "Please enter a valid weight", Toast.LENGTH_SHORT).show();
                }
            }
        });

        savetohistory.setOnClickListener(v -> {
            String selectedBreed = breedSpinner.getSelectedItem().toString();
            String selectedGender = genderSpinner.getSelectedItem().toString();
            if (!weightEditText.getText().toString().isEmpty()) {
                double weight = Double.parseDouble(weightEditText.getText().toString());

                double bcs = calculateBCS(selectedBreed, selectedGender, weight);

                int roundedBCS = (int) Math.round(bcs);

                saveBcsEntry(selectedBreed, selectedGender, weight, roundedBCS);

                // Optionally, you can display a message indicating the entry has been saved.
                Toast.makeText(Activity_BCS_Calculator.this, "BCS entry saved to history", Toast.LENGTH_SHORT).show();
                finish();

            }else {
                Toast.makeText(Activity_BCS_Calculator.this, "Invalid Input, Make Sure that field is not empty", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveBcsEntry(String breed, String gender, double weight, int result) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.BcsHistoryTable.COLUMN_BREED, breed);
        values.put(DatabaseHelper.BcsHistoryTable.COLUMN_GENDER, gender);
        values.put(DatabaseHelper.BcsHistoryTable.COLUMN_WEIGHT, weight);
        values.put(DatabaseHelper.BcsHistoryTable.COLUMN_RESULT, result);

        long newRowId = database.insert(DatabaseHelper.BcsHistoryTable.TABLE_NAME, null, values);

        if (newRowId == -1) {
            // Handle error
            Toast.makeText(Activity_BCS_Calculator.this, "Failed to save BCS entry", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        // Close the database connection when the activity is destroyed
        dbHelper.close();
        super.onDestroy();
    }
}