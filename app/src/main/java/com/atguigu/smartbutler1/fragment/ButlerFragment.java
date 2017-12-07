package com.atguigu.smartbutler1.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.atguigu.smartbutler1.R;
import com.atguigu.smartbutler1.adapter.ChatListAdapter;
import com.atguigu.smartbutler1.entity.ChatListData;
import com.atguigu.smartbutler1.ui.L;
import com.atguigu.smartbutler1.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by 陈雨田 on 2017/8/29.
 */

public class ButlerFragment extends Fragment implements View.OnClickListener {

    private Button btn_send;
    private EditText et_text;
    private ListView mChatListView;

    private ChatListAdapter adapter;
    private List<ChatListData> mList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.butler_fragmeng,null);
        findView(view);
        return view;
    }

    private void findView(View view) {
        mChatListView = (ListView) view.findViewById(R.id.mChatListView);
        btn_send = (Button) view.findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);
        et_text = (EditText) view.findViewById(R.id.et_text);

        //设置适配器
        adapter = new ChatListAdapter(getActivity(),mList);
        mChatListView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_send:
                //1、获取输入框的内容
                String et = et_text.getText().toString();
                //2、判断输入框是否为空
                if (!TextUtils.isEmpty(et)){
                    //3、判断输入框内容长度是否超过30
                    if (et.length()>30){
                        Toast.makeText(getActivity(),"内容长度不能超过30",Toast.LENGTH_SHORT).show();
                    }else {
                        //4、清空输入框
                        et_text.setText("");
                        //5、添加输入框内容到right item;
                        addRightItem(et);
                        //6、发送给机器人请求返回内容
                        String url = "http://op.juhe.cn/robot/index?info=" + et
                                + "&key=" + StaticClass.CHAT_LIST_KEY;
                        RxVolley.get(url, new HttpCallback() {
                            @Override
                            public void onSuccess(String t) {
                                L.i("Json" + t);
                                parsingJson(t);
                            }
                        });
                    }
                }else {
                    Toast.makeText(getActivity(),"输入框不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObhect = new JSONObject(t);
            JSONObject jsonresult = jsonObhect.getJSONObject("result");
            //拿到返回值
            String et = jsonresult.getString("text");
            //7.拿到机器人的返回值之后添加在left item
            addLeftItem(et);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addLeftItem(String text){

        ChatListData date = new ChatListData();
        date.setType(ChatListAdapter.VALUE_LEFT_TEXT);
        date.setText(text);
        mList.add(date);
        //通知adapter刷新
        adapter.notifyDataSetChanged();
        //滚动到底部
        mChatListView.setSelection(mChatListView.getBottom());

    }

    public void addRightItem(String text){

        ChatListData date = new ChatListData();
        date.setType(ChatListAdapter.VALUE_LEFT_TEXT);
        date.setText(text);
        mList.add(date);
        //通知adapter刷新
        adapter.notifyDataSetChanged();
        //滚动到底部
        mChatListView.setSelection(mChatListView.getBottom());

    }
}
