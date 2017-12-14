package com.atguigu.smartbutler1.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by CYT on 2017/12/14.
 */

public class SmsService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        
        init();
    }

    private void init() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
