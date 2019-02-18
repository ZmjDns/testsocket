package com.zmj.mvp.testsocket.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Author : Zmj
 * Blog : https://blog.csdn.net/Zmj_Dns
 * DitHub : https://github.com/ZmjDns
 * Time : 2019/2/15
 * Description :创建学生表
 */
@DatabaseTable//(tableName = "t_student")//可以通过(tableName = "user")修改表名
public class Student {
    @DatabaseField//只有通过添加这个注解，才能把此属性添加到表中的字段
    private String name;

    @DatabaseField(columnName = "id",generatedId = true)//generateedId = true 表示自增长的主键，columnName = “id”（必须要有，否则报错）
    private int id;

    @DatabaseField
    private String className;

    @DatabaseField
    private int userId;

    @DatabaseField
    private String sex;

    public Student() {
    }

    public Student(String name, int id, String className, int userId, String sex) {
        this.name = name;
        this.id = id;
        this.className = className;
        this.userId = userId;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ",id=" + id +
                ",classname='" + className + '\'' +
                ",userId='" + userId + '\'' +
                ",sex='" + sex + '\'' +
                '}';
    }
}
