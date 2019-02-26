package com.zmj.mvp.testsocket.viewpager.newverticalpager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * DitHub : https://github.com/ZmjDns
 * Time : 2019/2/25
 * Description :合理管理Fragment生命周期的Adapter
 * https://www.jianshu.com/p/2c71f699c5c4
 */
public class VerticalVPAdapter extends PagerAdapter {

    private final String TAG = this.getClass().getSimpleName();

    private FragmentManager fragmentManager;
    private FragmentTransaction mCurTransaction;
    private Fragment mCurrentPrimaryItem = null;
    private List<String> urlLists;

    public void setUrlLists(List<String> urlLists){
        this.urlLists = urlLists;
    }

    public VerticalVPAdapter(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        if (mCurTransaction == null){
            mCurTransaction = fragmentManager.beginTransaction();
        }

        FragmentTwo fragmentTwo = new FragmentTwo();
        if (urlLists.size() > 0 && urlLists != null){
            Bundle bundle = new Bundle();
            if (position >= urlLists.size()){
                bundle.putString("INFO",urlLists.get(position % urlLists.size()));
            } else {
                bundle.putString("INFO",urlLists.get(position));
            }
            fragmentTwo.setArguments(bundle);
        }

        mCurTransaction.add(container.getId(),fragmentTwo);
        fragmentTwo.setUserVisibleHint(false);

        return fragmentTwo;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        if (mCurTransaction == null){
            mCurTransaction = fragmentManager.beginTransaction();
        }
        mCurTransaction.detach((Fragment) object);
        mCurTransaction.remove((Fragment) object);
        super.destroyItem(container, position, object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return ((Fragment)object).getView() == object;
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Fragment fragment = (Fragment) object ;
        if (fragment != mCurrentPrimaryItem){
            if (mCurrentPrimaryItem != null){
                mCurrentPrimaryItem.setMenuVisibility(false);
                mCurrentPrimaryItem.setUserVisibleHint(false);
            }

            if (fragment != null){
                fragment.setMenuVisibility(true);
                fragment.setUserVisibleHint(true);
            }
            mCurrentPrimaryItem = fragment;
        }
    }

    @Override
    public void finishUpdate(@NonNull ViewGroup container) {
        if (mCurrentPrimaryItem != null){
            mCurTransaction.commitAllowingStateLoss();
            mCurTransaction = null;
        }
    }
}
