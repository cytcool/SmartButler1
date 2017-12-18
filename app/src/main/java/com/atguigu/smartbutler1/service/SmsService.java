package com.atguigu.smartbutler1.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.SmsMessage;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.atguigu.smartbutler1.R;
import com.atguigu.smartbutler1.ui.L;
import com.atguigu.smartbutler1.utils.StaticClass;

/**
 * Created by CYT on 2017/12/14.
 */

public class SmsService extends Service implements View.OnClickListener {

    private SmsRecevier smsRecevier;
    //发件人号码
    private String smsPhone;
    //短信内容
    private String smsContent;
    //窗口管理
    private WindowManager wm;
    //布局参数
    private WindowManager.LayoutParams layoutParams;
    //添加布局
    private View mView;

    private TextView tv_phone;
    private TextView tv_content;
    private Button btn_send_sms;
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_send_sms:
                sendSms();
                //消失窗口
                if (mView.getParent() != null){
                    wm.removeView(mView);
                }
                break;
        }
    }

    private void sendSms() {
        Uri uri = Uri.parse("smsto:" + smsPhone);
        Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
        //设置启动模式
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("sms_body","");
        startActivity(intent);
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

                        //窗口提示
                        showWindow();
                    }
                }
        }
    }

    //窗口提示
    private void showWindow() {
        //获取系统服务
        wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        //获取布局参数
        layoutParams = new WindowManager.LayoutParams();
        //定义宽高
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        //定义标记
        layoutParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        //定义格式
        layoutParams.format = PixelFormat.TRANSLUCENT;
        //定义类型
        layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        //加载布局
        mView = View.inflate(getApplicationContext(), R.layout.sms_item,null);
        tv_phone = (TextView) mView.findViewById(R.id.tv_phone);
        tv_content = (TextView) mView.findViewById(R.id.tv_content);
        btn_send_sms = (Button) mView.findViewById(R.id.btn_send_sms);
        btn_send_sms.setOnClickListener(this);


        //添加View到窗口
        wm.addView(mView,layoutParams);
    }
}
