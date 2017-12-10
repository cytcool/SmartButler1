package com.atguigu.smartbutler1.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.atguigu.smartbutler1.R;

/**
 * Created by 陈雨田 on 2017/8/29.
 */

public class GirlsFragment extends Fragment{

    private GridView mGridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.girls_fragmeng,null);
        findView(view);
        return view;
    }

    private void findView(View view) {
        mGridView = (GridView) view.findViewById(R.id.mGridView);
    }
}
