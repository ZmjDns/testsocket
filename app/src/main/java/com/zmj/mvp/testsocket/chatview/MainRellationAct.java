package com.zmj.mvp.testsocket.chatview;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zmj.mvp.testsocket.R;
import com.zmj.mvp.testsocket.chatview.fragment.HuiHuafragment;
import com.zmj.mvp.testsocket.chatview.fragment.KefuFragme;

public class MainRellationAct extends AppCompatActivity implements View.OnClickListener{

    private FrameLayout fl_mainchatContent;
    private TextView tv_kefuFragment,
            tv_huihuaFragmet;

    FragmentManager fragmentManager ;//= getSupportFragmentManager();
    FragmentTransaction fragmentTransaction ;//= fragmentManager.beginTransaction();

    private static final int KEFUFRAG = 11;
    private static final int HUIHUAFRAG = 22;

    private KefuFragme kefuFragme;
    private HuiHuafragment huiHuafragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_rellation);

        fl_mainchatContent = findViewById(R.id.fl_mainchatContent);
        tv_kefuFragment = findViewById(R.id.tv_kefuFragment);
        tv_huihuaFragmet = findViewById(R.id.tv_huihuaFragmet);

        tv_kefuFragment.setOnClickListener(this);
        tv_huihuaFragmet.setOnClickListener(this);

        tv_kefuFragment.performClick();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_kefuFragment:
                initFragment(KEFUFRAG);
                break;
            case R.id.tv_huihuaFragmet:
                initFragment(HUIHUAFRAG);
                break;
        }
    }

    private void initFragment(int frag){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        hideFragment();
        if (frag == KEFUFRAG){
            if (kefuFragme == null){
                kefuFragme = new KefuFragme();
                fragmentTransaction.add(R.id.fl_mainchatContent,kefuFragme);
                //fragmentTransaction.show(kefuFragme);
            }else {
                fragmentTransaction.show(kefuFragme);
            }
        }
        if (frag == HUIHUAFRAG){
            if (huiHuafragment == null){
                huiHuafragment = new HuiHuafragment();
                fragmentTransaction.add(R.id.fl_mainchatContent,huiHuafragment);
                //fragmentTransaction.show(huiHuafragment);
            }else {
                fragmentTransaction.show(huiHuafragment);
            }
        }

        fragmentTransaction.commit();
    }

    private void hideFragment(){
        if (kefuFragme != null){
            fragmentTransaction.hide(kefuFragme);
        }
        if (huiHuafragment != null){
            fragmentTransaction.hide(huiHuafragment);
        }
    }
}
