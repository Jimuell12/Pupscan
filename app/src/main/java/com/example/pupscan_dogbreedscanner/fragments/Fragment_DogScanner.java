package com.example.pupscan_dogbreedscanner.fragments;


import static com.example.pupscan_dogbreedscanner.helpers.ImageClassifierHelper.TAG;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.pupscan_dogbreedscanner.Activity_BCS_Calculator;
import com.example.pupscan_dogbreedscanner.Activity_DogResult;
import com.example.pupscan_dogbreedscanner.Activity_Dog_Emotion;
import com.example.pupscan_dogbreedscanner.Activity_MixedBreedResult;
import com.example.pupscan_dogbreedscanner.Camera_Preview;
import com.example.pupscan_dogbreedscanner.R;
import com.example.pupscan_dogbreedscanner.helpers.ImageClassifierHelper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_DogScanner#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_DogScanner extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1001;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_DogScanner() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_DogScanner.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_DogScanner newInstance(String param1, String param2) {
        Fragment_DogScanner fragment = new Fragment_DogScanner();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private ImageClassifierHelper imageClassifierHelper;
    private LottieAnimationView lottieAnimationView1, lottieAnimationView2, lottieAnimationView3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_dog_scanner, container, false);

        requestPermissionCameraAccess();

        lottieAnimationView1 = view.findViewById(R.id.dogScan);
        lottieAnimationView2 = view.findViewById(R.id.emotionScan);
        lottieAnimationView3 = view.findViewById(R.id.bcs);

        lottieAnimationView1.setOnClickListener(v -> {
            openActivityForResult(Camera_Preview.class, 1, "breedmodel.tflite");
        });
        lottieAnimationView2.setOnClickListener(v -> {
            openActivityForResult(Camera_Preview.class, 1, "emotemodel.tflite");
        });
        lottieAnimationView3.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), Activity_BCS_Calculator.class);
            startActivity(intent);
        });



        return view;
    }

    private void openActivityForResult(Class<?> activityClass, int requestCode, String model) {
        Intent intent = new Intent(getActivity(), activityClass);
        intent.putExtra("model", model);
        activityResultLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> activityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        int requestCode = result.getResultCode();
                        Intent data = result.getData();
                        handleResult(data);
                    });

    private void handleResult(Intent data) {
        if (data == null) {
            Log.e(TAG, "Received null data in handleResult");
            return;
        }
        // Retrieve labels and scores arrays
        String model = data.getStringExtra("model");
        String[] labels = data.getStringArrayExtra("labels");
        float[] scores = data.getFloatArrayExtra("scores");

        // Check for null to avoid potential NullPointerException
        if (labels != null && scores != null) {
            // Now you can use labels and scores arrays as needed
            for (int i = 0; i < labels.length; i++) {
                String label = labels[i];
                float score = scores[i];
            }

            if (model.equals("breedmodel.tflite")) {
                // Check if the 2nd or 3rd score is higher than 0.10
                if (scores.length >= 3 && (scores[1] > 0.15 || scores[2] > 0.15)) {
                    // Start Activity_MixedBreedResult
                    Intent intent = new Intent(getActivity(), Activity_MixedBreedResult.class);
                    intent.putExtra("label", labels);
                    intent.putExtra("scores", scores);
                    startActivity(intent);
                } else {
                    // Start Activity_DogResult
                    Intent intent = new Intent(getActivity(), Activity_DogResult.class);
                    intent.putExtra("label", labels);
                    intent.putExtra("scores", scores);
                    startActivity(intent);
                }
            }
            else if (model.equals("emotemodel.tflite")){
                Intent intent = new Intent(getActivity(), Activity_Dog_Emotion.class);
                intent.putExtra("label", labels);
                intent.putExtra("scores", scores);
                startActivity(intent);
            }
        }

    }

    private void requestPermissionCameraAccess(){
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        } else {

        }
    }
}