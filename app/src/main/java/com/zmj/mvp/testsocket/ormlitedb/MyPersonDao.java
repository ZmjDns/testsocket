package com.zmj.mvp.testsocket.ormlitedb;

import android.content.Context;

import com.zmj.mvp.testsocket.bean.Person;

import java.sql.SQLException;

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * DitHub : https://github.com/ZmjDns
 * Time : 2019/2/18
 * Description :
 */
public class MyPersonDao extends BaseDao {

    public MyPersonDao(Context context) {
        super(context, Person.class);
    }

    public int addOnePerson(Person person){
        try {
            return baseDao.create(person);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
