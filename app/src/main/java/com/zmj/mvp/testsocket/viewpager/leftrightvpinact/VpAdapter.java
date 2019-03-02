package com.zmj.mvp.testsocket.viewpager.leftrightvpinact;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.zmj.mvp.testsocket.R;

import java.util.List;

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * DitHub : https://github.com/ZmjDns
 * Time : 2019/2/28
 * Description :
 */
public class VpAdapter extends PagerAdapter {

    private Context context;
    private List<String> urlLists;

    public VpAdapter(Context context, List<String> urlLists) {
        this.context = context;
        this.urlLists = urlLists;
    }

    @Override
    public int getCount() {
        return urlLists.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(container.getContext());
        Glide.with(container.getContext()).load(urlLists.get(position)).into(imageView);
        //Picasso.with(context).load(urlLists.get(position)).placeholder(R.mipmap.ic_launcher).into(imageView);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        container.addView(imageView);

        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        Glide.with(container.getContext()).clear((View) object);
        container.removeView((View) object);
    }
}
