package com.atguigu.smartbutler1.adapter;

import android.content.Context;
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


public class GrilAdapter extends BaseAdapter {

    private Context mContext;
    private List<GrilData> mList;
    private LayoutInflater inflater;
    private GrilData data;
    private WindowManager wm;
    //屏幕宽
    private int width;

    public GrilAdapter(Context mContext, List<GrilData> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.gril_item,null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageview);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        data = mList.get(position);
        //解析图片
        String url = data.getImgUrl();

        PicassoUtils.loadImageViewSize(mContext,url,width/2,500,viewHolder.imageView);

        return convertView;
    }

    class ViewHolder{
        private ImageView imageView;
    }
}

