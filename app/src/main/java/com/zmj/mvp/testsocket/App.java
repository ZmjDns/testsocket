package com.zmj.mvp.testsocket;

import android.app.Application;

import com.zmj.mvp.testsocket.netkuangjia.HttpHelper;
import com.zmj.mvp.testsocket.netkuangjia.OkHttpProvessor;
import com.zmj.mvp.testsocket.netkuangjia.VolleyProcessor;
import com.zmj.mvp.testsocket.netkuangjia.XUtilsProsessor;

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

        //volley加载
        //HttpHelper.init(new VolleyProcessor(this));

        //okhttp加载  okHttp运行在子线程中，在OkHttpProvessor定义handler进行线程间通信
        //HttpHelper.init(new OkHttpProvessor());

        //Xutils加载
        HttpHelper.init(new XUtilsProsessor(this));
    }
}
