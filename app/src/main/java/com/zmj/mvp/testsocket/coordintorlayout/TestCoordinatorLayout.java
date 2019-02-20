package com.zmj.mvp.testsocket.coordintorlayout;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zmj.mvp.testsocket.R;

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * DitHub : https://github.com/ZmjDns
 * Time : 2019/2/18
 * Description :https://blog.csdn.net/hnkwei1213/article/details/78507997
 */
public class TestCoordinatorLayout extends AppCompatActivity {

    private boolean setBottomSheetHeight= false;

    private CoordinatorLayout coordinator;
    private RelativeLayout design_bottom_sheet,design_bottom_bar;
    private RecyclerView bottom_sheet_rv;
    private TextView bottom_sheet_tv;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_coordinator_layout);

        //协调布局
        coordinator = findViewById(R.id.coordinator);

        //展开头部bar
        design_bottom_bar = findViewById(R.id.design_bottom_bar);

        //折叠后显示
        design_bottom_sheet = findViewById(R.id.design_bottom_sheet);

        bottom_sheet_tv = findViewById(R.id.bottom_sheet_tv);
        //recyclerView
        bottom_sheet_rv = findViewById(R.id.bottom_sheet_rv);

        final BottomSheetBehavior behavior = BottomSheetBehavior.from(design_bottom_sheet);
        //默认让其展开状态，此时design_bottom_sheet的高度是总布局高度的一半，正好显示在中间
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottom_sheet_tv.setVisibility(View.GONE);

        //点击上方的锚定条折叠
        design_bottom_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            //BottomSheet状态改变时的回调
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                //collapsed
                if (newState != BottomSheetBehavior.STATE_COLLAPSED && bottom_sheet_tv.getVisibility() == View.VISIBLE){
                    bottom_sheet_tv.setVisibility(View.GONE);
                    bottom_sheet_rv.setVisibility(View.VISIBLE);
                }else if (newState == BottomSheetBehavior.STATE_COLLAPSED && bottom_sheet_tv.getVisibility() == View.GONE){
                    bottom_sheet_tv.setVisibility(View.VISIBLE);
                    bottom_sheet_rv.setVisibility(View.GONE);
                }
            }
            //BottomSheet滑动时的回调
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                if (bottomSheet.getTop() > coordinator.getHeight() /2 ){
                    CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) design_bottom_sheet.getLayoutParams();
                    params.height = coordinator.getHeight() / 2;
                    design_bottom_sheet.setLayoutParams(params);
                }else {
                    CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) design_bottom_sheet.getLayoutParams();
                    params.height = coordinator.getHeight() - design_bottom_bar.getHeight();
                    design_bottom_sheet.setLayoutParams(params);
                }


                if (bottomSheet.getTop() < 2 * design_bottom_bar.getHeight()){
                    design_bottom_bar.setVisibility(View.VISIBLE);
                    design_bottom_bar.setAlpha(slideOffset);
                    design_bottom_bar.setTranslationY(bottomSheet.getTop() - 2 * design_bottom_bar.getHeight());
                }else {
                    design_bottom_bar.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    //设置顶部位置（由于design_bottom_sheet布局充满父布局，此处需要留出顶部位置）
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //修改design_bottom_sheet高度，显示总布局一般高度
        if(setBottomSheetHeight){
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) design_bottom_sheet.getLayoutParams();
            params.height = coordinator.getHeight()/2;
            coordinator.setLayoutParams(params);
            setBottomSheetHeight = true;
        }
    }

    private void initrecycler(){
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        bottom_sheet_rv.setLayoutManager(manager);

    }
}
