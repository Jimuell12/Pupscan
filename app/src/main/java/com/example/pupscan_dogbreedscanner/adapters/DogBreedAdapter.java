package com.example.pupscan_dogbreedscanner.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pupscan_dogbreedscanner.Activity_DogResult;
import com.example.pupscan_dogbreedscanner.Activity_Dog_Emotion;
import com.example.pupscan_dogbreedscanner.ModelBCS;
import com.example.pupscan_dogbreedscanner.ModelBreed;
import com.example.pupscan_dogbreedscanner.R;

import java.util.Arrays;
import java.util.List;

public class DogBreedAdapter extends RecyclerView.Adapter<DogBreedAdapter.DogViewHolder> {

    private Context context;
    private List<ModelBreed> dogList; // Replace DogModel with your actual model class


    public DogBreedAdapter(Context context, List<ModelBreed> dogList) {
        this.context = context;
        this.dogList = dogList;
    }

    @NonNull
    @Override
    public DogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_dogresult, parent, false);
        return new DogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DogViewHolder holder, int position) {
        ModelBreed dog = dogList.get(position);

        // Set data to views
        holder.tvDogName.setText(dog.getDogName());
        holder.tvHeight.setText(dog.getHeight());
        holder.tvWeight.setText(dog.getWeight());
        holder.tvLifespan.setText(dog.getLifespan());
        String imageName = dog.getImage();
        int imageResourceId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        if (imageResourceId != 0) {
            holder.iv_dogIcon.setImageResource(imageResourceId);
        }

        holder.itemView.setOnClickListener(v -> {
            String model = dog.getModel();
            String[] labels = new String[]{dog.getLabel()}; // Convert to array
            float[] scores = new float[]{dog.getScore()}; // Convert to array
            Toast.makeText(context, Arrays.toString(labels), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(context, Activity_DogResult.class);
            intent.putExtra("history", true);
            intent.putExtra("label", labels); // Pass as array
            intent.putExtra("scores", scores); // Pass as array
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dogList.size();
    }

    public class DogViewHolder extends RecyclerView.ViewHolder {
        TextView tvDogName, tvHeight, tvWeight, tvLifespan;
        ImageView iv_dogIcon;

        public DogViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDogName = itemView.findViewById(R.id.tv_dogName);
            tvHeight = itemView.findViewById(R.id.tv_height_num);
            tvWeight = itemView.findViewById(R.id.tv_weight_num);
            tvLifespan = itemView.findViewById(R.id.tv_lifespan_num);
            iv_dogIcon = itemView.findViewById(R.id.iv_dogIcon);

        }
    }
}
