package com.example.pupscan_dogbreedscanner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.airbnb.lottie.LottieAnimationView;
import com.example.pupscan_dogbreedscanner.R;

public class SliderAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    // Arrays for slide images and slide descriptions
    private int[] slideImages = {
            R.raw.vector_onboard1,
            R.raw.vector_onboard2,
            R.raw.vector_onboard3,
            R.raw.vector_onboard4,
    };

    private String[] slideDescriptions = {
            "Explore Dog Breeds",
            "Scan Dog Emotion",
            "Check BCS Score",
            "Dogpedia"

    };

    private String[] slidemoredesc = {
            "Dive into a rich collection of dog breeds with detailed profiles.",
            "Understand your dog's emotions through advanced scanning technology.",
            "Monitor your dog's health with the Body Condition Score (BCS) feature.",
            "Access information on height, weight, lifespan, and compatibility – all in one centralized hub."
    };

    @Override
    public int getCount() {
        return slideImages.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slides_layout, container, false);

        LottieAnimationView lottieAnimationView = view.findViewById(R.id.slide_image);
        TextView slideDescriptionTextView = view.findViewById(R.id.slide_description);
        TextView moredesc = view.findViewById(R.id.moredesc);

        lottieAnimationView.setAnimation(slideImages[position]);
        slideDescriptionTextView.setText(slideDescriptions[position]);
        moredesc.setText(slidemoredesc[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
