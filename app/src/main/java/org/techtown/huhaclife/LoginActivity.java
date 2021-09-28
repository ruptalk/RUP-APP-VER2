package org.techtown.huhaclife;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class LoginActivity extends FragmentActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    FloatingActionButton fb,google,kakao;
    private TextView tv_rup;
    float v=0;
    final TablelayoutAdapter adapter=new TablelayoutAdapter(getSupportFragmentManager());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tabLayout=findViewById(R.id.tab_layout1);
        viewPager=findViewById(R.id.view_pager);
        fb=(FloatingActionButton) findViewById(R.id.fab_feacebook);
        google=(FloatingActionButton)findViewById(R.id.fab_google);
        kakao=(FloatingActionButton)findViewById(R.id.fab_kakao);
        tv_rup=(TextView)findViewById(R.id.tv_rup);


        setViewPager(viewPager);
        tabLayout.addTab(tabLayout.newTab().setText("로그인"));
        tabLayout.addTab(tabLayout.newTab().setText("회원가입"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        fb.setTranslationY(300);//setTranslationY가 무엇이여
        google.setTranslationY(300);
        kakao.setTranslationY(300);
        tabLayout.setTranslationY(300);
        tv_rup.setTranslationY(-300);



        fb.setAlpha(v);
        google.setAlpha(v);
        kakao.setAlpha(v);
        tabLayout.setAlpha(v);
        tv_rup.setAlpha(v);


        fb.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        google.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        kakao.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        tabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();
        tv_rup.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(400).start();


    }
    public void setViewPager(ViewPager viewPager){
        adapter.addFragment(new LoginTabFragment(),"로그인");
        adapter.addFragment(new RegisterTabFragment(),"회원가입");
        viewPager.setAdapter(adapter);
    }

}

/*
* alpha : 알파의 값(0[투명]~100[불투명])의 해당하는 헥사값을 알아보자
* 20%: 255*0.2=51 16진수 헥사 값 33 :투명도가 80%에 해당한다고 한다.
*
* duration: 효과시간 1/1000sec단위
* setstartDelay:  시작하고 몇초 후에 실행*/