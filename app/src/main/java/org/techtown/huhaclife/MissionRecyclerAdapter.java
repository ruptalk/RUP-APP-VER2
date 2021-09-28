package org.techtown.huhaclife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MissionRecyclerAdapter extends RecyclerView.Adapter<MissionRecyclerAdapter.ViewHolder> {

    private ArrayList<Mission> arrayList=new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View itemview=inflater.inflate(R.layout.mission_customlistview,parent,false);
        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Mission item=arrayList.get(position);
        holder.setItem(item);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void addItem(String context, int progresspercent,int percent){
        Mission item=new Mission(context,progresspercent,percent);
        arrayList.add(item);
    }
    public void setItem(ArrayList<Mission> items){
        arrayList=items;
    }
    public Mission getItem(int position){
        return arrayList.get(position);
    }
    public void setItem(int position,Mission item){
        arrayList.set(position,item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView context;
        private ProgressBar progressbar;

        public ViewHolder(View view){
            super(view);
            context=(TextView)view.findViewById(R.id.tv_missioncontext);
            progressbar=(ProgressBar)view.findViewById(R.id.pb_progress);
        }

        public void setItem(Mission item){
            context.setText(item.getContext());
            progressbar.setMax(item.getProgresspercent());
            progressbar.setProgress(item.getPercent());
        }
    }
}
