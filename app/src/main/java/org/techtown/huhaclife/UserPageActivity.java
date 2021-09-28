package org.techtown.huhaclife;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class UserPageActivity extends AppCompatActivity {
    private RecyclerView rv_missionlist;
    private MissionRecyclerAdapter adapter;


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_userpage);
        rv_missionlist=(RecyclerView)findViewById(R.id.rv_missionlist);

        rv_missionlist=(RecyclerView)findViewById(R.id.rv_missionlist);
        adapter=new MissionRecyclerAdapter();
        rv_missionlist.setAdapter(adapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rv_missionlist.setLayoutManager(layoutManager);
        setItem(adapter);

    }

    public void setItem(MissionRecyclerAdapter adapter){
        adapter.addItem("5개의 컵 분리수거하기",5,2);
        adapter.addItem("10개의 컵 분리수거하기",10,0);
        adapter.addItem("15개의 컵 분리수거하기",15,5);
        adapter.addItem("20개의 컵 분리수거하기",20,10);
        adapter.addItem("25개의 컵 분리수거하기",25,0);
        adapter.addItem("포인트 환전해보기",1,0);

    }
}
