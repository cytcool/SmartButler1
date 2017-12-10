package com.atguigu.smartbutler1.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.atguigu.smartbutler1.R;
import com.atguigu.smartbutler1.adapter.GrilAdapter;
import com.atguigu.smartbutler1.entity.GrilData;
import com.atguigu.smartbutler1.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈雨田 on 2017/8/29.
 */

public class GirlsFragment extends Fragment{

    private GridView mGridView;
    private List<GrilData> mList = new ArrayList<>();
    private GrilData data;

    private GrilAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.girls_fragmeng,null);
        findView(view);
        return view;
    }

    private void findView(View view) {
        mGridView = (GridView) view.findViewById(R.id.mGridView);

        //数据解析
        RxVolley.get(StaticClass.GIRL_URL, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                //解析json
                parsonJson(t);
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
