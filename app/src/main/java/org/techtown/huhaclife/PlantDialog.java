package org.techtown.huhaclife;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PlantDialog {
    private Context context;

    public PlantDialog(){

    }

    public void callFunction(String plantName){
        final Dialog dialog=new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.plant_dialog);
        dialog.show();

        ImageView pimage=(ImageView)dialog.findViewById(R.id.img_plant);
        TextView pname=(TextView)dialog.findViewById(R.id.tv_plantName);
        TextView pdate=(TextView)dialog.findViewById(R.id.tv_plantDate);
        TextView planguage=(TextView)dialog.findViewById(R.id.tv_plantLanguage);

        if(plantName.equals("선인장")){
            pimage.setImageResource(R.drawable.plant_cactus);
            pname.setText("이름: 선인장");
            pdate.setText("키운 날짜: 21/10/03");
            planguage.setText("꽃말: 타오르는 열정");
        }
        else if(plantName.equals("진달래")){
            pimage.setImageResource(R.drawable.plant_azalea);
            pname.setText("이름: 진달래");
            pdate.setText("키운 날짜: 21/10/05");
            planguage.setText("꽃말: 사랑의 기쁨");
        }
        else{
            pimage.setImageResource(R.drawable.p_logo);
            pname.setText("이름: ");
            pdate.setText("키운 날짜: ");
            planguage.setText("꽃말: ");
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

/*
public class PlantFragmentDialog extends DialogFragment {
    private Fragment fragment;
    private static final String TAG="PlantfragmentDialog";
    private static final String MAIN_MSG="dialog_plant";
    private String plantMsg;

    public static PlantFragmentDialog newInstance(String mainMsg){
        Bundle bundle=new Bundle();
        bundle.putString(MAIN_MSG, mainMsg);

        PlantFragmentDialog fragment=new PlantFragmentDialog();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            plantMsg=getArguments().getString(MAIN_MSG);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        View view=getActivity().getLayoutInflater().inflate(R.layout.plant_dialog,null);

        builder.setView(view);
        Dialog dialog=builder.create();
        return dialog;
    }
}


 */