package com.atguigu.smartbutler1.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.smartbutler1.R;
import com.atguigu.smartbutler1.entity.WeChatData;
import com.atguigu.smartbutler1.utils.PicassoUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by CYT on 2017/12/7.
 */

public class WeChatAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<WeChatData> mList;
    private WeChatData mdata;

    private int width,height;
    private WindowManager wm;

    public WeChatAdapter(Context mContext,List<WeChatData> mList){
        this.mContext = mContext;
        this.mList = mList;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null ){
            viewHolder = new ViewHolder();
            view = mLayoutInflater.inflate(R.layout.wechat_item,null);
            viewHolder.iv_img = (ImageView) view.findViewById(R.id.iv_img);
            viewHolder.tv_title = (TextView) view.findViewById(R.id.tv_title);
            viewHolder.tv_source = (TextView) view.findViewById(R.id.tv_source);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        //赋值
        mdata = mList.get(i);
        viewHolder.tv_title.setText(mdata.getTitle());
        viewHolder.tv_source.setText(mdata.getSource());

        //加载图片
        if(!TextUtils.isEmpty(mdata.getImgurl())){
            //加载图片
            PicassoUtils.loadImageViewSize(mContext, mdata.getImgurl(), width/3, 250, viewHolder.iv_img);
        }
       // Picasso.with(mContext).load(mdata.getImgurl()).into(viewHolder.iv_img);

        return view;
    }

    class ViewHolder{
        private ImageView iv_img;
        private TextView tv_title;
        private TextView tv_source;
    }
}
