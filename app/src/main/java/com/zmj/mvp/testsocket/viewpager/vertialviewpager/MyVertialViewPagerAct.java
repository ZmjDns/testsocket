package com.zmj.mvp.testsocket.viewpager.vertialviewpager;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zmj.mvp.testsocket.R;
import com.zmj.mvp.testsocket.viewpager.MyVerticalViewPager;

import java.util.ArrayList;

public class MyVertialViewPagerAct extends AppCompatActivity implements View.OnClickListener {

//    private MyVerticalViewPager vvp_pager;
    private VerticalViewPager vvp_pager;

    private LinearLayout ll_operates,ll_comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //无标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

        setContentView(R.layout.activity_my_vertial_view_pager);

        initView();
    }

    private void initView() {
        vvp_pager = findViewById(R.id.vvp_pager);

        ll_operates = findViewById(R.id.ll_operates);
        ll_operates.setOnClickListener(this);

        ll_comments = findViewById(R.id.ll_comments);
        ll_comments.setOnClickListener(this);

        ArrayList<View> viewArrayList = new ArrayList<View>();
        viewArrayList.add(getLayoutInflater().inflate(R.layout.view_pager_one,null,false));
        viewArrayList.add(getLayoutInflater().inflate(R.layout.view_pager_second,null,false));
        viewArrayList.add(getLayoutInflater().inflate(R.layout.view_pager_thrid,null,false));
        viewArrayList.add(getLayoutInflater().inflate(R.layout.view_pager_fourth,null,false));


        //vvp_pager.setAdapter(new MyInnerPagerAdapter(viewArrayList));
        vvp_pager.setViewList(viewArrayList);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_operates:
                ll_comments.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_comments:
                Toast.makeText(this,"滑动试试",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    class MyInnerPagerAdapter extends PagerAdapter{

        ArrayList<View> viewArrayList ;

        public MyInnerPagerAdapter(ArrayList<View> viewArrayList) {
            super();
            this.viewArrayList = viewArrayList;
        }

        @Override
        public int getCount() {
            return viewArrayList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(viewArrayList.get(position));
            return viewArrayList.get(position);
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(viewArrayList.get(position));
        }
    }
}
