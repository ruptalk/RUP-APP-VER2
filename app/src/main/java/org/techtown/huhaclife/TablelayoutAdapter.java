package org.techtown.huhaclife;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class TablelayoutAdapter extends FragmentPagerAdapter{
    private final List<Fragment> mfragment=new ArrayList<>();
    private final List<String> mtitle=new ArrayList<>();

    /*
    * FragmentPagerAdpater: 제한된(고정된) 개수의 항목(Fragment)들에 적합함 :
    * 한번 생성되면 Fragment 인스턴스를 FragmentManager에서 제거하지 않음
    * 현재보이지 않은 .Fragment에서 View들은 detech한다.
    * 만약, 페이지수를 알 수 없거나 메모리낭비가 심할때는 FragmentStatePagerAdapter를 사용한다. */

    //NonNull : null이 될 수 없는 변수 , 매개변수 또는 반환값을 나타낸다.
    //Nullable: null이 될 수 있는 변수, 매개변수 또는 반환값을 나타내낸다.



    public TablelayoutAdapter(@NonNull FragmentManager fm){
        super(fm);
    }

    public void addFragment(Fragment fragment,String title){
        mfragment.add(fragment);
        mtitle.add(title);
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mtitle.get(position);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mfragment.get(position);
    }

    @Override
    public int getCount() {
        return mfragment.size();
    }
}
