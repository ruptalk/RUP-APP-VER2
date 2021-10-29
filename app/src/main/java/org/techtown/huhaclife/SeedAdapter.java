package org.techtown.huhaclife;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SeedAdapter extends RecyclerView.Adapter<SeedAdapter.ViewHolder> implements OnPersonItemClickListener {
    ArrayList<Drawable>items=new ArrayList<Drawable>();
    static OnPersonItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View itemView=inflater.inflate(R.layout.item_seed,parent,false);
        return new ViewHolder(itemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Drawable imageView=items.get(position);
        holder.setItem(imageView);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener!=null){
            listener.onItemClick(holder,view,position);
            System.out.println("리스너 반응함.");
        }
    }

    public void setOnItemClickListener(OnPersonItemClickListener listener){
        this.listener=listener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView seed;

        public ViewHolder(@NonNull View itemView, SeedAdapter seedAdapter) {
            super(itemView);

            seed=(ImageView)itemView.findViewById(R.id.itemseed);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAbsoluteAdapterPosition();
                    view.setBackgroundColor(Color.YELLOW);

                    if(listener!=null){
                        listener.onItemClick(ViewHolder.this,view,position);

                    }
                }
            });
        }
        public void setItem(Drawable drawable){
            seed.setImageDrawable(drawable);
        }
    }


    public void addItem(Drawable item){
        items.add(item);
    }
    public void setItems(ArrayList<Drawable>items){
        this.items=items;
    }
    public Drawable getItem(int posision){
        return items.get(posision);
    }

}
