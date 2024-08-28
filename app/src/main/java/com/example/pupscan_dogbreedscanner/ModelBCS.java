package com.example.pupscan_dogbreedscanner;

public class ModelBCS {
    private String dogName;
    private String height;
    private String weight;
    private String lifespan;

    // Constructor
    public ModelBCS(String dogName, String height, String weight, String lifespan) {
        this.dogName = dogName;
        this.height = height;
        this.weight = weight;
        this.lifespan = lifespan;
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
}
