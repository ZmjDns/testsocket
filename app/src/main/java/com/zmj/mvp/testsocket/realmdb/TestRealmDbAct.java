package com.zmj.mvp.testsocket.realmdb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zmj.mvp.testsocket.R;
import com.zmj.mvp.testsocket.bean.KefuUser;
import com.zmj.mvp.testsocket.bean.User;
import com.zmj.mvp.testsocket.utils.Constant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class TestRealmDbAct extends AppCompatActivity implements View.OnClickListener{

    private final String TAG = this.getClass().getSimpleName();

    private TextView tv_data;
    private Button btn_loadData,btn_writeData,btn_deleteData;

    private List<KefuUser> dataList;

    Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_realm_db);

        tv_data = findViewById(R.id.tv_data);

        btn_loadData = findViewById(R.id.btn_loadData);
        btn_writeData = findViewById(R.id.btn_writeData);
        btn_deleteData = findViewById(R.id.btn_deleteData);

        btn_loadData.setOnClickListener(this);
        btn_writeData.setOnClickListener(this);
        btn_deleteData.setOnClickListener(this);


//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                loadData();
//            }
//        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_loadData:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        loadData();
                    }
                }).start();
                break;
            case R.id.btn_writeData:
                insertData();
                findData();
                break;
            case R.id.btn_deleteData:
                deleteData();
                findData();
                break;
        }
    }

    private void loadData(){
        HttpURLConnection connection;
        try {
            URL url = new URL(Constant.HOST_KEFU);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int code = connection.getResponseCode();
            if (code == 200){
                InputStream inputStream = connection.getInputStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                byte[] tmp = new byte[1024];
                int len = -1;
                while ((len = inputStream.read(tmp)) != -1){
                    outputStream.write(tmp,0,len);
                }

                String outKefuStr = new String(outputStream.toByteArray(),"utf-8");
                anayliseAndSaveData(outKefuStr);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void anayliseAndSaveData(String kefuStr) {
        List<KefuUser> kefuUserList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(kefuStr);
            JSONArray jsonArray = jsonObject.getJSONArray("users");
            int size = jsonObject.getInt("size");


            for (int i = 0; i < size; i++) {
                JSONObject userObj = jsonArray.getJSONObject(i);
                KefuUser user = new KefuUser();

                user.setAccount(userObj.getString("account"));
                user.setNickName(userObj.getString("nickname"));
                user.setType(userObj.getString("type"));

                kefuUserList.add(user);
            }

            Log.d(TAG, "anayliseAndSaveData: 数据解析完毕。。。");

            dataList =  kefuUserList;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertData(){
        realm = Realm.getDefaultInstance();

        try {
            realm.beginTransaction();
            realm.insert(dataList);
            realm.commitTransaction();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void findData(){
        realm = Realm.getDefaultInstance();
        RealmResults<KefuUser> kefuUsers = realm.where(KefuUser.class).findAll();

        StringBuilder builder = new StringBuilder();

        if (kefuUsers.size() < 1){
            builder.append("暂无数据");
        }else {
            for (int i = 0; i < kefuUsers.size(); i++){
                KefuUser user = kefuUsers.get(i);
                builder.append("account:" + user.getAccount());
                builder.append("nickName:" + user.getNickName());
                builder.append("type:" + user.getType());
                builder.append("\n");
            }
        }

        tv_data.setText(builder.toString());
        Log.d(TAG, "findData: 数据查询完毕。。。。");
    }

    private void deleteData(){
        realm = Realm.getDefaultInstance();
        final RealmResults<KefuUser> kefuUsers = realm.where(KefuUser.class).findAll();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //删除所有数据
                kefuUsers.deleteAllFromRealm();
                Log.d(TAG, "execute: 数据删除完毕。。。。。");
            }
        });
    }



}
