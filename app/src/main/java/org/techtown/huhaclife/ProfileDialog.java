package org.techtown.huhaclife;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class ProfileDialog extends Dialog {

    private TextView camera,album;

    public interface ProfileDialogListener{
        void CameraClick();
    }

    private ProfileDialogListener profileDialogListener;

    public ProfileDialog(@NonNull Context context, ProfileDialogListener profileDialogListener) {
        super(context);
        this.setContentView(R.layout.profile_dialog);
        this.profileDialogListener=profileDialogListener;
    }

    public ProfileDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected ProfileDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        camera=(TextView)findViewById(R.id.tv_camera);
        album=(TextView)findViewById(R.id.tv_album);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //인터페이스의 함수를 호출하여 변수에 저장된 값들을 ACtivity로 전달
                profileDialogListener.CameraClick();
                dismiss();
                

            }
        });
    }




}
