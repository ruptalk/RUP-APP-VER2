package org.techtown.huhaclife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private ImageView ib_userprofile;
    private ImageButton ib_info;
    private TextView tv_id;
    public ViewPager vp_page;
    private TabLayout tl_item;
    private FrameLayout container;
    private Fragment mainTabFragment, qrTabFragment,gardenTabFragment;
    private TablelayoutAdapter tablelayoutAdapter=new TablelayoutAdapter(getSupportFragmentManager());
    private FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;
    public String uid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ib_userprofile=(ImageView) findViewById(R.id.ib_userprofile);
        ib_info=(ImageButton)findViewById(R.id.ib_info);
        vp_page=(ViewPager)findViewById(R.id.vp_page);
        tl_item=(TabLayout)findViewById(R.id.tl_item);
        tv_id=(TextView)findViewById(R.id.tv_id);

        settingitem(vp_page);
        tl_item.addTab(tl_item.newTab().setText("홈"));
        tl_item.addTab(tl_item.newTab().setText("QR"));
        tl_item.addTab(tl_item.newTab().setText("정원"));
        tl_item.setTabGravity(TabLayout.GRAVITY_FILL);
        tl_item.setupWithViewPager(vp_page);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference();

        uid=firebaseUser.getUid();

        databaseReference.child("User").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserInfo userInfo=snapshot.getValue(UserInfo.class);
                tv_id.setText(userInfo.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ib_userprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),UserPageActivity.class);
                startActivity(intent);
            }
        });




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