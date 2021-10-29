package org.techtown.huhaclife;

import android.graphics.drawable.Drawable;

public class PlantItem {
    private String plantName, plantLanguage;
    private Drawable plantImage;

    public PlantItem(Drawable image, String name, String language){
        this.plantImage=image;
        this.plantName=name;
        this.plantLanguage=language;
    }

    public void setPlantImage(Drawable image) { this.plantImage=image; }

    public void setPlantName(String name) { this.plantName=name; }

    public void setPlantLanguage(String language) { this.plantLanguage=language; }

    public Drawable getPlantImage() { return plantImage; }

    public String getPlantName() { return plantName;}

    public String getPlantLanguage(){ return plantLanguage; }

}

