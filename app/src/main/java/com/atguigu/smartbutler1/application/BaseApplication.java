package com.atguigu.smartbutler1.application;

import android.app.Application;

import com.atguigu.smartbutler1.utils.StaticClass;

import cn.bmob.v3.Bmob;

/**
 * Created by 陈雨田 on 2017/8/29.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化Bmob
        Bmob.initialize(this, StaticClass.BMOB_APP_ID);
    }
}
