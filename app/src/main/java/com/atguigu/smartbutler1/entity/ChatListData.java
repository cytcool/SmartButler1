package com.atguigu.smartbutler1.entity;

/**
 * Created by CYT on 2017/12/5.
 */

public class ChatListData {

    //文本
    private String text;
    //区分左边右边
    private int type;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
