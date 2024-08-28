package com.example.pupscan_dogbreedscanner.fragments;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pupscan_dogbreedscanner.ModelBCS;
import com.example.pupscan_dogbreedscanner.ModelBreed;
import com.example.pupscan_dogbreedscanner.R;
import com.example.pupscan_dogbreedscanner.adapters.DogBreedAdapter;
import com.example.pupscan_dogbreedscanner.helpers.DatabaseHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Breed_History#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Breed_History extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Breed_History() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_History.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Breed_History newInstance(String param1, String param2) {
        Fragment_Breed_History fragment = new Fragment_Breed_History();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private DatabaseHelper databaseHelper;
    private DogBreedAdapter dogBreedAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__history, container, false);

        databaseHelper = new DatabaseHelper(getContext());
        List<ModelBreed> breedData = createBreedData();

        // Initialize the adapter with dummy data
        dogBreedAdapter = new DogBreedAdapter(getContext(), breedData);

        RecyclerView recyclerView = view.findViewById(R.id.breedRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(dogBreedAdapter);
        dogBreedAdapter.notifyDataSetChanged();



        return view;
    }

    private List<ModelBreed> createBreedData() {
        List<ModelBreed> modelBCSList = new ArrayList<>();

        DatabaseHelper dbHelper = new DatabaseHelper(getContext());

        Cursor cursor = dbHelper.getAllModelLabelScore();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(DatabaseHelper.ModelLabelScoreTable.COLUMN_ID);
                int modelIndex = cursor.getColumnIndex(DatabaseHelper.ModelLabelScoreTable.COLUMN_MODEL);
                int labelIndex = cursor.getColumnIndex(DatabaseHelper.ModelLabelScoreTable.COLUMN_LABEL);
                int scoreIndex = cursor.getColumnIndex(DatabaseHelper.ModelLabelScoreTable.COLUMN_SCORE);

                // Check if the column exists in the cursor
                if (idIndex != -1 && modelIndex != -1 && labelIndex != -1 && scoreIndex != -1) {
                    int id = cursor.getInt(idIndex);
                    String model = cursor.getString(modelIndex);
                    String label = cursor.getString(labelIndex);
                    float score = cursor.getFloat(scoreIndex);

                    String breedLabel = label;
                    String[] parts = breedLabel.split("\\.");
                    String breedNumber = parts[0];
                    String jsonData = readRawResource(R.raw.breeds);
                    try {
                        JSONObject jsonBreeds = new JSONObject(jsonData);
                        JSONObject breedData = jsonBreeds.getJSONObject("breeds");
                        Log.d("JSONObject", String.valueOf(breedData)); // Log the entire JSON data

                        // Check if the breed data exists in the JSON
                        if (breedData.has(breedNumber)) {
                            JSONObject breedIds = breedData.getJSONObject(breedNumber);
                            String breedName = breedIds.getString("name");
                            String height = breedIds.getJSONObject("details").getString("height");
                            String weight = breedIds.getJSONObject("details").getString("weight");
                            String lifespan = breedIds.getJSONObject("details").getString("lifespan");
                            String image = breedIds.getString("image");

                            ModelBreed modelBreed = new ModelBreed(breedName, height, weight, lifespan, model, label, score, image);
                            modelBCSList.add(modelBreed); // Change to add the model to the list

                        } else {
                            Toast.makeText(getContext(), "Breed data not found", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Error reading JSON", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle the case where one or more columns are not found
                    Toast.makeText(getContext(), "Columns not found in cursor", Toast.LENGTH_SHORT).show();
                }
            } while (cursor.moveToNext());
        } else {
            Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
        }

        if (cursor != null) {
            cursor.close();
        }

        Collections.reverse(modelBCSList);

        return modelBCSList; // Return the list
    }

    private String readRawResource(int resourceId) {
        InputStream inputStream = getResources().openRawResource(resourceId);
        Scanner scanner = new Scanner(inputStream).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }

}