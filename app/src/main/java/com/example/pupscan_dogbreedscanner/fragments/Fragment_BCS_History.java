package com.example.pupscan_dogbreedscanner.fragments;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pupscan_dogbreedscanner.ModelBCS;
import com.example.pupscan_dogbreedscanner.R;
import com.example.pupscan_dogbreedscanner.adapters.DogBCSAdapter;
import com.example.pupscan_dogbreedscanner.helpers.DatabaseHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_BCS_History#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_BCS_History extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_BCS_History() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Homepage.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_BCS_History newInstance(String param1, String param2) {
        Fragment_BCS_History fragment = new Fragment_BCS_History();
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

    private DogBCSAdapter dogBCSAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_bcs_history, container, false);

        List<ModelBCS> dummyData = createDummyData();

        // Initialize the adapter with dummy data
        dogBCSAdapter = new DogBCSAdapter(dummyData);

        RecyclerView recyclerView = view.findViewById(R.id.rvBreed);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(dogBCSAdapter);
        dogBCSAdapter.notifyDataSetChanged();


        return view;
    }

    private List<ModelBCS> createDummyData() {
        List<ModelBCS> modelBCSList = new ArrayList<>();

        DatabaseHelper dbHelper = new DatabaseHelper(getContext());

        // Call the getAllBcsHistory method to retrieve all records
        Cursor cursor = dbHelper.getAllBcsHistory();

        // Check if there are results
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Check if the cursor has the columns
                if (cursor.getColumnIndex(DatabaseHelper.BcsHistoryTable.COLUMN_BREED) != -1 &&
                        cursor.getColumnIndex(DatabaseHelper.BcsHistoryTable.COLUMN_WEIGHT) != -1 &&
                        cursor.getColumnIndex(DatabaseHelper.BcsHistoryTable.COLUMN_RESULT) != -1) {

                    // Retrieve data from the cursor
                    @SuppressLint("Range") String breed = cursor.getString(cursor.getColumnIndex(DatabaseHelper.BcsHistoryTable.COLUMN_BREED));
                    @SuppressLint("Range") String gender = cursor.getString(cursor.getColumnIndex(DatabaseHelper.BcsHistoryTable.COLUMN_GENDER));
                    @SuppressLint("Range") double weight = cursor.getDouble(cursor.getColumnIndex(DatabaseHelper.BcsHistoryTable.COLUMN_WEIGHT));
                    @SuppressLint("Range") int result = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.BcsHistoryTable.COLUMN_RESULT));

                    // Create DogModel instance and add it to the list
                    ModelBCS modelBCS = new ModelBCS(breed, gender, String.valueOf(weight), String.valueOf(result));
                    modelBCSList.add(modelBCS); // Changed from modelBCS.add(modelBCS);

                } else {
                    // Handle the case where one or more columns are missing
                    Toast.makeText(getContext(), "One or more columns are missing in the cursor", Toast.LENGTH_SHORT).show();
                }
            } while (cursor.moveToNext());

            // Close the cursor when you're done with it
            cursor.close();
        } else {
            // Handle the case where the cursor is empty
            Toast.makeText(getContext(), "No data found in the cursor", Toast.LENGTH_SHORT).show();
        }

        // Close the database when you're done with it
        dbHelper.close();

        Collections.reverse(modelBCSList); // Changed from modelBCS

        return modelBCSList; // Changed from modelBCS
    }

}