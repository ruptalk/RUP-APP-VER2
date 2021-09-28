package org.techtown.huhaclife;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private ImageView ib_userprofile;
    private ImageButton ib_info;
    private ViewPager vp_page;
    private TabLayout tl_item;
    private Drawable dw_userprofile;
    private TablelayoutAdapter tablelayoutAdapter=new TablelayoutAdapter(getSupportFragmentManager());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ib_userprofile=(ImageView) findViewById(R.id.ib_userprofile);
        ib_info=(ImageButton)findViewById(R.id.ib_info);
        vp_page=(ViewPager)findViewById(R.id.vp_page);
        tl_item=(TabLayout)findViewById(R.id.tl_item);
        dw_userprofile=(Drawable)getDrawable(R.drawable.userprofile_shape);

        settingitem(vp_page);
        tl_item.addTab(tl_item.newTab().setText("홈"));
        tl_item.addTab(tl_item.newTab().setText("QR"));
        tl_item.addTab(tl_item.newTab().setText("정원"));
        tl_item.setTabGravity(TabLayout.GRAVITY_FILL);
        tl_item.setupWithViewPager(vp_page);

        vp_page.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tl_item));


       ib_userprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),UserPageActivity.class);
                startActivity(intent);
            }
        });




    }
    protected void settingitem(ViewPager v){
        tablelayoutAdapter.addFragment(new MainTabFragment(),"홈");
        tablelayoutAdapter.addFragment(new QrTabFragment(),"QR");
        tablelayoutAdapter.addFragment(new GardenTabFragment(),"정원");
        v.setAdapter(tablelayoutAdapter);
    }
}