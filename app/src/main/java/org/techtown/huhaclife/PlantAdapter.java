package org.techtown.huhaclife;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PlantAdapter extends PagerAdapter {

    private ArrayList<PlantItem> items;
    private LayoutInflater layoutInflater;
    private Context context;

    public PlantAdapter(ArrayList<PlantItem> items, Context context){
        this.items=items;
        this.context=context;
    }

    @Override
    public int getCount() {
        if(items!=null) {
            return items.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.plant_recycler_item, container, false);

        TextView plantName, plantLanguage;
        ImageView plantImage;

        plantName=view.findViewById(R.id.plant_name);
        plantLanguage=view.findViewById(R.id.plant_language);
        plantImage=view.findViewById(R.id.plant_image);

        plantImage.setImageDrawable(items.get(position).getPlantImage());
        plantName.setText(items.get(position).getPlantName());
        plantLanguage.setText(items.get(position).getPlantLanguage());

        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Toast.makeText(context.getApplicationContext(), "클릭! position: "+position, Toast.LENGTH_SHORT).show();
                PlantDialog plantDialog=new PlantDialog(context);
                plantDialog.callFunction(items.get(position).getPlantName());

            }
        });

        container.addView(view,0);
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
