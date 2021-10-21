package org.techtown.huhaclife;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import org.jetbrains.annotations.NotNull;

import java.io.File;

public class UserPageActivity extends AppCompatActivity implements AutoPermissionsListener {
    private RecyclerView rv_missionlist;
    private MissionRecyclerAdapter adapter;
    private ImageView userprofile;
    private File file;




    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.activity_userpage);


        rv_missionlist=(RecyclerView)findViewById(R.id.rv_missionlist);
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
