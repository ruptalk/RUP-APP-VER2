package org.techtown.huhaclife;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PlantDialog {
    private static Context context;

    public PlantDialog(Context context){
        this.context=context;
    }

    public void callFunction(String name, int len){
        final Dialog dialog=new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.plant_dialog);
        dialog.show();

        ImageView pimage=(ImageView)dialog.findViewById(R.id.img_plant);
        TextView pname=(TextView)dialog.findViewById(R.id.tv_plantName);
        TextView pdate=(TextView)dialog.findViewById(R.id.tv_plantDate);
        TextView planguage=(TextView)dialog.findViewById(R.id.tv_plantLanguage);

        TypedArray typedArray=context.getResources().obtainTypedArray(R.array.plant_list);
        String[] names=context.getResources().getStringArray(R.array.plant_name);
        String[] languages=context.getResources().getStringArray(R.array.plant_language);

        for(int i=0; i<len; i++){
            if(name.equals(names[i])){
                pimage.setImageDrawable(typedArray.getDrawable(i));
                pname.setText(names[i]);
                planguage.setText(languages[i]);
                break;
            }
        }


        final Button cancelButton=(Button)dialog.findViewById(R.id.btn_close);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


}
