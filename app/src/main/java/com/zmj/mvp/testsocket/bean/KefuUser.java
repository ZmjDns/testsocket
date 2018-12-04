package com.zmj.mvp.testsocket.bean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * 使用Realm数据库的实体类
 * @author Zmj
 * @date 2018/12/4
 */
public class KefuUser extends RealmObject {

    @PrimaryKey
    private String account;
    private String nickName;
    private String type;

    public KefuUser() {
    }

    public KefuUser(String account, String nickName, String type) {
        this.account = account;
        this.nickName = nickName;
        this.type = type;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
