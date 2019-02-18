package com.zmj.mvp.testsocket.ormlitedb;

import android.content.Context;

import com.zmj.mvp.testsocket.bean.Student;

import java.sql.SQLException;
import java.util.List;

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * DitHub : https://github.com/ZmjDns
 * Time : 2019/2/18
 * Description :
 */
public class MyStudentDao extends BaseDao{

    public MyStudentDao(Context context) {
        super(context, Student.class);
    }

    public int addOneStud(Student student){
        try {
            return baseDao.create(student);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int updateStudent(Student student){
        try {
            return baseDao.update(student);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public List<Student> findAllStudent(){
        try {
            return baseDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int deleteStudent(int id){
        try {
            return baseDao.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
