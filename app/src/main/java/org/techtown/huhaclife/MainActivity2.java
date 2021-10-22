package org.techtown.huhaclife;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.File;

public class MainActivity2 extends AppCompatActivity {
    private ProgressBar progressBar;
    private ImageView QR_code,userprofile;
    private File file;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        QR_code=(ImageView)findViewById(R.id.qr);
        userprofile=(ImageView)findViewById(R.id.iv_userprofile);

        //위험권한팝업창 설정
        userprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileDialog dialog=new ProfileDialog(MainActivity2.this, new ProfileDialog.ProfileDialogListener() {
                    @Override
                    public void CameraClick() {
                        int cameraPermission= ActivityCompat.checkSelfPermission(MainActivity2.this, Manifest.permission.CAMERA);
                        if(cameraPermission== PackageManager.PERMISSION_GRANTED){
                            play_camera();
                        }
                        else{
                            requestPermission();
                        }

                    }
                });
                dialog.setContentView(R.layout.profile_dialog);
                dialog.show();
            }
        });

        //QR코드 생성
        MultiFormatWriter multiFormatWriter=new MultiFormatWriter();
        try {
            int qrColor=0xFF6E917A;
            int qrbackroundColor=0xFFF3D6AB;

            BitMatrix bitMatrix=multiFormatWriter.encode("RUP", BarcodeFormat.QR_CODE,200,200);
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

    private void requestPermission(){
        String []permissions={Manifest.permission.CAMERA};
        ActivityCompat.requestPermissions(MainActivity2.this,permissions,101);
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
    }
    private  void play_camera(){
        if(file==null){
            file=createFile();
        }
        Uri fileuri= FileProvider.getUriForFile(MainActivity2.this,"org.techtown.huhaclife.fileprovider",file);
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,fileuri);
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(intent,201);
        }
    }
    private File createFile(){
        String filename="userprofile.jpg";
        File startDir= Environment.getExternalStorageDirectory();
        File outFile =new File(startDir,filename);
        return  outFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==201&&requestCode==RESULT_OK){
            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inSampleSize=8;
            Bitmap bitmap=BitmapFactory.decodeFile(file.getAbsolutePath(),options);
            userprofile.setImageBitmap(bitmap);
        }
    }
}