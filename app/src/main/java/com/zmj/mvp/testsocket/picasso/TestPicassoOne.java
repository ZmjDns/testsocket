package com.zmj.mvp.testsocket.picasso;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.zmj.mvp.testsocket.R;

import java.util.ArrayList;
import java.util.List;

public class TestPicassoOne extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();

    private ImageView iv_pic1;
    private RecyclerView rv_recycler_pic;
    private List<String> dataLists = new ArrayList<>();
    private RecyclerPicassoAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_picasso_one);

        iv_pic1 = findViewById(R.id.iv_picone);
        rv_recycler_pic = findViewById(R.id.rv_recycler_pic);
    }

    public void loadImage(View view){
        loadPicByPicasso();

//        initDataUrl();
//        initRecycler();
    }

    private void loadPicByPicasso(){
        String url = "http://ww3.sinaimg.cn/large/610dc034jw1fasakfvqe1j20u00mhgn2.jpg";
        Picasso
                .get()
                .load(url)
                .resize(200,200)
                //.into(iv_pic1);
                .into(loadPicByTarget());
    }

    private void initDataUrl() {
        dataLists.clear();
        dataLists.add("http://i.imgur.com/rFLNqWI.jpg");
        dataLists.add("http://i.imgur.com/C9pBVt7.jpg");
        dataLists.add("http://i.imgur.com/rT5vXE1.jpg");
        dataLists.add("http://i.imgur.com/aIy5R2k.jpg");
        dataLists.add("http://i.imgur.com/MoJs9pT.jpg");
        dataLists.add("http://i.imgur.com/S963yEM.jpg");
        dataLists.add("http://i.imgur.com/rLR2cyc.jpg");
        dataLists.add("http://i.imgur.com/SEPdUIx.jpg");
        dataLists.add("http://i.imgur.com/aC9OjaM.jpg");
        dataLists.add("http://i.imgur.com/76Jfv9b.jpg");
        dataLists.add("http://i.imgur.com/fUX7EIB.jpg");
        dataLists.add("http://i.imgur.com/syELajx.jpg");
        dataLists.add("http://i.imgur.com/COzBnru.jpg");
        dataLists.add("http://i.imgur.com/Z3QjilA.jpg");
    }

    private void initRecycler(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rv_recycler_pic.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerPicassoAdapter(this,dataLists);
        rv_recycler_pic.setAdapter(adapter);

    }


    private Target loadPicByTarget(){

        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Log.d(TAG, "onBitmapLoaded: 获取到Bitmap了" );
                bitmap.setHasAlpha(true);
                iv_pic1.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                Log.d(TAG, "onBitmapFailed: 加载出错" + e.getMessage());
                errorDrawable = getResources().getDrawable(R.drawable.toright);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                Log.d(TAG, "onPrepareLoad: target");
                placeHolderDrawable = getResources().getDrawable(R.drawable.chat_send);
            }
        };

        return target;

    }

}
