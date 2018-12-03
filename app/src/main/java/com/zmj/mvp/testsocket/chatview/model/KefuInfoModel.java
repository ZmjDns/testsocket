package com.zmj.mvp.testsocket.chatview.model;

import android.util.JsonReader;
import android.util.Log;

import com.zmj.mvp.testsocket.bean.User;
import com.zmj.mvp.testsocket.chatview.iviewipersent.IKefuModel;
import com.zmj.mvp.testsocket.utils.Constant;

import org.java_websocket.util.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zmj
 * @date 2018/11/30
 */
public class KefuInfoModel implements IKefuModel {
    private final String TAG = this.getClass().getSimpleName();

    @Override
    public void loadKefu(final OnLoadKefuListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getKefuUser(listener);
            }
        }).start();
    }

    private void getKefuUser(final OnLoadKefuListener listener){
        HttpURLConnection connection;
        try{
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

                String kefus = new String(outputStream.toByteArray(),"utf-8");
                Log.d(TAG, "loadKefu: kefuSting: " + kefus);

                listener.onComplete(changeStringToObj(kefus));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private List<User> changeStringToObj(String kefus){
        List<User> userList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(kefus);
            JSONArray jsonArray = jsonObject.getJSONArray("users");
            int size = jsonObject.getInt("size");
            for (int i = 0; i < size; i++){
                JSONObject userObj = jsonArray.getJSONObject(i);
                User user = new User();

                user.setAccount(userObj.getString("account"));
                user.setNickname(userObj.getString("nickname"));
                user.setType(userObj.getString("type"));
                userList.add(user);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return userList;
    }
}
