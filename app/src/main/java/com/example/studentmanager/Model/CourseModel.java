package com.example.studentmanager.Model;

import com.example.studentmanager.Model.i.QueryUserListener;
import com.example.studentmanager.Utils.Constant;
import com.example.studentmanager.entity.Course;
import com.example.studentmanager.entity.StuJoinCourse;
import com.example.studentmanager.entity.Student;
import com.example.studentmanager.entity.Teacher;
import com.example.studentmanager.entity.TeacherCreateCourse;
import com.example.studentmanager.entity.TstartandEndSign;

import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class CourseModel {
    private static CourseModel ourInstance = new CourseModel();
    public static CourseModel getInstance(){return ourInstance;}
    private CourseModel(){}


    public void findCourseById(String courseId,FindListener<Course> listener){
        BmobQuery<Course> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId",courseId);
        query.findObjects(new FindListener<Course>() {
            @Override
            public void done(List<Course> list, BmobException e) {
           listener.done(list,e);
            }
        });
    }

    public void queryCourseStudentNumber(String courseId,FindListener<StuJoinCourse> listener){
        BmobQuery<StuJoinCourse> query = new BmobQuery<>();
        query.addWhereEqualTo("CourseId",courseId);
        query.findObjects(new FindListener<StuJoinCourse>() {
            @Override
            public void done(List<StuJoinCourse> list, BmobException e) {
           listener.done(list,e);
            }
        });
    }
    public void queryTeacherAllCourse(FindListener<Course> listener){
        BmobQuery<Course> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereRelatedTo("createCourse",new BmobPointer(Constant.teacher));
        bmobQuery.findObjects(new FindListener<Course>() {
            @Override
            public void done(List<Course> list, BmobException e) {
                if (e == null) {
                    listener.done(list, e);
                }
            }
        });
    }
    public void queryStudentJoinCourse(FindListener<Course> listener){
        BmobQuery<Course> query = new BmobQuery<>();
        query.addWhereEqualTo("course",new BmobPointer(Constant.student));
        query.findObjects(new FindListener<Course>() {
            @Override
            public void done(List<Course> list, BmobException e) {
                listener.done(list,e);
            }
        });
    }
    public void queryCourse(String courseId,FindListener<Course> listener){
        BmobQuery<Course> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId",courseId);
        query.findObjects(new FindListener<Course>() {
            @Override
            public void done(List<Course> list, BmobException e) {
                listener.done(list,e);
            }
        });
    }
    public void queryExistsCourse(String courseId,FindListener<TstartandEndSign> listener){
        BmobQuery<TstartandEndSign> query = new BmobQuery<>();
        query.addWhereEqualTo("courseId",courseId);
        query.findObjects(new FindListener<TstartandEndSign>() {
            @Override
            public void done(List<TstartandEndSign> list, BmobException e) {
           listener.done(list,e);
            }
        });
    }

    public void queryTeacherAllCourse1(FindListener<TeacherCreateCourse> listener){
            BmobQuery<TeacherCreateCourse> query = new BmobQuery<>();
            query.addWhereEqualTo("teacherNo",Constant.teacher.getTeacherNo());
            query.findObjects(new FindListener<TeacherCreateCourse>() {
                @Override
                public void done(List<TeacherCreateCourse> list, BmobException e) {
                    listener.done(list,e);
                }
            });
    }
    public void queryStudentAllCourse(FindListener<StuJoinCourse> listener){
        BmobQuery<StuJoinCourse> query = new BmobQuery<>();
        query.addWhereEqualTo("stuNo",Constant.student.getStuno());
        query.findObjects(new FindListener<StuJoinCourse>() {
            @Override
            public void done(List<StuJoinCourse> list, BmobException e) {
            listener.done(list,e);
            }
        });
    }

    public void judgeExistCourse(String courseId,FindListener<StuJoinCourse> listener){
        BmobQuery<StuJoinCourse> query = new BmobQuery<>();
        query.addWhereEqualTo("CourseId",courseId);
        query.findObjects(new FindListener<StuJoinCourse>() {
            @Override
            public void done(List<StuJoinCourse> list, BmobException e) {
                listener.done(list,e);
            }
        });
    }
    public void queryJoinCourse(String stuNo,FindListener<StuJoinCourse> listener){
        BmobQuery<StuJoinCourse> query = new BmobQuery<>();
        query.addWhereEqualTo("stuNo",stuNo);
        query.findObjects(new FindListener<StuJoinCourse>() {
            @Override
            public void done(List<StuJoinCourse> list, BmobException e) {
                listener.done(list,e);
            }
        });
    }
}
