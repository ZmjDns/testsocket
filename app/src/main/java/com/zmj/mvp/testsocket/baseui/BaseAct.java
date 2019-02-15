package com.zmj.mvp.testsocket.baseui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zmj.mvp.testsocket.ormlitedb.OrmLiteHelper;

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * DitHub : https://github.com/ZmjDns
 * Time : 2019/2/15
 * Description :
 */
public abstract class BaseAct extends AppCompatActivity {

    protected OrmLiteHelper ormLiteHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ormLiteHelper = OrmLiteHelper.getOrmLiteHelper(this);
    }
}
