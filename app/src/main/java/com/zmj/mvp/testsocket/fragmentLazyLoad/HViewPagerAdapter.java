package com.zmj.mvp.testsocket.fragmentLazyLoad;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * DitHub : https://github.com/ZmjDns
 * Time : 2019/2/25
 * Description :
 */
public class HViewPagerAdapter extends FragmentStatePagerAdapter {

    private String[] mTitles;

    private final String TAG = this.getClass().getSimpleName();

    public HViewPagerAdapter(FragmentManager fm,String[] mTitles) {
        super(fm);
        this.mTitles = mTitles;
    }

    @Override
    public Fragment getItem(int position) {
        EmptyFragment fragment = EmptyFragment.newInstance(mTitles[position]);
        Log.d(TAG, "getItem: 当前位置：" + position);
        return fragment;
    }

    @Override
    public int getCount() {
        return mTitles == null ? 0 : mTitles.length;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Log.d(TAG, "getItem: 当前位置：" + position);
        return super.instantiateItem(container, position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles == null ? "" : mTitles[position];
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }

    public void setTitles(String[] titles){
        mTitles = titles;
        notifyDataSetChanged();
    }
}
