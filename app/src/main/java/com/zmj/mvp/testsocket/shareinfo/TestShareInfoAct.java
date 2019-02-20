package com.zmj.mvp.testsocket.shareinfo;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.j256.ormlite.stmt.query.In;
import com.zmj.mvp.testsocket.R;
import com.zmj.mvp.testsocket.utils.AesUtil;

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * DitHub : https://github.com/ZmjDns
 * Time : 2019/2/20
 * Description :
 */
public class TestShareInfoAct extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    private EditText et_info;
    private TextView tv_before_encrypt,tv_after_encrypt,tv_after_decrypt;

    String info,encryptedInfo,decryptedINfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_share_info);

        et_info = findViewById(R.id.et_info);

        tv_before_encrypt = findViewById(R.id.tv_before_encrypt);
        tv_after_encrypt = findViewById(R.id.tv_after_encrypt);
        tv_after_decrypt = findViewById(R.id.tv_after_decrypt);
    }

    public void shareInfo(View view){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,"https://www.baidu.com/");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    public void toEncrypt(View view){
        info = et_info.getText().toString();
        tv_before_encrypt.append(info);
        try {
            encryptedInfo = AesUtil.encrypt("TEST",info);
            tv_after_encrypt.append(encryptedInfo);
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.d(TAG, "toEncrypt: info:" + info + " encryptedInfo:" + encryptedInfo);
    }

    public void toDecrypt(View view){
        try{
            decryptedINfo = AesUtil.decrypt("TEST",encryptedInfo);
            tv_after_decrypt.append(decryptedINfo);
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.d(TAG, "toDecrypt: info:" + info + " encryptedInfo:" + encryptedInfo + " decryptedINfo：" + decryptedINfo);
    }

    public void getCode1(View view){
        CountDownTimer timer = new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                view.setEnabled(false);
                view.//setTooltipText("以发送" + millisUntilFinished / 1000 );
            }

            @Override
            public void onFinish() {

            }
        };
    }
}
