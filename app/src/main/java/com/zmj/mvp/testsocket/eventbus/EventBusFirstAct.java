package com.zmj.mvp.testsocket.eventbus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zmj.mvp.testsocket.R;
import com.zmj.mvp.testsocket.bean.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * EventBus的注册、订阅和解除
 */
public class EventBusFirstAct extends AppCompatActivity {

    private TextView tv_show_message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_first);

        tv_show_message = findViewById(R.id.tv_show_message);

        tv_show_message.setText("Today is Wensday");

        //注册EventBus
        EventBus.getDefault().register(this);
    }

    //订阅
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(MessageEvent messageEvent){
        tv_show_message.setText(messageEvent.getMessage());
    }


    public void gotoNextAct(View view){
        startActivity(new Intent(this,EventBusSecondAct.class));
    }

    @Override
    protected void onDestroy() {
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }
}
