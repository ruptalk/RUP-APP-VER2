package org.techtown.huhaclife;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LoginTabFragment extends Fragment {

    private EditText student_id,student_pw;
    private Button btn_login;
    private TextView tv_fog;
    float v=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root=(ViewGroup)inflater.inflate(R.layout.login_tab_fragment,container,false);
        student_id=(EditText)root.findViewById(R.id.student_id);
        student_pw=(EditText)root.findViewById(R.id.student_pw);
        btn_login=(Button)root.findViewById(R.id.student_login);
        tv_fog=(TextView)root.findViewById(R.id.tv_fogpw);


        student_id.setTranslationX(800);
        student_pw.setTranslationX(800);
        btn_login.setTranslationX(800);
        tv_fog.setTranslationX(800);

        student_id.setAlpha(v);
        student_pw.setAlpha(v);
        btn_login.setAlpha(v);
        tv_fog.setAlpha(v);

        student_id.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        student_pw.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        btn_login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        tv_fog.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

}
