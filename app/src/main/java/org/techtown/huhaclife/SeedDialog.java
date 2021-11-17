package org.techtown.huhaclife;

import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class SeedDialog extends Dialog {

    private RecyclerView seedlist;
    private android.widget.Button select;
    private final int size=8;
    private GridLayoutManager gridLayoutManager;
    public static int seednumber=-1;
    private SeedDialogListener seedDialogListener;
    public MainActivity mainActivity;

    public interface SeedDialogListener{
        void ContentTranslate(int a);
    }

    public SeedDialog(@NonNull Context context, SeedDialogListener seedDialogListener) {
        super(context);
        this.setContentView(R.layout.dialog_seed);
        this.seedDialogListener=seedDialogListener;
    }

    public SeedDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected SeedDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        seedlist=(RecyclerView)findViewById(R.id.rv_seedlist);
        select=(Button)findViewById(R.id.btn_seeds);
        gridLayoutManager=new GridLayoutManager(getContext(),4);
        seedlist.setLayoutManager(gridLayoutManager);


        SeedAdapter seedAdapter=new SeedAdapter();
        TypedArray typedArray=getContext().getResources().obtainTypedArray(R.array.seed);
        int size=typedArray.length();
        for(int i=0;i<size;i++){
            seedAdapter.addItem(typedArray.getDrawable(i));
        }
        seedlist.setAdapter(seedAdapter);
        seedAdapter.setOnItemClickListener(new OnPersonItemClickListener() {
            @Override
            public void onItemClick(SeedAdapter.ViewHolder holder, View view, int position) {
                seednumber=position;
                //((MainActivity) getContext()).plant.setAnimation("plant7.json");
                //dismiss();

            }
        });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(seednumber!=-1){

                    seedDialogListener.ContentTranslate(seednumber);
                    dismiss();

                }
                else{
                    Toast.makeText(getContext(),"씨앗을 선택해주세요",Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

}
