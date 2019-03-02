package com.zmj.mvp.testsocket.viewpager.leftrightvpinact;

import android.provider.ContactsContract;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zmj.mvp.testsocket.R;

import java.util.ArrayList;
import java.util.List;

public class LikeRedBookAct extends AppCompatActivity {

    private ViewPager vp_pic_gallery;

    private List<String> picUrls = new ArrayList<>();
    private VpAdapter vpAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_red_book);
        vp_pic_gallery = findViewById(R.id.vp_pic_gallery);

        initData();
        initVP();
    }

    private void initVP(){
        vpAdapter = new VpAdapter(this,picUrls);
        vp_pic_gallery.setAdapter(vpAdapter);
    }

    private void initData(){
        picUrls.add("http://192.168.1.254:8080/corvertSXWWData/pics/view_1.jpg");
        picUrls.add("http://192.168.1.254:8080/corvertSXWWData/pics/view_2.jpg");
        picUrls.add("http://192.168.1.254:8080/corvertSXWWData/pics/view_3.jpg");
        picUrls.add("http://192.168.1.254:8080/corvertSXWWData/pics/view_4.jpg");
        picUrls.add("http://192.168.1.254:8080/corvertSXWWData/pics/view_5.jpg");
        picUrls.add("http://192.168.1.254:8080/corvertSXWWData/pics/view_6.jpg");
    }

    @Override
    protected void onDestroy() {
        vpAdapter = null;
        picUrls = null;
        super.onDestroy();
    }
}
