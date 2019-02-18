package com.zmj.mvp.testsocket.ormlitedb;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.table.TableUtils;

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * DitHub : https://github.com/ZmjDns
 * Time : 2019/2/18
 * Description :dao基础类
 */
public class BaseDao<T> {

    protected Context context;
    protected Dao<T,Integer> baseDao;

    public BaseDao(Context context, Class<T> tClass) {
        this.context = context;
        initDao(tClass);
    }

    private void initDao(Class<T> tClass) {
        OrmLiteHelper helper = OrmLiteHelper.getOrmLiteHelper(context);

        try {
            baseDao = helper.getDao(tClass);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
