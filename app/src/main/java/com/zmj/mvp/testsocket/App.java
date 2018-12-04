package com.zmj.mvp.testsocket;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * @author Zmj
 * @date 2018/12/4
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //加载Realm数据库
        Realm.init(this);
        //配置Realm
        RealmConfiguration myConfig = new RealmConfiguration.Builder()
                .name("myrealm.realm")  //数据库名称
                .schemaVersion(2)       //版本号
                .build();

        Realm.setDefaultConfiguration(myConfig);
    }
}
