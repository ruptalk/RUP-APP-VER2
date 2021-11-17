package org.techtown.huhaclife;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class UserPageActivity extends AppCompatActivity  {

    ArrayList<PlantItem> plantList;
    String uid, checklist="000000000000000";
    private String TAG="UserPageActivity";
    ViewPager viewPager;
    PlantAdapter adapter;
    private int rCount, point, size=15;

    private TextView recycleCountTextView, userPointTextView;
    private ImageView userprofile;
    private File file;
    private DatabaseReference rootRef;
    private FirebaseUser user;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private ActivityResultLauncher<Intent> resultLauncher,resultLauncher2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userpage);

        recycleCountTextView=(TextView)findViewById(R.id.recycle_count);
        userPointTextView=(TextView)findViewById(R.id.point_userpage);

        rootRef= FirebaseDatabase.getInstance().getReference();
        user= FirebaseAuth.getInstance().getCurrentUser();
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        uid=user.getUid();
        plantList=new ArrayList<PlantItem>();

        adapter=new PlantAdapter(plantList, UserPageActivity.this);
        viewPager=findViewById(R.id.plant_viewPager);

        //user profile
        userprofile=(ImageView)findViewById(R.id.iv_userprofile);
        userprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //위험권한팝업창 설정
                ProfileDialog dialog = new ProfileDialog(UserPageActivity.this, new ProfileDialog.ProfileDialogListener() {
                    @Override
                    public void CameraClick() {
                        int cameraPermission = ActivityCompat.checkSelfPermission(UserPageActivity.this, Manifest.permission.CAMERA);
                        if (cameraPermission == PackageManager.PERMISSION_GRANTED) {
                            play_camera();
                        } else {
                            requestPermission();
                        }

                    }

                    @Override
                    public void AlbumClick() {
                        int albumPermission = ActivityCompat.checkSelfPermission(UserPageActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
                        if (albumPermission == PackageManager.PERMISSION_GRANTED) {
                            play_album();
                        } else {
                            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                            ActivityCompat.requestPermissions(UserPageActivity.this, permissions, 102);
                        }

                    }
                });
                dialog.setContentView(R.layout.profile_dialog);
                dialog.show();

            }
        });

        //user profile and plant cardview
        if(user!=null){
            rootRef.child("User").child(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserInfo userInfo=snapshot.getValue(UserInfo.class);

                    //user profile
                    rCount=userInfo.getCount(); //재활용 횟수
                    point=userInfo.getPoint(); //포인트
                    recycleCountTextView.setText(""+rCount);
                    userPointTextView.setText(""+point);

                    //plant cardview
                    checklist=userInfo.getPlant(); //사용자의 식물 종류 list
                    TypedArray typedArray=getResources().obtainTypedArray(R.array.plant_list);
                    String[] names=getResources().getStringArray(R.array.plant_name);
                    String[] languages=getResources().getStringArray(R.array.plant_language);
                    size= checklist.length();

                    cardviewSetting(size);
//
//                    viewPager.setAdapter(adapter);
//                    viewPager.setClipToPadding(false);
//                    viewPager.setPadding(130,100,130,50);
//                    viewPager.setPageMargin(50);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        //사용자 프로필 둥글게 처리
        userprofile.setBackground(new ShapeDrawable(new OvalShape()));
        userprofile.setClipToOutline(true);

        //프로필 설정
        if(uid!=null){
            System.out.println("여기가 반복해서 실행되나?");
            storageReference.child("User/"+uid).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(UserPageActivity.this).load(uri).into(userprofile);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        }

        //카메라 접근을 하기 위한 콜백함수 처리
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent intent = result.getData();
                            Uri file=intent.getData();
                            UploadTask uploadTask=storageReference.child("User/"+uid).putFile(file);
                            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Toast.makeText(getApplicationContext(), "사진이 성공적으로 업로드되었습니다.", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "사진 업로드 실패", Toast.LENGTH_SHORT).show();
                                }
                            });
                            Bitmap bit = (Bitmap) intent.getExtras().get("data");
                            if (bit != null) {
                                userprofile.setImageBitmap(bit);
                            }
                        }
                    }
                });

        //앨범 접근을 하기 위한 콜백함수 처리
        resultLauncher2=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent intent = result.getData();
                            System.out.println(intent);
                            InputStream in = null;
                            try {
                                //파일경로 알아서 파이어베이스 스토리지에 업로드 하기
                                Uri file=intent.getData();
                                UploadTask uploadTask=storageReference.child("User/"+uid).putFile(file);
                                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Toast.makeText(getApplicationContext(), "사진이 성공적으로 업로드되었습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), "사진 업로드 실패", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                in = getContentResolver().openInputStream(intent.getData());
                                Bitmap img = BitmapFactory.decodeStream(in);
                                userprofile.setImageBitmap(img);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }



                        }
                    }
                });

    }

    private void requestPermission(){
        String []permissions={Manifest.permission.CAMERA};
        ActivityCompat.requestPermissions(UserPageActivity.this,permissions,101);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //카메라
        if(requestCode==101){
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                play_camera();
            }
            else{
                System.out.println(grantResults[0]);

            }
        }
        //앨범
        else if(requestCode==102){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                play_album();
            }
            else{
                System.out.println(grantResults[0]);

            }
        }
    }
    //카메라 실행
    private  void play_camera(){
        if(file==null){
            file=createFile();
        }
        Uri fileuri= FileProvider.getUriForFile(UserPageActivity.this,"org.techtown.huhaclife.fileprovider",file);
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        System.out.println(intent.getData());
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        if(intent.resolveActivity(getPackageManager())!=null){
            resultLauncher.launch(intent);
        }
    }

    //앨범 실행
    private void play_album(){
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra("request",202);
        resultLauncher2.launch(intent);
    }


    //파일생성함수
    private File createFile(){
        String filename="userprofile.jpg";
        File startDir= Environment.getExternalStorageDirectory();
        File outFile =new File(startDir,filename);
        return  outFile;
    }

    //plant cardview 세팅함수
    private void cardviewSetting(int size){

        TypedArray typedArray=getResources().obtainTypedArray(R.array.plant_list);
        String[] names=getResources().getStringArray(R.array.plant_name);
        String[] languages=getResources().getStringArray(R.array.plant_language);
        int bColor;

        for(int i=0;i<size;i++) {
            bColor=1;
            if (checklist.charAt(i) == '0') { //사용자가 식물을 보유하고 있으면 1, 없으면 0
                Log.d(TAG,"added "+i+" "+checklist.charAt(i));
                bColor=0;
            }
            plantList.add(new PlantItem(typedArray.getDrawable(0),names[i],languages[i],bColor));
        }

        viewPager.setAdapter(adapter);
        viewPager.setClipToPadding(false);
        viewPager.setPadding(130,100,130,50);
        viewPager.setPageMargin(50);
    }

}
