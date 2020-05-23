package com.example.studentmanager.Model;

import com.example.studentmanager.entity.Course;
import com.example.studentmanager.entity.TstartandEndSign;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class SignModel {

    private static SignModel ourInstance = new SignModel();
    public static SignModel getInstance(){return ourInstance;}
    private SignModel(){}


    public void judgeSignFlag(Course course, final FindListener<TstartandEndSign> listener){
        BmobQuery<TstartandEndSign> query = new BmobQuery<>();
        query.addWhereEqualTo("teacherNo",course.getTeacherNo());
        query.addWhereEqualTo("course",course.getObjectId());
        query.findObjects(new FindListener<TstartandEndSign>() {
            @Override
            public void done(List<TstartandEndSign> list, BmobException e) {
                listener.done(list,e);
            }
        });
    }
    public void findExistSign(String courseId,FindListener<TstartandEndSign> listener){
        BmobQuery<TstartandEndSign> query = new BmobQuery<>();
        query.addWhereEqualTo("courseId",courseId);
        query.findObjects(new FindListener<TstartandEndSign>() {
            @Override
            public void done(List<TstartandEndSign> list, BmobException e) {
            listener.done(list,e);
            }
        });
    }
}
