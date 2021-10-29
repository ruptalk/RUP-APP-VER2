package org.techtown.huhaclife;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private ImageView QR_code,userprofile;
    private TextView username,userpoint;
    private File file;
    private FirebaseUser firebaseUsser= FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    public DatabaseReference databaseReference;
    public String uid;
    private UserInfo userInfo;
    private ActivityResultLauncher<Intent> resultLauncher,resultLauncher2;

    private Button test;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("종료");
        builder.setMessage("take-out컵 버릴 때 또 만나요!");
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                moveTaskToBack(true) ;//테스크를 백그라운드로 이동
                finishAndRemoveTask();//액티비티 종료+테스크 리스트에서 지우기
                System.exit(0);

            }
        });
        builder.show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QR_code = (ImageView) findViewById(R.id.qr);
        userprofile = (ImageView) findViewById(R.id.iv_userprofile);
        username = (TextView) findViewById(R.id.tv_username);
        userpoint = (TextView) findViewById(R.id.tv_userpoint);
        databaseReference = firebaseDatabase.getReference();

        uid = firebaseUsser.getUid().trim();

        test=(Button)findViewById(R.id.btn_test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SeedDialog dialog=new SeedDialog(MainActivity.this, new SeedDialog.SeedDialogListener() {
                    @Override
                    public void ContentTranslate(int seednumber) {
                        String t=String.valueOf(seednumber);
                        Toast.makeText(MainActivity.this,seednumber+"들고옴",Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setContentView(R.layout.dialog_seed);
                dialog.show();
            }
        });

        //뒤로가기 버튼 두번 누르면 종료


        //사용자 프로필 둥글게 처리
        userprofile.setBackground(new ShapeDrawable(new OvalShape()));
        userprofile.setClipToOutline(true);

        //사용자 프로필
        databaseReference.child("User").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userInfo = snapshot.getValue(UserInfo.class);
                username.setText(userInfo.getName());
                userpoint.setText(String.valueOf(userInfo.getPoint()));

                qrcreate(userInfo.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        userprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,UserPageActivity.class);
                startActivity(intent);
                //위험권한팝업창 설정
//                ProfileDialog dialog = new ProfileDialog(MainActivity2.this, new ProfileDialog.ProfileDialogListener() {
//                    @Override
//                    public void CameraClick() {
//                        int cameraPermission = ActivityCompat.checkSelfPermission(MainActivity2.this, Manifest.permission.CAMERA);
//                        if (cameraPermission == PackageManager.PERMISSION_GRANTED) {
//                            play_camera();
//                        } else {
//                            requestPermission();
//                        }
//
//                    }
//
//                    @Override
//                    public void AlbumClick() {
//                        int albumPermission = ActivityCompat.checkSelfPermission(MainActivity2.this, Manifest.permission.READ_EXTERNAL_STORAGE);
//                        if (albumPermission == PackageManager.PERMISSION_GRANTED) {
//                            play_album();
//                        } else {
//                            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
//                            ActivityCompat.requestPermissions(MainActivity2.this, permissions, 102);
//                        }
//
//                    }
//                });
//                dialog.setContentView(R.layout.profile_dialog);
//                dialog.show();
            }
        });
        //카메라 접근을 하기 위한 콜백함수 처리
        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            Intent intent = result.getData();
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
        ActivityCompat.requestPermissions(MainActivity.this,permissions,101);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //카메라
        if(requestCode==101){
           if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
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
        Uri fileuri= FileProvider.getUriForFile(MainActivity.this,"org.techtown.huhaclife.fileprovider",file);
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



    //qr코드 생성
    private void qrcreate(String useremail){
        MultiFormatWriter multiFormatWriter=new MultiFormatWriter();
        try {
            int qrColor=0xFF6E917A;
            int qrbackroundColor=0xFFF3D6AB;

            BitMatrix bitMatrix=multiFormatWriter.encode(useremail, BarcodeFormat.QR_CODE,200,200);
            Bitmap bitmap=Bitmap.createBitmap(bitMatrix.getWidth(),bitMatrix.getHeight(),Bitmap.Config.ARGB_8888);
            for(int x=0; x<200;x++){
                for(int y=0;y<200;y++){
                    bitmap.setPixel(x,y,bitMatrix.get(x,y)? qrColor:qrbackroundColor);
                }
            }
            QR_code.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}