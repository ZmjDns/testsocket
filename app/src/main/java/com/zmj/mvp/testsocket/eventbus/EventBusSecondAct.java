package com.zmj.mvp.testsocket.eventbus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zmj.mvp.testsocket.R;
import com.zmj.mvp.testsocket.bean.MessageEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * 用EventBus发送事件
 */
public class EventBusSecondAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_second);
    }

    public void sendEventBusMessage(View view){
        EventBus.getDefault().post(new MessageEvent("EventBus消息"));
        finish();
    }
}
