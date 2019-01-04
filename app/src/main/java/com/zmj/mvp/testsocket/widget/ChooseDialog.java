package com.zmj.mvp.testsocket.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.zmj.mvp.testsocket.R;
import com.zmj.mvp.testsocket.utils.ScreenUtils;

/**
 * @author Zmj
 * @date 2019/1/3
 */
public class ChooseDialog implements View.OnClickListener{

    private final String TAG = this.getClass().getSimpleName();

    private Dialog dialog;
    private View view;
    private Context context;

    private RadioGroup rg_iscomplainted,rg_channels;
    private RadioButton rb_yes,rb_no,rb_origin_web,rb_matter_center,rb_tousu_app,rb_other;
    private LinearLayout ll_tousu_qudao;

    /*private int TOUS_CHANNEL;
    private static final int NEVER_COMPLAINTED = 0;
    private static final int ORIGIN_WEB = 1;
    private static final int MATTER_CENTER = 2;
    private static final int TOUSU_APP = 3;
    private static final int OTHER = 4;*/

    public ChooseDialog(Context context) {
        this.context = context;
        this.view = LayoutInflater.from(context).inflate(R.layout.dialog_multi_choice,null);
    }

    public void show(){
        dialog = new Dialog(context);
        dialog.setContentView(view);
        final Window window = dialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        final int[] screenSize = ScreenUtils.getScreenSize(context);
        layoutParams.width = screenSize[0] * 19/20;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER;
        window.setAttributes(layoutParams);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        initView();
        dialog.show();
    }

    private void initView(){
        rg_iscomplainted = view.findViewById(R.id.rg_iscomplainted);
        view.findViewById(R.id.rb_yes).setOnClickListener(this);
        view.findViewById(R.id.rb_no).setOnClickListener(this);

        rg_channels = view.findViewById(R.id.rg_channels);
        rb_origin_web = view.findViewById(R.id.rb_origin_web);
        rb_matter_center = view.findViewById(R.id.rb_matter_center);
        rb_tousu_app = view.findViewById(R.id.rb_tousu_app);
        rb_other = view.findViewById(R.id.rb_other);

        ll_tousu_qudao = view.findViewById(R.id.ll_tousu_qudao);

        view.findViewById(R.id.btn_choosed).setOnClickListener(this);

        rg_iscomplainted.setOnCheckedChangeListener(isComplaintedListener);

        rg_channels.setOnCheckedChangeListener(channelsListener);

    }

    RadioGroup.OnCheckedChangeListener isComplaintedListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rb_yes:
                    ll_tousu_qudao.setVisibility(View.VISIBLE);

                    break;
                case R.id.rb_no:
                    ll_tousu_qudao.setVisibility(View.GONE);
                    rg_channels.clearCheck();
                    break;
            }
        }
    };

    RadioGroup.OnCheckedChangeListener channelsListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            Log.d(TAG, "onCheckedChanged: 选中的ID：" + checkedId);
            switch (checkedId){
                case R.id.rb_origin_web:
                case R.id.rb_matter_center:
                case R.id.rb_tousu_app:
                case R.id.rb_other:
                    //查询姓名投诉编号

                    break;
                case R.id.btn_choosed:
                    //进一步询问

                    break;
            }
        }
    };


    //提交
    private void submitChoos(View view){
        if (rb_yes.isChecked()){//进一步操作
            if (rb_origin_web.isChecked() || rb_matter_center.isChecked() || rb_tousu_app.isChecked()){
                //弹出界面查询之前的投诉
                dialog.dismiss();


            }else if (rb_other.isChecked()){
                //询问是否得到结果

            }else {
                Toast.makeText(context,"请选择投诉的渠道",Toast.LENGTH_SHORT).show();
            }

        }else if (rb_no.isChecked()){
            //打开投诉界面

        }else {
            Toast.makeText(context,"请选择是否投诉过",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {

    }
}
