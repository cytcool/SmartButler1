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
import com.atguigu.smartbutler1.utils.ShareUtils;
import com.atguigu.smartbutler1.utils.StaticClass;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
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

    //TTS
    private SpeechSynthesizer mTts;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.butler_fragmeng,null);
        findView(view);
        return view;
    }

    private void findView(View view) {

        //1.创建SpeechSynthesizer对象, 第二个参数：本地合成时传InitListener
        mTts = SpeechSynthesizer.createSynthesizer(getActivity(), null);
        //2.合成参数设置，详见《科大讯飞MSC API手册(Android)》SpeechSynthesizer 类
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
        //设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”
        //保存在SD卡需要在AndroidManifest.xml添加写SD卡权限
        //如果不需要保存合成音频，注释该行代码
        //mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");

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

        boolean isSpeak = ShareUtils.getBoolean(getActivity(), "isSpeak", false);
        if (isSpeak) {
            startSpeak(text);
        }

        ChatListData date = new ChatListData();
        date.setType(ChatListAdapter.VALUE_LEFT_TEXT);
        date.setText(text);
        mList.add(date);
        //通知adapter刷新
        adapter.notifyDataSetChanged();
        //滚动到底部
        mChatListView.setSelection(mChatListView.getBottom());

    }


    //开始说话
    private void startSpeak(String text) {
        //3.开始合成
        mTts.startSpeaking(text, mSynListener);
    }

    //合成监听器
    private SynthesizerListener mSynListener = new SynthesizerListener() {
        //会话结束回调接口，没有错误时，error为null
        public void onCompleted(SpeechError error) {
        }

        //缓冲进度回调
        //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
        }

        //开始播放
        public void onSpeakBegin() {
        }

        //暂停播放
        public void onSpeakPaused() {
        }

        //播放进度回调
        //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
        }

        //恢复播放回调接口
        public void onSpeakResumed() {
        }

        //会话事件回调接口
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
        }
    };
}
