package com.atguigu.smartbutler1.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.atguigu.smartbutler1.MainActivity;
import com.atguigu.smartbutler1.R;
import com.atguigu.smartbutler1.utils.ShareUtils;
import com.atguigu.smartbutler1.utils.StaticClass;
import com.atguigu.smartbutler1.utils.UtilsTools;

/**
 * Created by 陈雨田 on 2017/8/30.
 */

public class SplashActivity extends AppCompatActivity {

    private TextView tv_splash;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case StaticClass.HANDLER_SPLASH:
                    //判断是否是第一次运行
                    if(isFirst()){
                        startActivity(new Intent(SplashActivity.this,GuideActivity.class));
                    }else {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    }
                    finish();
                    break;
            }
        }
    };



    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();
    }

    private void initView() {

        //延时2000ms
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH,2000);

        tv_splash = (TextView) findViewById(R.id.tv_splash);

        UtilsTools.setFont(this,tv_splash);


    }

    private boolean isFirst() {
        boolean isFirst = ShareUtils.getBoolean(SplashActivity.this,StaticClass.SHARE_IS_FIRST,true);
        if(isFirst){

            ShareUtils.putBoolean(this,StaticClass.SHARE_IS_FIRST,false);
            return true;
        }else {
            return false;
        }


    }

    //禁止返回键
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
