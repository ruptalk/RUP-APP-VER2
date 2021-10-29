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
import androidx.viewpager.widget.ViewPager;

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

import de.hdodenhof.circleimageview.CircleImageView;

public class UserPageActivity extends AppCompatActivity implements AutoPermissionsListener {

    ArrayList<PlantItem> plantList;
    String uid, checklist="000000000000000";
    private String TAG="UserPageActivity";
    ViewPager viewPager;
    PlantAdapter adapter;
    int size=0;

    private ImageView userprofile;
    private File file;
    private DatabaseReference rootRef;
    private FirebaseUser user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userpage);

        rootRef= FirebaseDatabase.getInstance().getReference();
        user= FirebaseAuth.getInstance().getCurrentUser();
        uid=user.getUid();
        plantList=new ArrayList<PlantItem>();

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_userpage);

        adapter=new PlantAdapter(plantList, UserPageActivity.this);
        viewPager=findViewById(R.id.plant_viewPager);

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

                    @Override
                    public void AlbumClick() {

                    }

                });
                dialog.show();
            }
        });

        //plant cardview
        if(user!=null){
            rootRef.child("User").child(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserInfo userInfo=snapshot.getValue(UserInfo.class);
                    checklist=userInfo.getPlant(); //사용자의 식물 종류 list

                    TypedArray typedArray=getResources().obtainTypedArray(R.array.plant_list);
                    String[] names=getResources().getStringArray(R.array.plant_name);
                    String[] languages=getResources().getStringArray(R.array.plant_language);
                    size= checklist.length();

                    for(int i=0;i<size;i++) {
                        if (checklist.charAt(i) == '1') { //사용자가 식물을 보유하고 있으면 1, 없으면 0
                            Log.d(TAG,"added "+i+" "+checklist.charAt(i));
                            plantList.add(new PlantItem(typedArray.getDrawable(0),names[i],languages[i]));
                        }
                    }

                    viewPager.setAdapter(adapter);
                    viewPager.setClipToPadding(false);
                    viewPager.setPadding(130,100,130,50);
                    viewPager.setPageMargin(50);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
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

}
