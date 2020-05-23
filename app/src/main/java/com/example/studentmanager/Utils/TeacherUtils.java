package com.example.studentmanager.Utils;

import com.example.studentmanager.entity.Teacher;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

public class TeacherUtils {

    public static  void queryTeacher(String teacherNo){
        String sql = "select * from Teacher where teacherNo = ?";
        BmobQuery<Teacher> teacherBmobQuery = new BmobQuery<Teacher>();
       teacherBmobQuery.doSQLQuery(sql, new SQLQueryListener<Teacher>() {
           @Override
           public void done(BmobQueryResult<Teacher> bmobQueryResult, BmobException e) {
               if (e == null){
                   List<Teacher> list = bmobQueryResult.getResults();
                   teacher.getTeacher(list.get(0));
               }
           }
       },teacherNo);


    }
    private static ITeacher teacher;

    public static void setITeacherListener(ITeacher iTeacher){
        teacher = iTeacher;
    }

    public  interface ITeacher{
        void getTeacher(Teacher teacher);
    }
}
