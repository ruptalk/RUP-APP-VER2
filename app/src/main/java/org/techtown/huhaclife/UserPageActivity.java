package org.techtown.huhaclife;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;

public class UserPageActivity extends AppCompatActivity implements AutoPermissionsListener {

    RecyclerView plantRecyclerView=null;
    PlantAdapter plantAdapter=null;
    ArrayList<PlantItem> plantList;
    GridLayoutManager gridLayoutManager;
    String uid, checklist="000000000000000";
    private String TAG="UserPageActivity";

    private ImageView userprofile;
    private File file;
    private DatabaseReference rootRef= FirebaseDatabase.getInstance().getReference();
    private FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_userpage);

        //user profile
        userprofile=(ImageView)findViewById(R.id.iv_userprofile);
        userprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileDialog dialog=new ProfileDialog(UserPageActivity.this, new ProfileDialog.ProfileDialogListener() {
                    @Override
                    public void CameraClick() {

                        AutoPermissions.Companion.loadAllPermissions(UserPageActivity.this,101);
                        if (file == null) {
                            file = createFile();
                        }
                        Uri fileUri = FileProvider.getUriForFile(UserPageActivity.this, "org.techtown.huhaclife.fileprovider", file);
                        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent1.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        AutoPermissions.Companion.loadAllPermissions(UserPageActivity.this, 101);
                        if (intent1.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(intent1, 101);
                        }

                    }

                });
                dialog.show();
            }
        });

        //recycler view
        plantRecyclerView=(RecyclerView)findViewById(R.id.garden_recycler_view);
        plantList=new ArrayList<PlantItem>();

        plantAdapter=new PlantAdapter(plantList);
        plantRecyclerView.setAdapter(plantAdapter);

        gridLayoutManager=new GridLayoutManager(getApplicationContext(),4);
        plantRecyclerView.setLayoutManager(gridLayoutManager);

        uid=user.getUid();
        if(user!=null){
            rootRef.child("User").child(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserInfo userInfo=snapshot.getValue(UserInfo.class);
                    checklist=userInfo.getPlant();
                    Log.d(TAG,checklist);
                    //[코드리뷰]to.꼬세지! from mini_0u0
                    //이렇게 짜보는것도 괜찮을지도?[110-118 line확인]
                    //values디렉터리->array파일 확인
                    //Drawable ArrayList 참고함.

                    TypedArray typedArray=getResources().obtainTypedArray(R.array.plant_list);
                    String[] names=getResources().getStringArray(R.array.plant_name);
                    int size= checklist.length();

                    for(int i=0;i<size;i++){
                        if(checklist.charAt(i)=='1'){
                            addPlantItem(typedArray.getDrawable(i),names[i]);
                        }
                        else{
                            addPlantItem(typedArray.getDrawable('3'),names[3]);
                        }
                    }
                    /*
                    if (checklist.charAt(0) == '1') {
                        Log.d(TAG,"들어옴0");
                        addPlantItem(getDrawable(R.drawable.plant_cactus), "선인장");
                    }
                    if (checklist.charAt(1) == '1') {
                        Log.d(TAG,"들어옴1");
                        addPlantItem(getDrawable(R.drawable.plant_azalea), "진달래");
                    }
                    if (checklist.charAt(2) == '1') {
                        Log.d(TAG,"들어옴2");
                        addPlantItem(getDrawable(R.drawable.plant_sunflower), "해바라기");
                    }
                    if (checklist.charAt(3) == '1') {
                        addPlantItem(getDrawable(R.drawable.login_planet_earth), "백일홍");
                    }
                    if (checklist.charAt(4) == '1') {
                        addPlantItem(getDrawable(R.drawable.login_planet_earth), "수련");
                    }
                    if (checklist.charAt(5) == '1') {
                        addPlantItem(getDrawable(R.drawable.login_planet_earth), "수국");
                    }
                    if (checklist.charAt(6) == '1') {
                        addPlantItem(getDrawable(R.drawable.login_planet_earth), "당귀꽃");
                    }
                    if (checklist.charAt(7) == '1') {
                        addPlantItem(getDrawable(R.drawable.login_planet_earth), "백련");
                    }
                    if (checklist.charAt(8) == '1') {
                        addPlantItem(getDrawable(R.drawable.login_planet_earth), "도라지꽃");
                    }
                    if (checklist.charAt(9) == '1') {
                        addPlantItem(getDrawable(R.drawable.login_planet_earth), "금낭화");
                    }
                    if (checklist.charAt(10) == '1') {
                        addPlantItem(getDrawable(R.drawable.login_planet_earth),"참나리");
                    }
                    if (checklist.charAt(11) == '1') {
                        addPlantItem(getDrawable(R.drawable.login_planet_earth),"코스모스");
                    }
                    if (checklist.charAt(12) == '1') {
                        addPlantItem(getDrawable(R.drawable.login_planet_earth),"국화");
                    }
                    if (checklist.charAt(13) == '1') {
                        addPlantItem(getDrawable(R.drawable.login_planet_earth),"무궁화");
                    }
                    if (checklist.charAt(14) == '1') {
                        addPlantItem(getDrawable(R.drawable.login_planet_earth),"철쭉");
                    }

                     */
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        //Log.d(TAG,checklist);
        checklist="111011101001101";
        plantCheck(checklist);

        plantAdapter.notifyDataSetChanged();

    }

    public void addPlantItem(Drawable image, String name){
        PlantItem item=new PlantItem();
        item.setPlantImage(image);
        item.setPlantName(name);

        plantList.add(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==101&&resultCode==RESULT_OK){
            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inSampleSize=8;
            Bitmap bitmap=BitmapFactory.decodeFile(file.getAbsolutePath(),options);
            userprofile.setImageBitmap(bitmap);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AutoPermissions.Companion.parsePermissions(this,requestCode,permissions,this);

    }
    @Override
    public void onDenied(int i, @NotNull String[] strings) {
        Toast.makeText(this,"permission denied : "+i,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onGranted(int i, @NotNull String[] strings) {
        Toast.makeText(this,"permission granted : "+i,Toast.LENGTH_SHORT).show();

    }

    private File createFile(){
        String filename="capture.jpg";
        File startDir= Environment.getExternalStorageDirectory();
        File outFile=new File(startDir,filename);

        return outFile;
    }

    public void plantCheck(String checkstr) {
        if (checkstr.charAt(0) == '1') {
            addPlantItem(getDrawable(R.drawable.plant_cactus), "선인장");
        }
        if (checkstr.charAt(1) == '1') {
            addPlantItem(getDrawable(R.drawable.plant_azalea), "진달래");
        }
        if (checkstr.charAt(2) == '1') {
            addPlantItem(getDrawable(R.drawable.plant_sunflower), "해바라기");
        }
        if (checkstr.charAt(3) == '1') {
            addPlantItem(getDrawable(R.drawable.login_planet_earth), "백일홍");
        }
        if (checkstr.charAt(4) == '1') {
            addPlantItem(getDrawable(R.drawable.login_planet_earth), "수련");
        }
        if (checkstr.charAt(5) == '1') {
            addPlantItem(getDrawable(R.drawable.login_planet_earth), "수국");
        }
        if (checkstr.charAt(6) == '1') {
            addPlantItem(getDrawable(R.drawable.login_planet_earth), "당귀꽃");
        }
        if (checkstr.charAt(7) == '1') {
            addPlantItem(getDrawable(R.drawable.login_planet_earth), "백련");
        }
        if (checkstr.charAt(8) == '1') {
            addPlantItem(getDrawable(R.drawable.login_planet_earth), "도라지꽃");
        }
        if (checkstr.charAt(9) == '1') {
            addPlantItem(getDrawable(R.drawable.login_planet_earth), "금낭화");
        }
        if (checkstr.charAt(10) == '1') {
            addPlantItem(getDrawable(R.drawable.login_planet_earth),"참나리");
        }
        if (checkstr.charAt(11) == '1') {
            addPlantItem(getDrawable(R.drawable.login_planet_earth),"코스모스");
        }
        if (checkstr.charAt(12) == '1') {
            addPlantItem(getDrawable(R.drawable.login_planet_earth),"국화");
        }
        if (checkstr.charAt(13) == '1') {
            addPlantItem(getDrawable(R.drawable.login_planet_earth),"무궁화");
        }
        if (checkstr.charAt(14) == '1') {
            addPlantItem(getDrawable(R.drawable.login_planet_earth),"철쭉");
        }
    }

}
