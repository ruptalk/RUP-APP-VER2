package org.techtown.huhaclife;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class GardenTabFragment extends Fragment {
<<<<<<< HEAD

    ImageButton imgBtn_plantCactus, imgBtn_plantAzalea;
    Dialog dialog_plant;
=======
    public static GardenTabFragment newInstance(){
        return new GardenTabFragment();
    }
>>>>>>> upstream/main

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view=(ViewGroup)inflater.inflate(R.layout.garden_tab_fragment,container,false);

        imgBtn_plantCactus=(ImageButton)view.findViewById(R.id.imgBtn_plantCactus);
        imgBtn_plantAzalea=(ImageButton)view.findViewById(R.id.imgBtn_plantAzalea);

        dialog_plant=new Dialog(getActivity().getApplicationContext());
        dialog_plant.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_plant.setContentView(R.layout.plant_dialog);

        imgBtn_plantCactus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                PlantDialog plantDialog=new PlantDialog(getActivity());
                plantDialog.callFunction("선인장");

            }
        });

        imgBtn_plantAzalea.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                PlantDialog plantDialog=new PlantDialog(getActivity());
                plantDialog.callFunction("진달래");
            }
        });


        return view;
    }

}
