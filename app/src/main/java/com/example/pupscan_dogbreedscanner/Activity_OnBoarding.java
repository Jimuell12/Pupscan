package com.example.pupscan_dogbreedscanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.pupscan_dogbreedscanner.adapters.SliderAdapter;

public class Activity_OnBoarding extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout dotsLayout;
    private SliderAdapter sliderAdapter;
    private TextView[] dots;
    private Button letsGetStarted, skip;
    private Animation animation;
    int currentPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isOnboardingShown()) {
            // Onboarding has been shown before, navigate to the main activity
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        setContentView(R.layout.activity_on_boarding);

        // Link the ViewPager with its corresponding view in the XML layout
        viewPager = findViewById(R.id.viewpager);

        // Link the dotsLayout with its corresponding view in the XML layout
        dotsLayout = findViewById(R.id.dotsLayout);

        // Link the Button letsGetStarted with its corresponding view in the XML layout
        letsGetStarted = findViewById(R.id.letsGetStarted);

        // Initialize the SliderAdapter
        sliderAdapter = new SliderAdapter(this);

        // Set the adapter to the ViewPager
        viewPager.setAdapter(sliderAdapter);

        // Call the addDots method to add the dots to the dotsLayout
        addDots(0);

        // Set the onPageChangeListener to the ViewPager
        viewPager.addOnPageChangeListener(changeListener);

        skip = findViewById(R.id.skip);

        skip.setOnClickListener(v -> {
            SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            preferences.edit().putBoolean("onboardingShown", true).apply();

            Intent intent = new Intent(Activity_OnBoarding.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        letsGetStarted.setOnClickListener(v -> {
            SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            preferences.edit().putBoolean("onboardingShown", true).apply();

            Intent intent = new Intent(Activity_OnBoarding.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private boolean isOnboardingShown() {
        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return preferences.getBoolean("onboardingShown", false);
    }


    public void skip(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void next(View view) {
        viewPager.setCurrentItem(currentPos + 1);
    }

    private void addDots(int position) {

        dots = new TextView[4];
        dotsLayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            int flags = Html.FROM_HTML_MODE_LEGACY;
            dots[i].setText(Html.fromHtml("&#8226;", flags));
            dots[i].setTextSize(35);

            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[position].setTextColor(getResources().getColor(R.color.fern_100));
        }

    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            currentPos = position;

            if (position == 0) {
                letsGetStarted.setVisibility(View.INVISIBLE);
            } else if (position == 1) {
                letsGetStarted.setVisibility(View.INVISIBLE);
            } else if (position == 2) {
                letsGetStarted.setVisibility(View.INVISIBLE);
            } else {
                animation = AnimationUtils.loadAnimation(Activity_OnBoarding.this, R.anim.bottom_anim);
                letsGetStarted.setAnimation(animation);
                letsGetStarted.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}