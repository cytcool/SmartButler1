package com.atguigu.smartbutler1.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.atguigu.smartbutler1.R;
import com.atguigu.smartbutler1.adapter.WeChatAdapter;
import com.atguigu.smartbutler1.entity.CourierData;
import com.atguigu.smartbutler1.entity.WeChatData;
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

public class WechatFragment extends Fragment {

    private ListView mListView;
    private List<WeChatData> mList = new ArrayList<>();
    //标题
    private List<String> mListTitle = new ArrayList<>();
    //地址
    private List<String> mListUrl = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.wechat_fragmeng, null);
        findView(view);
        return view;
    }

    private void findView(View view) {
        mListView = (ListView) view.findViewById(R.id.mListView);

        //解析接口
        String url = "http://v.juhe.cn/weixin/query?key=" + StaticClass.WECHAT_KEY + "&ps=100";
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                parsingJson(t);
            }
        });
    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            JSONArray jsonArray = jsonResult.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = (JSONObject) jsonArray.get(i);
                WeChatData data = new WeChatData();
                data.setTitle(json.getString("title"));
                data.setSource(json.getString("source"));
                data.setImgurl(json.getString("firstImg"));
                mList.add(data);
            }
            WeChatAdapter adapter = new WeChatAdapter(getActivity(), mList);
            mListView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
