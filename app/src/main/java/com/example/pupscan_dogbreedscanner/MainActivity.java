package com.example.pupscan_dogbreedscanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.pupscan_dogbreedscanner.fragments.Fragment_BCS_History;
import com.example.pupscan_dogbreedscanner.fragments.Fragment_DogScanner;
import com.example.pupscan_dogbreedscanner.fragments.Fragment_Breed_History;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            // Handle navigation item selection here
            if (item.getItemId() == R.id.Breed_Scanner) {
                replaceFragment(new Fragment_DogScanner());
                return true;
            } else if (item.getItemId() == R.id.BCS_History) {
                replaceFragment(new Fragment_BCS_History());
                return true;
            } else if (item.getItemId() == R.id.Breed_History) {
                replaceFragment(new Fragment_Breed_History());
                return true;
            }
            return false;
        });
        replaceFragment(new Fragment_DogScanner());
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.framelayout, fragment);
        transaction.commit();
    }
}
