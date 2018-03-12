package com.atguigu.smartbutler1.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.smartbutler1.MainActivity;
import com.atguigu.smartbutler1.R;
import com.atguigu.smartbutler1.entity.MyUser;
import com.atguigu.smartbutler1.utils.ShareUtils;
import com.atguigu.smartbutler1.view.CustomDialog;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 陈雨田 on 2017/9/5.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_registered;
    private EditText et_name;
    private EditText et_password;
    private Button btn_login;
    private CheckBox keep_password;
    private TextView tv_forget;

    private CustomDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {

        btn_registered = (Button) findViewById(R.id.btn_registered);
        btn_registered.setOnClickListener(this);

        tv_forget = (TextView) findViewById(R.id.tv_forget);
        tv_forget.setOnClickListener(this);

        et_name = (EditText) findViewById(R.id.et_name);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        dialog = new CustomDialog(this,300,300,R.layout.dialog_loading,R.style.Theme_dialog, Gravity.CENTER,R.style.pop_anim_style);
        dialog.setCancelable(false);

        keep_password = (CheckBox) findViewById(R.id.keep_password);

        boolean isChecked = ShareUtils.getBoolean(this,"keeppass",true);
        keep_password.setChecked(isChecked);
        if(isChecked){
            et_name.setText(ShareUtils.getString(this,"name",""));
            et_password.setText(ShareUtils.getString(this,"password",""));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_registered:
                startActivity(new Intent(this, RegisteredActivity.class));
                break;
            case R.id.btn_login:
                //1.获取输入框的值
                String name = et_name.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                //2.判断是否为空
                if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(password)) {
                    //登录
                    dialog.show();
                    final MyUser user = new MyUser();
                    user.setUsername(name);
                    user.setPassword(password);
                    user.login(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            dialog.dismiss();
                            //判断结果
                            if (e == null) {
                                //判断邮箱是否验证
                                if (user.getEmailVerified()) {
                                    //跳转
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();

                                    //记住状态
                                    ShareUtils.putBoolean(LoginActivity.this,"keeppass",keep_password.isChecked());

                                    //是否记住密码
                                    if(keep_password.isChecked()){
                                        ShareUtils.putString(LoginActivity.this,"name",et_name.getText().toString().trim());
                                        ShareUtils.putString(LoginActivity.this,"password",et_password.getText().toString().trim());
                                    }else {
                                        ShareUtils.deleShare(LoginActivity.this,"name");
                                        ShareUtils.deleShare(LoginActivity.this,"password");
                                    }

                                } else {
                                    Toast.makeText(LoginActivity.this, "请前往邮箱验证", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "登录失败：" + e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.tv_forget:
                startActivity(new Intent(this,ForgetPasswordActivity.class));
                break;
        }
    }

}

