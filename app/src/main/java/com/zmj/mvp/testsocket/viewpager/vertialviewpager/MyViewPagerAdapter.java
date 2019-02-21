package com.zmj.mvp.testsocket.viewpager.vertialviewpager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * DitHub : https://github.com/ZmjDns
 * Time : 2019/2/20
 * Description :
 */
public class MyViewPagerAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<View> viewArrayList;

    public MyViewPagerAdapter(Context context,ArrayList<View> viewArrayList) {
        this.context = context;
        this.viewArrayList = viewArrayList;
    }

    public MyViewPagerAdapter(ArrayList<View> viewArrayList) {
        this.viewArrayList = viewArrayList;
    }

    @Override
    public int getCount() {
        return viewArrayList.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(viewArrayList.get(position));
        return viewArrayList.get(position);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(viewArrayList.get(position));
    }
}
