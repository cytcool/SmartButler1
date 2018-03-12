package com.atguigu.smartbutler1.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.LinearLayout;

/**
 * Created by CYT on 2017/12/18.
 */

public class DispatchLinearLayout extends LinearLayout{

    private DispatchKeyEventListener dispatchKeyEventListener;

    public DispatchLinearLayout(Context context) {
        super(context);
    }

    public DispatchLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DispatchLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DispatchKeyEventListener getDispatchKeyEventListener() {
        return dispatchKeyEventListener;
    }

    public void setDispatchKeyEventListener(DispatchKeyEventListener dispatchKeyEventListener) {
        this.dispatchKeyEventListener = dispatchKeyEventListener;
    }


    //接口
    public static interface DispatchKeyEventListener{
        boolean dispatchKeyEvent(KeyEvent event);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (dispatchKeyEventListener != null){
            return dispatchKeyEventListener.dispatchKeyEvent(event);
        }
        return super.dispatchKeyEvent(event);
    }
}
