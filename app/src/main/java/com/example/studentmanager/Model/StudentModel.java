package com.example.studentmanager.Model;

import android.webkit.WebView;

import com.example.studentmanager.Utils.Constant;
import com.example.studentmanager.entity.Course;
import com.example.studentmanager.entity.HomeWork;
import com.example.studentmanager.entity.StuCommitHomework;
import com.example.studentmanager.entity.StuJoinCourse;
import com.example.studentmanager.entity.StuSign;
import com.example.studentmanager.entity.Student;
import com.example.studentmanager.entity.Teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;

public class StudentModel {

    private static StudentModel ourInstance = new StudentModel();
    public static StudentModel getInstance(){return ourInstance;}
    private StudentModel(){}


    public void queryJoinCourseTeacher(Student student,final FindListener<Teacher> listener){
        BmobQuery<Teacher> query = new BmobQuery<>();
        query.addWhereRelatedTo("teacher",new BmobPointer(student));
        query.findObjects(new FindListener<Teacher>() {
            @Override
            public void done(List<Teacher> list, BmobException e) {
                if (e == null){
                    listener.done(list,e);
                }
            }
        });
    }

    public void findStudentById(String stuNo,final FindListener<Student> listener){
        BmobQuery<Student> query = new BmobQuery<>();
        query.addWhereEqualTo("stuno",stuNo);
        query.findObjects(new FindListener<Student>() {
            @Override
            public void done(List<Student> list, BmobException e) {
                listener.done(list,e);
            }
        });
    }


    public void FindCommitStudentByHomeworkId(String homeworkId,FindListener<StuCommitHomework> listener){
                BmobQuery<StuCommitHomework> query = new BmobQuery<>();
                query.addWhereEqualTo("homeWorkID",homeworkId);
                query.addWhereEqualTo("commit_flag",1);
                query.findObjects(new FindListener<StuCommitHomework>() {
                    @Override
                    public void done(List<StuCommitHomework> list, BmobException e) {
                        listener.done(list,e);
                    }
                });
    }

    public void queryCourseStudent(String courseId,final FindListener<StuJoinCourse> listener){
        BmobQuery<StuJoinCourse> query = new BmobQuery<>();
        query.addWhereEqualTo("CourseId",courseId);
        query.findObjects(new FindListener<StuJoinCourse>() {
            @Override
            public void done(List<StuJoinCourse> list, BmobException e) {
                if (e==null){
                    listener.done(list,e);
                }
            }
        });
    }
    public void findMyselfInfo(String stuNo,FindListener<Student> listener){
        BmobQuery<Student> query = new BmobQuery<>();
        query.addWhereEqualTo("stuNo",stuNo);
        query.findObjects(new FindListener<Student>() {
            @Override
            public void done(List<Student> list, BmobException e) {
           listener.done(list,e);
            }
        });
    }
    public void findStudentAllCourse(FindListener<StuJoinCourse> listener){
        BmobQuery<StuJoinCourse> query = new BmobQuery<>();
        query.addWhereEqualTo("stuNo",Constant.student.getStuno());
        query.findObjects(new FindListener<StuJoinCourse>() {
            @Override
            public void done(List<StuJoinCourse> list, BmobException e) {
                listener.done(list,e);
            }
        });
    }
    public void findSignStudent(String courseId, BmobDate startTime, BmobDate endTime, FindListener<StuSign> listener){
        BmobQuery<StuSign> start = new BmobQuery<>();
        start.addWhereGreaterThanOrEqualTo("createdAt",startTime);
        BmobQuery<StuSign> end = new BmobQuery<>();
        end.addWhereLessThanOrEqualTo("createdAt",endTime);
        List<BmobQuery<StuSign>> queries = new ArrayList<>();
        queries.add(start);
        queries.add(end);

        BmobQuery<StuSign> query = new BmobQuery<>();
        query.addWhereEqualTo("courseId",courseId);
        query.and(queries);
        query.findObjects(new FindListener<StuSign>() {
            @Override
            public void done(List<StuSign> list, BmobException e) {
           listener.done(list,e);
            }
        });
    }
    public void queryCourseAllStudent(String courseId,FindListener<StuJoinCourse> listener){
        BmobQuery<StuJoinCourse> query = new BmobQuery<>();
        query.addWhereEqualTo("CourseId",courseId);
        query.findObjects(new FindListener<StuJoinCourse>() {
            @Override
            public void done(List<StuJoinCourse> list, BmobException e) {
                listener.done(list,e);
            }
        });
    }
    public void queryStudentByPhone(String phone, FindListener<Student> listener){
        BmobQuery<Student> query = new BmobQuery<>();
        query.addWhereEqualTo("phone",phone);
        query.findObjects(new FindListener<Student>() {
            @Override
            public void done(List<Student> list, BmobException e) {
           listener.done(list,e);
            }
        });
    }
}
