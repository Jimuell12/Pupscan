package com.example.pupscan_dogbreedscanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

public class Activity_Dog_Emotion extends AppCompatActivity {

    float[] scores;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog_emotion);

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("label")) {
            String[] labels = extras.getStringArray("label");
            scores = extras.getFloatArray("scores");
            Toast.makeText(this, String.valueOf(scores[0]), Toast.LENGTH_SHORT).show();

            LottieAnimationView lottieAnimationView2 = findViewById(R.id.lottieAnimationView2);
            TextView TVemotion = findViewById(R.id.TVemotion);
            if(labels[0].equals("0")){
                TVemotion.setText("Happy");
                TVemotion.setTextColor(ContextCompat.getColor(this, R.color.clover_lime));
                lottieAnimationView2.setAnimation(R.raw.vector_dog_happy);
            }else if(labels[0].equals("1")){
                TVemotion.setText("Sad");
                TVemotion.setTextColor(ContextCompat.getColor(this, R.color.corn_blue));
                lottieAnimationView2.setAnimation(R.raw.vector_dog_sad);
            }else if(labels[0].equals("2")){
                TVemotion.setText("Angry");
                TVemotion.setTextColor(ContextCompat.getColor(this, R.color.red_orange));
                lottieAnimationView2.setAnimation(R.raw.vector_dog_angry);
            }else if(labels[0].equals("3")){
                TVemotion.setTextColor(ContextCompat.getColor(this, R.color.fern_100));
                TVemotion.setText("Relaxed");
                lottieAnimationView2.setAnimation(R.raw.vector_dog_relax);
            }
        }

        ImageButton ibBack = findViewById(R.id.ibBack);
        ibBack.setOnClickListener(v -> {
            finish();
        });
    }
}