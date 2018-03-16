package com.atguigu.smartbutler1.ui;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.widget.ImageView;
import com.atguigu.smartbutler1.R;
import com.xys.libzxing.zxing.encoding.EncodingUtils;


/**
 * Created by CYT on 2018/3/16.
 */

public class QrCodeActivity extends BaseActivity {

    private ImageView iv_qr_code;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        
        initView();
    }

    private void initView() {
        iv_qr_code = (ImageView) findViewById(R.id.iv_qr_code);
        //屏幕的宽
        int width = getResources().getDisplayMetrics().widthPixels;

        Bitmap qrCodeBitmap = EncodingUtils.createQRCode("我是智能管家", width / 2, width / 2,
              BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        iv_qr_code.setImageBitmap(qrCodeBitmap);
    }
}
