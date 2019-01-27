package com.zmj.mvp.testsocket.viewpager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zmj.mvp.testsocket.R;

import java.util.List;

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * DitHub : https://github.com/ZmjDns
 * Time : 2019/1/27
 * Description :
 */
public class ViewPagerAdapter extends PagerAdapter {

    private final String TAG = this.getClass().getSimpleName();

    private Context mContext;
    private List<String> mData;
    private LayoutInflater mInflater;

    public ViewPagerAdapter(Context mContext, List<String> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = mInflater.inflate(R.layout.view_pager_item,container,false);

        TextView tv_show = view.findViewById(R.id.tv_show);

        tv_show.setText(mData.get(position));

        container.addView(view);

        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //super.destroyItem(container, position, object);

        container.removeView((View) object);
    }
}
