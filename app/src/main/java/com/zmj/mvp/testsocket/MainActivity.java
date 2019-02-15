package com.zmj.mvp.testsocket;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zmj.mvp.testsocket.chatview.LoginAct;
import com.zmj.mvp.testsocket.eventbus.EventBusFirstAct;
import com.zmj.mvp.testsocket.glide.TestGlideOne;
import com.zmj.mvp.testsocket.kotlins.KotlinTest;
import com.zmj.mvp.testsocket.ormlitedb.TestOrmLiteDBAct;
import com.zmj.mvp.testsocket.picasso.TestPicassoOne;
import com.zmj.mvp.testsocket.realmdb.TestRealmDbAct;
import com.zmj.mvp.testsocket.rxjava2.TestRxJavaAct1;
import com.zmj.mvp.testsocket.rxjavaandretrofit.loginmvp.TestRxJandRetrAct2;
import com.zmj.mvp.testsocket.rxjavaandretrofit.TestRxJandRetrofitAct1;
import com.zmj.mvp.testsocket.socketmvp.MySocketAct;
import com.zmj.mvp.testsocket.socketmvp.ObjInSocketAct;
import com.zmj.mvp.testsocket.socketmvp.TcpSocketAct;
import com.zmj.mvp.testsocket.socketmvp.TcpSocketAct1;
import com.zmj.mvp.testsocket.socketmvp.TcpSocketPlusAct;
import com.zmj.mvp.testsocket.viewpager.TestViewPagerAct;
import com.zmj.mvp.testsocket.websocket.MyWebSocketAct;
import com.zmj.mvp.testsocket.websocket.MyWebSocketPlusAct;
import com.zmj.mvp.testsocket.websocketmvp.MyMvpWebSocketAct;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_socket, btn_socket1,btn_socketPlus,btn_objInSocket,btn_mySocket,
            btn_websocket,btn_websockrtPlus,btn_webSOcketMvp,btn_ceshi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn_socket = findViewById(R.id.btn_socket1);
        btn_socket1 = findViewById(R.id.btn_socket);
        btn_socketPlus = findViewById(R.id.btn_socketPlus);
        btn_objInSocket = findViewById(R.id.btn_objInSocket);
        btn_mySocket = findViewById(R.id.btn_mySocket);
        btn_websocket = findViewById(R.id.btn_websocket);
        btn_websockrtPlus = findViewById(R.id.btn_websockrtPlus);
        btn_webSOcketMvp = findViewById(R.id.btn_webSOcketMvp);
        findViewById(R.id.btn_ceshi).setOnClickListener(this);
        findViewById(R.id.btn_realm).setOnClickListener(this);

        btn_socket.setOnClickListener(this);
        btn_socket1.setOnClickListener(this);
        btn_socketPlus.setOnClickListener(this);
        btn_objInSocket.setOnClickListener(this);
        btn_mySocket.setOnClickListener(this);
        btn_websocket.setOnClickListener(this);
        btn_websockrtPlus.setOnClickListener(this);
        btn_webSOcketMvp.setOnClickListener(this);

        //写入用户
        initUser();

        getPermission();
    }

    private void getPermission(){
        if (!(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},001);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 001 || grantResults[0] == 0){
            //获取权限成功
            Toast.makeText(this,"获取权限成功",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_socket:
                startActivity(new Intent(this, TcpSocketAct.class));
                break;
            case R.id.btn_socket1:
                startActivity(new Intent(this, TcpSocketAct1.class));
                break;
            case R.id.btn_socketPlus:
                startActivity(new Intent(this, TcpSocketPlusAct.class));
              break;
            case R.id.btn_objInSocket:
                startActivity(new Intent(this, ObjInSocketAct.class));
                break;
            case R.id.btn_mySocket:
                startActivity(new Intent(this, MySocketAct.class));
                break;
            case R.id.btn_websocket:
                startActivity(new Intent(this, MyWebSocketAct.class));
                break;
            case R.id.btn_websockrtPlus:
                startActivity(new Intent(this, MyWebSocketPlusAct.class));
                break;
            case R.id.btn_webSOcketMvp:
                startActivity(new Intent(this, MyMvpWebSocketAct.class));
                break;
            case R.id.btn_ceshi:
                startActivity(new Intent(this, LoginAct.class));
                break;
            case R.id.btn_realm:
                startActivity(new Intent(this, TestRealmDbAct.class));
                break;
        }
    }

    private void initUser(){
        SharedPreferences sharedPreferences = getSharedPreferences("user",0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("三星","18302451883");
//        editor.putString("小米","15822009415");
        editor.putString("小米","15822009415");
        editor.commit();
    }

    public void gotoKotlin(View view){
        startActivity(new Intent(this, KotlinTest.class));
    }

    public void gotoPicasso(View view){
        startActivity(new Intent(this, TestPicassoOne.class));
    }

    public void gotoGlide(View view){startActivity(new Intent(this, TestGlideOne.class));}
    public void gotoEventBus(View view){
        startActivity(new Intent(this, EventBusFirstAct.class));
    }

    public void gotoRxAndroid1(View view){
        startActivity(new Intent(this, TestRxJavaAct1.class));
    }

    public void gotoRxJandRetr(View view){
        startActivity(new Intent(this, TestRxJandRetrofitAct1.class));
    }

    public void gotoRxJandRetrofit(View view){
        startActivity(new Intent(this, TestRxJandRetrAct2.class));
    }

    public void gotoViewPager(View view){
        startActivity(new Intent(this, TestViewPagerAct.class));
    }

    public void gotoOrmLite(View view){
        startActivity(new Intent(this, TestOrmLiteDBAct.class));
    }
}
