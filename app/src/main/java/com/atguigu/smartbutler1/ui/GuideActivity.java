package com.atguigu.smartbutler1.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.atguigu.smartbutler1.MainActivity;
import com.atguigu.smartbutler1.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 陈雨田 on 2017/8/30.
 */

public class GuideActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private List<View> mList = new ArrayList<>();
    private View view1,view2,view3;

    private ImageView point1,point2,point3;
    private ImageView iv_back;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initView();
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);

        point1 = (ImageView) findViewById(R.id.point1);
        point2 = (ImageView) findViewById(R.id.point2);
        point3 = (ImageView) findViewById(R.id.point3);

        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        //设置图片默认属性
        setImg(true,false,false);



        view1 = View.inflate(this,R.layout.page_guide_one,null);
        view2 = View.inflate(this,R.layout.page_guide_two,null);
        view3 = View.inflate(this,R.layout.page_guide_three,null);

        view3.findViewById(R.id.bt_start).setOnClickListener(this);


        mList.add(view1);
        mList.add(view2);
        mList.add(view3);

        //设置适配器
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            //从当前的container的位置将对应的view;
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mList.get(position));

            }

            //将当前的视图加载到container，然后返回当前相对应的view
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mList.get(position));

                return mList.get(position);
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        setImg(true,false,false);
                        iv_back.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        setImg(false,true,false);
                        iv_back.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        setImg(false,false,true);
                        iv_back.setVisibility(View.GONE);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
        private void setImg(boolean isCheck1,boolean isCheck2,boolean isCheck3){
            if(isCheck1){
                point1.setBackgroundResource(R.drawable.point_on);
            }else {
                point1.setBackgroundResource(R.drawable.point_off);
            }
            if(isCheck2){
                point2.setBackgroundResource(R.drawable.point_on);
            }else {
                point2.setBackgroundResource(R.drawable.point_off);
            }
            if(isCheck3){
                point3.setBackgroundResource(R.drawable.point_on);
            }else {
                point3.setBackgroundResource(R.drawable.point_off);
            }
        }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
            case R.id.bt_start:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
    }
}
