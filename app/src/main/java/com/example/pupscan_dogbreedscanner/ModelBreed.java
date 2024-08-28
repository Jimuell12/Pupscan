package com.example.pupscan_dogbreedscanner;

public class ModelBreed {
    private String dogName;
    private String height;
    private String weight;
    private String lifespan;
    private String model;
    private String label;
    private float score;
    private String image;

    // Constructor
    public ModelBreed(String dogName, String height, String weight, String lifespan, String model, String label, float score, String image) {
        this.dogName = dogName;
        this.height = height;
        this.weight = weight;
        this.lifespan = lifespan;
        this.model = model;
        this.label = label;
        this.score = score;
        this.image = image;
    }

    // Getter methods
    public String getDogName() {
        return dogName;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public String getLifespan() {
        return lifespan;
    }
    public String getModel(){
        return model;
    }
    public String getLabel(){
        return label;
    }
    public float getScore() {
        return score;
    }

    public String getImage() {
        return image;
    }
}
