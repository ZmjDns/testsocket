package com.zmj.mvp.testsocket.ormlitedb;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.zmj.mvp.testsocket.bean.Student;

import java.util.List;

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * DitHub : https://github.com/ZmjDns
 * Time : 2019/2/15
 * Description :
 */
public class StudentDao {
    private Context context;
    private Dao<Student,Integer> studentDaoOpen;
    private OrmLiteHelper helper;

    public StudentDao(Context context){
        this.context = context;

        helper = OrmLiteHelper.getOrmLiteHelper(context);
        try {
            studentDaoOpen = helper.getDao(Student.class);
        }catch (Exception  e){
            e.printStackTrace();
        }
    }

    //增加一条数据
    public int addStudent(Student student){
        try {
            return studentDaoOpen.create(student);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    //删除一条数据
    public int deleteStudent(int id){
        try {
            return studentDaoOpen.deleteById(id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    //修改
    public int updateStudent(Student student){
        try {
            studentDaoOpen.update(student);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    //查询
    public List<Student> queryAllStudent(){
        try {
            return studentDaoOpen.queryForAll();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
