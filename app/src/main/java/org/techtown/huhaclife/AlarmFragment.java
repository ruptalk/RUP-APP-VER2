//package org.techtown.huhaclife;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.Locale;
//
//public class AlarmFragment extends Fragment {
//    String month, day, uid, TAG="AlarmFragment";
//    public int point, changed_point;
//    public Date currentTime;
//    public SimpleDateFormat dayFormat, monthFormat;
//
//    Context context;
//    RecyclerView.LayoutManager layoutManager;
//    RecyclerView recyclerView=null;
//    AlarmAdapter adapter=null;
//    ArrayList<AlarmItem> arrayList=new ArrayList<>();
//
//    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
//    DatabaseReference databaseReference;
//
//    public AlarmFragment(){ }
//
//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        initDataset();
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        ViewGroup view=(ViewGroup)inflater.inflate(R.layout.alarm, container,false);
//        context=view.getContext();
//
//
//        //addValueEventListener : 실시간으로 데이터베이스의 값 변화 감지해서
//        // 변화가 있다면 onDataChange를 호출해주므로 전역변수 값도 그때그때 바뀜.
//        uid=user.getUid();
//        databaseReference= FirebaseDatabase.getInstance().getReference();
//        databaseReference.child("Users2").child(uid).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                UserInfo userInfo=snapshot.getValue(UserInfo.class);
//                changed_point=Integer.parseInt(userInfo.getPoint());
//                Log.d(TAG,"point!!!!: "+changed_point);
//
//
//                    currentTime = Calendar.getInstance().getTime();
//                    dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
//                    monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
//
//                    month = monthFormat.format(currentTime);
//                    day = dayFormat.format(currentTime);
//                    Log.d(TAG, month + " " + day);
//
//                    arrayList.add(new AlarmItem("1point가 적립되었습니다.",month, day));
//                    adapter=new AlarmAdapter(context, arrayList);
//                    recyclerView.setAdapter(adapter);
//                }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//
//        return view;
//    }
//
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        //adapter=new AlarmAdapter(context, arrayList);
//
//        recyclerView=(RecyclerView)view.findViewById(R.id.recyceler_view);
//        layoutManager=new LinearLayoutManager(getActivity());
//        ((LinearLayoutManager) layoutManager).setReverseLayout(true);
//        ((LinearLayoutManager) layoutManager).setStackFromEnd(true);
//        recyclerView.setLayoutManager(layoutManager);
//
//        adapter=new AlarmAdapter(context, arrayList);
//        recyclerView.setAdapter(adapter);
//
//    }
//    private void initDataset() {
//        //ArrayList<AlarmItem> mSearchData = new ArrayList<>();
//
//        for(int i=0; i<100; i++){
//            currentTime= Calendar.getInstance().getTime();
//            dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
//            monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
//
//            month = monthFormat.format(currentTime);
//            day = dayFormat.format(currentTime);
//
//            arrayList.add(new AlarmItem(i+"point가 적립되었습니다.",month, day));
//
//
//        }
//
//    }
//}