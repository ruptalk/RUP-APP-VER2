package org.techtown.huhaclife;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RegisterTabFragment extends Fragment {

    private EditText edt_id,edt_pw,edt_repw,edt_name;
    private Button btn_reg;
    float v=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup=(ViewGroup)inflater.inflate(R.layout.register_tab_fragment,container,false);
        edt_id=(EditText)viewGroup.findViewById(R.id.edt_id);
        edt_name=(EditText)viewGroup.findViewById(R.id.edt_name);
        edt_pw=(EditText)viewGroup.findViewById(R.id.edt_pw);
        edt_repw=(EditText)viewGroup.findViewById(R.id.edt_repw);
        btn_reg=(Button)viewGroup.findViewById(R.id.btn_reg);

        edt_id.setTranslationX(1000);
        edt_name.setTranslationX(1000);
        edt_pw.setTranslationX(1000);
        edt_repw.setTranslationX(1000);
        btn_reg.setTranslationX(1000);

        edt_id.setAlpha(v);
        edt_name.setAlpha(v);
        edt_pw.setAlpha(v);
        edt_repw.setAlpha(v);
        btn_reg.setAlpha(v);

        edt_id.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        edt_name.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        edt_pw.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        edt_repw.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();
        btn_reg.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(900).start();

        return viewGroup;
    }
}
