package com.atguigu.smartbutler1.entity;

/**
 * Created by CYT on 2017/12/7.
 */

public class WeChatData {

    //标题
    private String title;
    //出处
    private String source;
    //图片
    private String imgurl;
    //新闻地址
    private String newsurl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getNewsurl() {
        return newsurl;
    }

    public void setNewsurl(String newsurl) {
        this.newsurl = newsurl;
    }
}
