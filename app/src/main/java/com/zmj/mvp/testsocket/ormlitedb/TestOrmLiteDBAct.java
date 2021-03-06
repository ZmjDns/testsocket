package com.zmj.mvp.testsocket.ormlitedb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.zmj.mvp.testsocket.R;
import com.zmj.mvp.testsocket.bean.Person;
import com.zmj.mvp.testsocket.bean.Student;

import java.util.List;

public class TestOrmLiteDBAct extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    private StudentDao studentDao;
    private PersonDao personDao;

    private MyStudentDao myStudentDao;
    private MyPersonDao myPersonDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_orm_lite_db);

//        studentDao = new StudentDao(this);
//        personDao = new PersonDao(this);
        myStudentDao = new MyStudentDao(this);
        myPersonDao = new MyPersonDao(this);
    }

    public void addOneStu(View view){
//        Student student = new Student("aa",1,"firstClass",001,"男");
//        int result = studentDao.addStudent(student);
//        Log.d(TAG, "addOneStu: result:" + result);
//        Toast.makeText(this, "插入一个成功:" + result,Toast.LENGTH_SHORT).show();

//        Person person = new Person("aaa",18);
//        int result = personDao.addOnePerson(person);
//        Log.d(TAG, "addOneStu: result:" + result);
//        Toast.makeText(this, "插入一个成功:" + result,Toast.LENGTH_SHORT).show();

        Student student = new Student("new1",10,"secondClass",101,"女");
        int result = myStudentDao.addOneStud(student);

        Log.d(TAG, "addOneStu: result:" + result);
        Toast.makeText(this, "插入一个成功:" + result,Toast.LENGTH_SHORT).show();

    }

    public void addOTwoStu(View view){
//        Student student = new Student("bb",2,"firstClass",002,"女");
//        int result1 = studentDao.addStudent(student);
//        Student student2 = new Student("cc",3,"secondClass",001,"男");
//        int result2 = studentDao.addStudent(student2);

        Person person = new Person("Anna",18);
        int result1 = myPersonDao.addOnePerson(person);
        Person person1 = new Person("Dave",19);
        int result2 = myPersonDao.addOnePerson(person1);

        Log.d(TAG, "addOneStu: result1:" + result1 + "   result2:" + result2);
        Toast.makeText(this, "插入两个成功:" + result2,Toast.LENGTH_SHORT).show();
    }

    public void updateStu(View view){
        Student student = new Student("bb",2,"secondClass",002,"女");
        myStudentDao.updateStudent(student);
        Toast.makeText(this, "更新成功",Toast.LENGTH_SHORT).show();
    }

    public void queryAllStu(View view){
        List<Student> students =  myStudentDao.findAllStudent();

        String studentsStr = "";

        for (int i = 0; i < students.size(); i++){
            Student student = students.get(i);
            studentsStr += student.getId() + "," + student.getName() + "," + student.getClassName() + "," + student.getUserId() + "," + student.getSex() + "/\n";
        }

        Toast.makeText(this, studentsStr,Toast.LENGTH_SHORT).show();
    }

    public void delOneStu(View view){
        int result = myStudentDao.deleteStudent(3);

        Log.d(TAG, "addOneStu: result:" + result);
        Toast.makeText(this, "删除一个成功:" + result,Toast.LENGTH_SHORT).show();

    }

}
