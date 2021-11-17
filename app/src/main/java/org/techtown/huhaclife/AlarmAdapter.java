package org.techtown.huhaclife;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.Holder>{
public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolder> {

    ArrayList<AlarmItem> arrayList;

    public AlarmAdapter(Context context, ArrayList<AlarmItem> list){
        arrayList=list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView contentView;
        public TextView dateView;

        public ViewHolder(View view){
            super(view);
            contentView=(TextView)view.findViewById(R.id.tv_alarm_content);
            dateView=(TextView)view.findViewById(R.id.tv_alarm_date);
            contentView.setText("");
            dateView.setText("");
        }

        public TextView getContentView(){
            return contentView;
        }

        public TextView getDateView(){
            return dateView;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_alarm, parent, false);

        ViewHolder viewholder=new ViewHolder(view);
        return viewholder;
    }

    //실제 각 뷰 홀더(position)에 데이터를 연결해주는 함수
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.contentView.setText(arrayList.get(position).content);
        holder.dateView.setText(arrayList.get(position).month+"월 "+arrayList.get(position).day+"일");

    }

    //리사이클러뷰 안에 들어갈 뷰 홀더의 개수
    @Override
    public int getItemCount() {
        if(arrayList!=null) {
            return arrayList.size();
        }
        return 0;
    }



}
