package org.techtown.huhaclife;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.ViewHolder> {

    private ArrayList<PlantItem> plantList=null;

    //리스너 객체 참조를 저장하는 변수
    private OnItemClickListener mListener=null;

    //커스텀 리스너(Custom Listener) 인터페이스 정의
    public interface OnItemClickListener{
        void onItemClick(View v, int pos);
    }

    //OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener=listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView plantImage;
        TextView plantName;

        ViewHolder(View view){
            super(view);

            plantImage=view.findViewById(R.id.plantImage);
            plantName=view.findViewById(R.id.plantName);

            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    if(pos!=RecyclerView.NO_POSITION){
                        if(mListener!=null){
                            mListener.onItemClick(v,pos);
                        }
                    }
                }
            });
        }
    }

    PlantAdapter(ArrayList<PlantItem> list){
        plantList=list;
    }

    @NonNull
    @Override
    public PlantAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view= inflater.inflate(R.layout.plant_recycler_item,parent, false);
        PlantAdapter.ViewHolder viewHolder=new PlantAdapter.ViewHolder(view);
        return viewHolder;
    }

    //실제 각 뷰 홀더(position)에 데이터를 연결해주는 함수
    @Override
    public void onBindViewHolder(@NonNull PlantAdapter.ViewHolder holder, int position) {
        PlantItem item=plantList.get(position);

        holder.plantImage.setImageDrawable(item.getPlantImage());
        holder.plantName.setText(item.getPlantName());
    }

    @Override
    public int getItemCount() {
        if(plantList!=null) {
            return plantList.size();
        }
        return 0;
    }

}