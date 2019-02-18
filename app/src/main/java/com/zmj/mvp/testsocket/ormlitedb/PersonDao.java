package com.zmj.mvp.testsocket.ormlitedb;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.zmj.mvp.testsocket.bean.Person;

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * DitHub : https://github.com/ZmjDns
 * Time : 2019/2/18
 * Description :
 */
public class PersonDao {

    private Context context;

    private Dao<Person,Integer> personDao;

    public PersonDao(Context context) {
        this.context = context;
        init();
    }

    private void init(){
        OrmLiteHelper helper = OrmLiteHelper.getOrmLiteHelper(context.getApplicationContext());
        try {
            personDao = helper.getDao(Person.class);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public int addOnePerson(Person person){
        try {
            int code = personDao.create(person);
            return code;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

}
