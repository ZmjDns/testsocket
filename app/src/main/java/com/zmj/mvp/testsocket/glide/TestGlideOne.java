/*
package com.zmj.mvp.testsocket.glide;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.zmj.mvp.testsocket.R;
import com.zmj.mvp.testsocket.widget.ChooseDialog;

import java.net.SocketImpl;

public class TestGlideOne extends AppCompatActivity {

    private ImageView iv_glide_pic;
    private MyLayout ml_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_glide_one);

        iv_glide_pic = findViewById(R.id.iv_glide_pic);
        ml_layout = findViewById(R.id.ml_layout);
    }

    public void loadGlidePic(View view){
        glideLoadPicToIV();
        //loadPicWithMyGlideUrl();
    }

    private void glideLoadPicToIV() {
        String url = "http://ww3.sinaimg.cn/large/610dc034jw1fasakfvqe1j20u00mhgn2.jpg";
        Glide
                .with(this)
                .load(url)
                //.into(iv_glide_pic);
                //.into(getSimpleTarget());
                //.into(getBitmapSimpleTarget());??传参错误，什么情况
                .into(ml_layout.getTarget());
    }

    private void loadPicWithMyGlideUrl(){
        //带token的地址
        String urlWitToken = "http://url.com/image.jpg?token=d9caa6e02c990b0a";
        Glide
                .with(this)
                .load(new MyGlideUrl(urlWitToken))
                .into(iv_glide_pic);
    }

    private SimpleTarget<GlideDrawable> getSimpleTarget(){

        SimpleTarget<GlideDrawable> simpleTarget = new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                iv_glide_pic.setImageDrawable(resource);
            }
        };

        return simpleTarget;
    }

    private SimpleTarget<Bitmap> getBitmapSimpleTarget(){
        SimpleTarget<Bitmap> simpleTarget = new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                iv_glide_pic.setImageBitmap(resource);
            }
        };

        return simpleTarget;
    }

    public void showDialog(View view){
        new ChooseDialog(this).show();




        */
/*String[] res = {"AA","BB","CC"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Have you asked before?")
                .setMultiChoiceItems(res, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                    }
                })
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();*//*


    }
}
*/
