package com.atguigu.smartbutler1.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.atguigu.smartbutler1.R;
import com.atguigu.smartbutler1.entity.GrilData;
import com.atguigu.smartbutler1.utils.PicassoUtils;

import java.util.List;

/**
 * Created by CYT on 2017/12/10.
 */

public class GrilAdapter extends BaseAdapter {

    private Context mContext;
    private List<GrilData> mList;
    private LayoutInflater mLayoutInflater;
    private GrilData data;

    private int width,height;
    private WindowManager wm;

    public GrilAdapter(Context mContext,List<GrilData> mList){
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
        if (view == null){
            viewHolder = new ViewHolder();
            view = mLayoutInflater.inflate(R.layout.gril_item,null);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.imageview);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        //加载数据
        data = mList.get(i);
        //加载图片
        if(!TextUtils.isEmpty(data.getImgUrl())){
            //加载图片
            PicassoUtils.loadImageViewSize(mContext, data.getImgUrl(), width/2, 500, viewHolder.imageView);
        }
        return view;
    }
    class ViewHolder{
        private ImageView imageView;
    }

}
