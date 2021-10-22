package org.techtown.huhaclife;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.ViewHolder> {

    private Activity activity;
    private ArrayList<PlantItem> plantList=null;
    private OnItemClick mCallback;
    Dialog dialog_plant;
    ActivityManager manager;


    public PlantAdapter(ArrayList<PlantItem> list){
        this.activity=activity;
        plantList=list;
    }

    @NonNull
    @Override
    public PlantAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view= inflater.inflate(R.layout.garden_recycler_item,parent, false);
        PlantAdapter.ViewHolder viewHolder=new PlantAdapter.ViewHolder(view);
        return viewHolder;
    }

    //실제 각 뷰 홀더(position)에 데이터를 연결해주는 함수
    @Override
    public void onBindViewHolder(@NonNull PlantAdapter.ViewHolder holder, final int position) {
        PlantItem item=plantList.get(position);

        holder.plantImageButton.setImageDrawable(item.getPlantImage());
        holder.plantName.setText(item.getPlantName());

        holder.plantImageButton.setTag(position);
        //dialog_plant=new Dialog();
//        dialog_plant.requestWindowFeature(Window.FEATURE_NO_TITLE);
   //     dialog_plant.setContentView(R.layout.plant_dialog);

        holder.plantImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(holder.plantName.equals("선인장")){
                    PlantDialog plantDialog=new PlantDialog();
                    plantDialog.callFunction("선인장");
                }
                if(holder.plantName.equals("진달래")){
                    PlantDialog plantDialog=new PlantDialog();
                    plantDialog.callFunction("진달래");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if(plantList!=null) {
            return plantList.size();
        }
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageButton plantImageButton;
        TextView plantName;

        ViewHolder(@NonNull View view) {
            super(view);
            plantImageButton=(ImageButton)view.findViewById(R.id.plantImageButton);
            plantName=(TextView)view.findViewById(R.id.plantName);

            plantImageButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                }
            });

        }
    }

    private void showPlantDialog(){

    }
}
