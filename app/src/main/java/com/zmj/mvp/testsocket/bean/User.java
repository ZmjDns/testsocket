package com.zmj.mvp.testsocket.bean;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * @author Zmj
 * @date 2018/11/19
 */
public class User extends RealmObject implements Serializable {
//    private Socket socket;
    private String account;
    private String nickname;
    private String type;

//    private String password;
//    private InetAddress ipAddress;
//    private boolean isOnline;


    public User(String account, String nickname, String type) {
        this.account = account;
        this.nickname = nickname;
        this.type = type;
    }

    public User() {
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
