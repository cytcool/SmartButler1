package com.atguigu.smartbutler1.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.atguigu.smartbutler1.R;
import com.atguigu.smartbutler1.adapter.GrilAdapter;
import com.atguigu.smartbutler1.entity.GrilData;
import com.atguigu.smartbutler1.utils.PicassoUtils;
import com.atguigu.smartbutler1.utils.StaticClass;
import com.atguigu.smartbutler1.view.CustomDialog;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by 陈雨田 on 2017/8/29.
 */

public class GirlsFragment extends Fragment{

    private GridView mGridView;
    private List<GrilData> mList = new ArrayList<>();
    private GrilData data;

    private GrilAdapter mAdapter;

    //提示框
    private CustomDialog dialog;
    //预览图片
    private ImageView iv_img;
    //图片地址
    private List<String> mListUrl = new ArrayList<>();
    private PhotoViewAttacher mAttacher;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.girls_fragmeng,null);
        findView(view);
        return view;
    }

    private void findView(View view) {
        mGridView = (GridView) view.findViewById(R.id.mGridView);

        //初始化dialog
        dialog = new CustomDialog(getActivity(), LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,R.layout.dialog_gril,R.style.pop_anim_style, Gravity.CENTER);
        iv_img = (ImageView) dialog.findViewById(R.id.iv_img);


        //数据解析
        RxVolley.get(StaticClass.GIRL_URL, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                //解析json
                parsonJson(t);
            }
        });

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //图片加载
                PicassoUtils.loadImageViewCrop(getContext(),mListUrl.get(i),iv_img);
                //缩放
                mAttacher = new PhotoViewAttacher(iv_img);
                //刷新
                mAttacher.update();
                dialog.show();
            }
        });
    }

    private void parsonJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length() ; i++) {
                JSONObject json = (JSONObject) jsonArray.get(i);
                String url = json.getString("url");
                mListUrl.add(url);
                data.setImgUrl(url);
                mList.add(data);
            }
            //设置适配器
            mAdapter = new GrilAdapter(getActivity(),mList);
            mGridView.setAdapter(mAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
