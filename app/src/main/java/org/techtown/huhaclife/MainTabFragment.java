package org.techtown.huhaclife;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class MainTabFragment extends Fragment {
    //각각의 fragment마다 인스턴스를 반환해줄 메소드를 생성합니다ㅏ.
    public static MainTabFragment newInstance(){
        return new MainTabFragment();
    }

    private TextView tv_userpoint;
    private Button btn_plantGo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root=(ViewGroup)inflater.inflate(R.layout.main_tab_fragment,container,false);
        tv_userpoint=(TextView)root.findViewById(R.id.tv_userpoint);
        btn_plantGo=(Button)root.findViewById(R.id.btn_plantGo);

        DatabaseReference databaseReference=((MainActivity)getActivity()).databaseReference;
        String uid=((MainActivity)getActivity()).uid;

        databaseReference.child("User").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserInfo userInfo=snapshot.getValue(UserInfo.class);
                tv_userpoint.setText(String.valueOf(userInfo.getPoint()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btn_plantGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //프래그먼트에서 액티비티 변수 접근: https://jhshjs.tistory.com/16
                ViewPager vp=((MainActivity)getActivity()).vp_page;
                vp.setCurrentItem(2);
            }
        });

        return root;
    }
}
