package com.atguigu.smartbutler1.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsMessage;

import com.atguigu.smartbutler1.ui.L;
import com.atguigu.smartbutler1.utils.StaticClass;

/**
 * Created by CYT on 2017/12/14.
 */

public class SmsService extends Service {

    private SmsRecevier smsRecevier;
    //发件人号码
    private String smsPhone;
    //短信内容
    private String smsContent;

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
        //动态注册
        smsRecevier = new SmsRecevier();
        IntentFilter intent = new IntentFilter();
        //添加Action
        intent.addAction(StaticClass.SMS_ACTION);
        //设置权限
        intent.setPriority(Integer.MAX_VALUE);
        //注册
        registerReceiver(smsRecevier,intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销
        unregisterReceiver(smsRecevier);
    }

    //短信广播
    public class SmsRecevier extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (StaticClass.SMS_ACTION.equals(action)){
                    L.i("来短信了");
                    Object[] objs = (Object[]) intent.getExtras().get("pdus");
                    //遍历数组得到相关数据
                    for (Object obj: objs) {
                        SmsMessage sms = SmsMessage.createFromPdu((byte[]) obj);
                        //发件人
                        smsPhone = sms.getOriginatingAddress();
                        //内容
                        smsContent = sms.getMessageBody();
                    }
                }
        }
    }
}
