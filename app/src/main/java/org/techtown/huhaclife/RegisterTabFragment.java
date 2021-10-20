package org.techtown.huhaclife;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class RegisterTabFragment extends Fragment {

    private EditText edt_id,edt_pw,edt_repw,edt_name;
    private Button btn_reg;
    private FirebaseAuth root;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String id,pw,repw,name,uid;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup=(ViewGroup)inflater.inflate(R.layout.register_tab_fragment,container,false);
        edt_id=(EditText)viewGroup.findViewById(R.id.edt_id);
        edt_name=(EditText)viewGroup.findViewById(R.id.edt_name);
        edt_pw=(EditText)viewGroup.findViewById(R.id.edt_pw);
        edt_repw=(EditText)viewGroup.findViewById(R.id.edt_repw);
        btn_reg=(Button)viewGroup.findViewById(R.id.btn_reg);
        database=FirebaseDatabase.getInstance();
        reference=database.getReference();
        root=FirebaseAuth.getInstance();




        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=edt_name.getText().toString().trim();
                id=edt_id.getText().toString().trim();
                pw=edt_pw.getText().toString().trim();
                repw=edt_repw.getText().toString().trim();


                //에러설정
                if(name.isEmpty())
                    edt_name.setError("이름을 입력해주십시오");
                if(id.isEmpty())
                    edt_id.setError("이메일을 입력해주세요");
                if(pw.isEmpty())
                    edt_pw.setError("패스워드를 입력해주세요");
                if(repw.isEmpty())
                    edt_repw.setError("패스워드를 재입력해주세요");
                if(pw.length()<6)
                    edt_pw.setError("패스워드는 6자리 이상입니다.");

                //사용자 계정에 추가하고 Database에도 추가
                if(pw.equals(repw)&&!pw.isEmpty()){
                    ProgressDialog dialog=new ProgressDialog(getContext());
                    dialog.setMessage("회원가입하는 중");
                    dialog.show();
                    root.createUserWithEmailAndPassword(id,pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                dialog.dismiss();
                                FirebaseUser user=root.getCurrentUser();
                                //realtimedatabase에 값 추가하고 login tab으로 이동
                                uid=user.getUid();
                                UserInfo userInfo=new UserInfo();
                                userInfo.setEmail(id);
                                userInfo.setName(name);
                                userInfo.setPw(pw);
                                userInfo.setPoint(0);
                                reference.child("User").child(uid).setValue(userInfo);
                                ViewPager viewPager=((LoginActivity)getActivity()).viewPager;
                                viewPager.setCurrentItem(0);
                            }
                            else{
                                System.out.println("회원가입이 실패되었습니다.");
                            }
                        }
                    });

                }
                else{
                    edt_repw.setError("비밀번호가 일치하지 않습니다.");
                }
            }
        });








        return viewGroup;
    }
}
/* 애니메이션 지정
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
        btn_reg.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(900).start();*/
