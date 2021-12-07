package org.techtown.huhaclife;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginTabFragment extends Fragment {

    private EditText edt_email,edt_pw;
    private Button btn_login;
    private TextView tv_fog;
    private FirebaseAuth root1;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String email, pw;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root=(ViewGroup)inflater.inflate(R.layout.login_tab_fragment,container,false);
        edt_email=(EditText)root.findViewById(R.id.student_id);
        edt_pw=(EditText)root.findViewById(R.id.student_pw);
        btn_login=(Button)root.findViewById(R.id.student_login);
        tv_fog=(TextView)root.findViewById(R.id.tv_fogpw);
        root1= FirebaseAuth.getInstance();
        user=root1.getCurrentUser();

       /* //사용자 권한이 있을 경우
        if(user!=null){
           startActivity(new Intent(getActivity(), MainActivity.class));
        }*/


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=edt_email.getText().toString().trim();
                pw=edt_pw.getText().toString().trim();
                if(email.isEmpty())
                    edt_email.setError("이메일을 입력하세요.");
                if(pw.isEmpty())
                    edt_pw.setError("비밀번호를 입력하세요");
                if(!email.isEmpty()&&!pw.isEmpty()) {
                    root1.signInWithEmailAndPassword(email, pw).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(getContext(), MainActivity.class));
                            } else {
                                Toast.makeText(getContext(), "로그인에 실패하셨습니다.", Toast.LENGTH_SHORT).show();
                                edt_email.setText("");
                                edt_pw.setText(null);
                            }
                        }
                    });
                }

            }
        });



        return root;
    }

}
/*
//애니메이션 설정
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
*/
