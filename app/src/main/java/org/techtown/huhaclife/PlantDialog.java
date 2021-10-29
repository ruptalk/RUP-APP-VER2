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
    private Context context;
    private String TAG="PlantDialog";

    public PlantDialog(Context context){
        this.context=context;
    }

    public void callFunction(String name){
        final Dialog dialog=new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.plant_dialog);
        dialog.show();

        //ImageView pimage=(ImageView)dialog.findViewById(R.id.img_plant);
        TextView pname=(TextView)dialog.findViewById(R.id.plant_dialog_name);
        TextView ptext=(TextView)dialog.findViewById(R.id.plant_dialog_text);
        //TextView pdate=(TextView)dialog.findViewById(R.id.tv_plantDate);
        //TextView planguage=(TextView)dialog.findViewById(R.id.tv_plantLanguage);

        String[] names=context.getResources().getStringArray(R.array.plant_name);
        String[] languages=context.getResources().getStringArray(R.array.plant_language);

        for(int i=0; i<15; i++){ //나중에 checklist 개수에 대해서도 정리해야 할듯?
            if(name.equals(names[i])){
                Log.d(TAG,names[i]+"\n"+languages[i]);
                pname.setText(names[i]);
                ptext.setText(languages[i]);
            }
        }

        final Button cancelButton=(Button)dialog.findViewById(R.id.btn_dialog_close);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}