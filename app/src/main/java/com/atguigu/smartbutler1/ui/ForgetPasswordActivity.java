package com.atguigu.smartbutler1.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.atguigu.smartbutler1.R;
import com.atguigu.smartbutler1.entity.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 陈雨田 on 2017/9/6.
 */

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_forget_password;
    private EditText et_email;

    private EditText et_now;
    private EditText et_new;
    private EditText et_new_password;
    private Button btn_update_password;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        initView();
    }

    private void initView() {
        btn_forget_password = (Button) findViewById(R.id.btn_forget_password);
        btn_forget_password.setOnClickListener(this);
        et_email = (EditText) findViewById(R.id.et_email);

        et_now = (EditText) findViewById(R.id.et_now);
        et_new = (EditText) findViewById(R.id.et_new);
        et_new_password = (EditText) findViewById(R.id.et_new_password);
        btn_update_password = (Button) findViewById(R.id.btn_update_password);
        btn_update_password.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_forget_password:
                final String email = et_email.getText().toString().trim();
                if(!TextUtils.isEmpty(email)){
                    MyUser user = new MyUser();
                    user.resetPasswordByEmail(email, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){
                                Toast.makeText(ForgetPasswordActivity.this, "邮件已经发送至"+ email +"请查收", Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(ForgetPasswordActivity.this, "重置密码失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_update_password:
                //获取输入框的值
                String now = et_now.getText().toString().trim();
                String news = et_new.getText().toString().trim();
                String new_password = et_new_password.getText().toString().trim();

                if(!TextUtils.isEmpty(now)&!TextUtils.isEmpty(news)&!TextUtils.isEmpty(new_password)){
                    MyUser.updateCurrentUserPassword(now, news, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){
                                Toast.makeText(ForgetPasswordActivity.this, "密码修改完成", Toast.LENGTH_SHORT).show();
                                finish();

                            }else {
                                Toast.makeText(ForgetPasswordActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
