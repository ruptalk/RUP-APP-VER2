package org.techtown.huhaclife;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class KakaoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        KakaoSdk.init(this,"6d2aa639e8ea6e75a8dd34f45ad60cf0");
    }
}
