package org.techtown.huhaclife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AlarmActivity extends AppCompatActivity {
    String month, day, uid, TAG="AlarmFragment";
    public int point, changed_point;
    public Date currentTime;
    public SimpleDateFormat dayFormat, monthFormat;

    Context context;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView alarmRecyclerView=null;
    AlarmAdapter adapter=null;
    ArrayList<AlarmItem> arrayList=new ArrayList<>();

    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference databaseReference, mReference;
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    ChildEventListener childEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        //알람 recyclerview
        alarmRecyclerView=(RecyclerView)findViewById(R.id.rv_alarm);
        layoutManager=new LinearLayoutManager(this);
        ((LinearLayoutManager) layoutManager).setReverseLayout(true);
        ((LinearLayoutManager) layoutManager).setStackFromEnd(true);
        alarmRecyclerView.setLayoutManager(layoutManager);

        adapter=new AlarmAdapter(context, arrayList);

        //addValueEventListener : 실시간으로 데이터베이스의 값 변화 감지해서
        // 변화가 있다면 onDataChange를 호출해주므로 전역변수 값도 그때그때 바뀜.
        uid=user.getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference();

        mReference=firebaseDatabase.getReference("Notice");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        })

        databaseReference.child("Notice").child("notice").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //point 변화가 있을 때 알람 기능 구현
        databaseReference.child("User").child(uid).child("point").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserInfo userInfo=snapshot.getValue(UserInfo.class);
                changed_point=userInfo.getPoint();
                Log.d(TAG,"point!!!!: "+changed_point);


                initDataset(); //오늘 날짜 받아오기
                Log.d(TAG, month + " " + day);

                arrayList.add(new AlarmItem("1point가 적립되었습니다.",month, day));
                adapter=new AlarmAdapter(context, arrayList);
                alarmRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        alarmRecyclerView.setAdapter(adapter);
    }

    private void initDataset() {

        for(int i=0; i<100; i++){
            currentTime= Calendar.getInstance().getTime();
            dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
            monthFormat = new SimpleDateFormat("MM", Locale.getDefault());

            month = monthFormat.format(currentTime);
            day = dayFormat.format(currentTime);
        }

    }
}