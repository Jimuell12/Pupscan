package com.example.pupscan_dogbreedscanner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pupscan_dogbreedscanner.ModelBCS;
import com.example.pupscan_dogbreedscanner.R;

import java.util.List;
import java.util.Objects;

public class DogBCSAdapter extends RecyclerView.Adapter<DogBCSAdapter.DogViewHolder> {

    private Context context;
    private List<ModelBCS> dogList; // Replace DogModel with your actual model class


    public DogBCSAdapter(List<ModelBCS> dogList) {
        this.dogList = dogList;
    }

    @NonNull
    @Override
    public DogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_dogbcs, parent, false);
        return new DogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DogViewHolder holder, int position) {
        ModelBCS dog = dogList.get(position);

        // Set data to views
        holder.tv_dogName.setText(dog.getDogName());
        holder.tv_gender.setText(dog.getHeight());
        holder.tv_weight_num.setText(dog.getWeight());
        holder.tv_dogScore.setText(dog.getLifespan());

        if(Objects.equals(dog.getLifespan(), "1")){
            holder.iv_dogIcon.setImageResource(R.drawable.vector_bcs_1);
        }else if(Objects.equals(dog.getLifespan(), "2")){
            holder.iv_dogIcon.setImageResource(R.drawable.vector_bcs_2);
        }else if(Objects.equals(dog.getLifespan(), "3")){
            holder.iv_dogIcon.setImageResource(R.drawable.vector_bcs_3);
        }else if(Objects.equals(dog.getLifespan(), "4")){
            holder.iv_dogIcon.setImageResource(R.drawable.vector_bcs_4);
        }else if(Objects.equals(dog.getLifespan(), "5")){
            holder.iv_dogIcon.setImageResource(R.drawable.vector_bcs_5);
        }else {
            holder.iv_dogIcon.setImageResource(R.drawable.vector_bcs_1);
        }
    }

    @Override
    public int getItemCount() {
        return dogList.size();
    }

    public class DogViewHolder extends RecyclerView.ViewHolder {
        TextView tv_dogName, tv_gender, tv_weight_num, tv_dogScore;
        ImageView iv_dogIcon;

        public DogViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_dogName = itemView.findViewById(R.id.tv_dogName);
            tv_gender = itemView.findViewById(R.id.tv_gender);
            tv_weight_num = itemView.findViewById(R.id.tv_weight_num);
            tv_dogScore = itemView.findViewById(R.id.tv_dogScore);
            iv_dogIcon = itemView.findViewById(R.id.iv_dogIcon);
        }
    }
}
