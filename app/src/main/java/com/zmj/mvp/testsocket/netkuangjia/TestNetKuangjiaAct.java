package com.zmj.mvp.testsocket.netkuangjia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zmj.mvp.testsocket.R;
import com.zmj.mvp.testsocket.bean.User;

public class TestNetKuangjiaAct extends AppCompatActivity {

    private String url = "http://baidu.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_net_kuangjia);
    }

    private void anayslis(){
        HttpHelper.obtaion().post(url, null, new HttpCallback<User>() {
            @Override
            public void onFailed(String error) {

            }

            @Override
            public void onSuccess(User user) {

            }
        });
    }
}
