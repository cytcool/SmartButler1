package com.atguigu.smartbutler1.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.atguigu.smartbutler1.R;
import com.atguigu.smartbutler1.service.SmsService;
import com.atguigu.smartbutler1.utils.ShareUtils;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import java.util.LinkedList;

import static com.atguigu.smartbutler1.utils.ShareUtils.getBoolean;


/**
 * Created by 陈雨田 on 2017/8/29.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    //语音播报
    private Switch sw_speak;
    //短信提示
    private Switch sw_sms;
    //扫一扫
    private LinearLayout ll_scan;
    //扫一扫结果
    private TextView tv_scan_result;
    //生成二维码
    private LinearLayout ll_qr_code;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initView();
    }

    private void initView() {
        sw_speak = (Switch) findViewById(R.id.sw_speak);
        sw_speak.setOnClickListener(this);

        boolean isSpeak = getBoolean(this, "isSpeak", false);
        sw_speak.setChecked(isSpeak);

        sw_sms = (Switch) findViewById(R.id.sw_sms);
        sw_sms.setOnClickListener(this);

        boolean isSms = getBoolean(this,"isSms",false);
        sw_sms.setChecked(isSms);

        ll_scan = (LinearLayout) findViewById(R.id.ll_scan);
        ll_scan.setOnClickListener(this);
        tv_scan_result = (TextView) findViewById(R.id.tv_scan_result);

        ll_qr_code = (LinearLayout) findViewById(R.id.ll_qr_code);
        ll_qr_code.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sw_speak:
                //切换相反
                sw_speak.setSelected(!sw_speak.isSelected());
                //保存状态
                ShareUtils.putBoolean(this, "isSpeak", sw_speak.isChecked());
                break;
            case R.id.sw_sms:
                //切换相反
                sw_sms.setSelected(!sw_sms.isSelected());
                //保存状态
                ShareUtils.putBoolean(this,"isSms",sw_sms.isChecked());
                if (sw_sms.isChecked()){
                    startService(new Intent(this, SmsService.class));
                }else {
                    stopService(new Intent(this,SmsService.class));
                }
            case R.id.ll_scan:
                Intent openCameraIntent = new Intent(this, CaptureActivity.class);
                startActivityForResult(openCameraIntent, 0);
                break;
            case R.id.ll_qr_code:
                startActivity(new Intent(this,QrCodeActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            tv_scan_result.setText(scanResult);
        }
    }
}
