package org.techtown.huhaclife;

import android.graphics.drawable.Drawable;

public class PlantItem {
    private String plantName;
    private Drawable plantImage;

    public void setPlantImage(Drawable image) { this.plantImage=image; }

    public void setPlantName(String name) { this.plantName=name; }

    public Drawable getPlantImage() { return plantImage; }

    public String getPlantName() { return plantName;}

}
