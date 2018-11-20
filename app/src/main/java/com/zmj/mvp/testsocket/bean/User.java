package com.zmj.mvp.testsocket.bean;

import java.io.Serializable;

/**
 * @author Zmj
 * @date 2018/11/19
 */
public class User implements Serializable {
//    private Socket socket;
    private String userName;
//    private String password;
//    private InetAddress ipAddress;
//    private boolean isOnline;


    public User(String userName) {
//        this.socket = socket;
        this.userName = userName;
//        this.ipAddress = socket.getInetAddress();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
