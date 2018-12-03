package com.zmj.mvp.testsocket.chatview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zmj.mvp.testsocket.R;

public class LoginAct extends AppCompatActivity {

    private EditText ed_userAccount;
    private Button btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ed_userAccount = findViewById(R.id.ed_userAccount);

        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login(){
        if (ed_userAccount.getText().toString().trim().length() != 11){
            Toast.makeText(this,"请填写正确的手机号",Toast.LENGTH_SHORT).show();
        }else {
            SharedPreferences sp = getSharedPreferences("testuser",0);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("me",ed_userAccount.getText().toString().trim());
            editor.commit();
            startActivity(new Intent(this,MainRellationAct.class));
        }
    }
}
