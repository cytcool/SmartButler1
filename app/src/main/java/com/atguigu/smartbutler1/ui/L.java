package com.atguigu.smartbutler1.ui;

import android.util.Log;

/**
 * Created by 陈雨田 on 2017/8/30.
 */

public class L {

    public static final boolean DEBUG = true;

    public static final String TAG = "SmartButler";

    public static void d(String text){
        if(DEBUG){
            Log.d(TAG,text);
        }
    }

    public static void i(String text){
        if(DEBUG){
            Log.i(TAG,text);
        }
    }

    public static void e(String text){
        if(DEBUG){
            Log.e(TAG,text);
        }
    }

    public static void w(String text){
        if(DEBUG){
            Log.w(TAG,text);
        }
    }
}
