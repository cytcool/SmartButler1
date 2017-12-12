package com.atguigu.smartbutler1.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Switch;

import com.atguigu.smartbutler1.R;

import static com.atguigu.smartbutler1.ui.ShareUtils.getBoolean;

/**
 * Created by 陈雨田 on 2017/8/29.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    //语音播报
    private Switch sw_speak;

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
        }
    }
}
