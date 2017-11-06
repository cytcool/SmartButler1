package com.atguigu.smartbutler1.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by 陈雨田 on 2017/8/29.
 */

public class UtilsTools {

    public static void setFont(Context mContext, TextView textView){
        Typeface type =Typeface.createFromAsset(mContext.getAssets(),"fonts/FONT.TTF");
        textView.setTypeface(type);
    }
}
