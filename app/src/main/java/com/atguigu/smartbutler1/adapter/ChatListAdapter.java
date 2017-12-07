package com.atguigu.smartbutler1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.atguigu.smartbutler1.R;
import com.atguigu.smartbutler1.entity.ChatListData;

import java.util.List;

/**
 * Created by CYT on 2017/12/5.
 */

public class ChatListAdapter extends BaseAdapter {

    //左边的type
    public static final int VALUE_LEFT_TEXT = 1;
    //右边的type
    public static final int VALUE_RIGHT_TEXT = 2;

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ChatListData data;
    private List<ChatListData> mList;


    public ChatListAdapter(Context mContext,List<ChatListData> mList){
        this.mContext = mContext;
        this.mList = mList;
        //获取系统服务
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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

        ViewHolderLeftText viewHolderLeftText = null;
        ViewHolderRightText viewHolderRightText = null;
        int type = getItemViewType(i);
        if (view == null){
            switch (type){
                case VALUE_LEFT_TEXT:
                    viewHolderLeftText = new ViewHolderLeftText();
                    view = mLayoutInflater.inflate(R.layout.left_item,null);
                    viewHolderLeftText.tv_left_text = (TextView) view.findViewById(R.id.tv_left_text);
                    view.setTag(viewHolderLeftText);
                    break;
                case VALUE_RIGHT_TEXT:
                    viewHolderRightText = new ViewHolderRightText();
                    view = mLayoutInflater.inflate(R.layout.right_item,null);
                    viewHolderRightText.tv_right_text = (TextView) view.findViewById(R.id.tv_right_text);
                    view.setTag(viewHolderLeftText);
                    break;
            }
        }else {
            switch (type){
                case VALUE_LEFT_TEXT:
                    viewHolderLeftText = (ViewHolderLeftText) view.getTag();
                    break;
                case VALUE_RIGHT_TEXT:
                    viewHolderRightText = (ViewHolderRightText) view.getTag();
                    break;
            }
        }

        //赋值
        ChatListData data = mList.get(i);
        switch (type){
            case VALUE_LEFT_TEXT:
                viewHolderLeftText.tv_left_text.setText(data.getText());
                break;
            case VALUE_RIGHT_TEXT:
                viewHolderRightText.tv_right_text.setText(data.getText());
        }

        return view;
    }

    //根据数据源的position来返回显示的item
    @Override
    public int getItemViewType(int position) {
        ChatListData data = mList.get(position);
        int type = data.getType();
        return type;
    }

    //返回所有的Layout数据
    @Override
    public int getViewTypeCount() {
        return mList.size()+1;
    }

    //左边的ViewHolder
    class ViewHolderLeftText{
        private TextView tv_left_text;
    }

    //右边的ViewHolder
    class ViewHolderRightText{
        private TextView tv_right_text;
    }
}
