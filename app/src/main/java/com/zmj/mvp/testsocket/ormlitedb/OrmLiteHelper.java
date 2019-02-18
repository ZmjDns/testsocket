package com.zmj.mvp.testsocket.ormlitedb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.zmj.mvp.testsocket.bean.Person;
import com.zmj.mvp.testsocket.bean.Student;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * DitHub : https://github.com/ZmjDns
 * Time : 2019/2/15
 * Description :https://www.cnblogs.com/zhangqie/p/7255471.html
 */
public class OrmLiteHelper extends OrmLiteSqliteOpenHelper {

    public static final String DB_NAME = "TEST_ORMLITE";//数据库名称
    public static final int DB_VERSION = 1;

    private static OrmLiteHelper ormLiteHelper;

    //用来存放dao的集合
    //private Map<String,Dao> mapDaos = new HashMap<String, Dao>();

    private OrmLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //单例模式获取数据库实例
    public static OrmLiteHelper getOrmLiteHelper(Context context){
        if (ormLiteHelper == null){
            synchronized (OrmLiteHelper.class){
                if (ormLiteHelper == null){
                    ormLiteHelper = new OrmLiteHelper(context.getApplicationContext());
                }
            }
        }
        return ormLiteHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Person.class);
            TableUtils.createTable(connectionSource,Student.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource,Student.class,true);
            onCreate(database,connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //获取dao实例
//    public synchronized Dao getDao(Class clas) throws SQLException{
//        Dao dao = null;
//        String className = clas.getSimpleName();//获取类名
//        //如果class存在Map中就从Map中取
//        if (mapDaos.containsKey(clas)){//判断是否存在map中
//            dao = mapDaos.get(className);
//        }else {//如果不存在就调用父类的 getDao（）方法获得dao，并存在mapDao中便于下次调用
//            dao = (Dao) super.getDao(clas);
//            mapDaos.put(className,dao);
//        }
//        return dao;
//    }

    @Override
    public void close() {
        super.close();

    }
}
