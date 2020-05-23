package com.example.studentmanager.Model;

import android.content.Intent;
import android.widget.Toast;

import com.example.studentmanager.Model.i.QueryUserListener;
import com.example.studentmanager.Model.i.UpdateCacheListener;
import com.example.studentmanager.Utils.Constant;
import com.example.studentmanager.Utils.ToastUtils;
import com.example.studentmanager.activity.teacher.TeacherContactActivity;
import com.example.studentmanager.entity.Course;
import com.example.studentmanager.entity.Student;
import com.example.studentmanager.entity.Teacher;
import com.example.studentmanager.entity.TeacherCreateCourse;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.Queue;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMConversation;
import cn.bmob.newim.bean.BmobIMMessage;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.event.MessageEvent;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

public class TeacherModel{
        private Intent intent;
        Teacher teacher = Constant.teacher;
//        BmobIMUserInfo info = new BmobIMUserInfo(teacher.getObjectId(),teacher.getName(),null);
        private static TeacherModel ourInstance = new TeacherModel();
        public static TeacherModel getInstance(){return ourInstance;}
        private TeacherModel(){}

        //查询所有老师
        public void queryTeachers(final FindListener<Teacher> listener){
            BmobQuery<Teacher> query = new BmobQuery<>();
            query.addQueryKeys("objectId");
            query.order("createAt");
            query.findObjects(new FindListener<Teacher>() {
                @Override
                public void done(List<Teacher> list, BmobException e) {
                    listener.done(list,e);
                }
            });
        }
        public void queryTeachersCreateCourse(Teacher teacher, final FindListener<Course> listener){
            BmobQuery<Course> query = new BmobQuery<>();
            query.addWhereRelatedTo("createCourse",new BmobPointer(teacher));
            query.findObjects(new FindListener<Course>() {
                @Override
                public void done(List<Course> list, BmobException e) {
                    if (e == null) {
                        listener.done(list, e);
                    }else{
                        listener.done(null,new BmobException("未创建课程"));
                    }
                }
            });
        }

    public void queryUserInfo(String stuNo, final QueryUserListener listener){
        BmobQuery<Student> query = new BmobQuery<>();
        query.addWhereEqualTo("stuno", stuNo);
        query.findObjects(new FindListener<Student>() {
            @Override
            public void done(List<Student> list, BmobException e) {
                if (list != null && list.size() > 0) {
                    listener.internalDone(list.get(0), null);
                } else {
                    listener.internalDone(new BmobException(000, "查无此人"));
                }

            }
        });
    }

        public void queryJoinStudent(Teacher teacher,final FindListener<Student> listener){
            BmobQuery<Student> query = new BmobQuery<>();
            query.addWhereRelatedTo("student",new BmobPointer(teacher));
            query.findObjects(new FindListener<Student>() {
                @Override
                public void done(List<Student> list, BmobException e) {
                    if (e == null){
                        Logger.i(String.valueOf(list.size()));
                        listener.done(list,e);
                    }
                }
            });
        }

        public void queryCreateCourse(FindListener<TeacherCreateCourse> listener){
            BmobQuery<TeacherCreateCourse> query = new BmobQuery<>();
            query.addWhereEqualTo("teacherNo",Constant.teacher.getTeacherNo());
            query.findObjects(new FindListener<TeacherCreateCourse>() {
                @Override
                public void done(List<TeacherCreateCourse> list, BmobException e) {
                 listener.done(list,e);
                }
            });
        }

        public void queryCourseTeacher(String courseId, FindListener<TeacherCreateCourse> listener){
            BmobQuery<TeacherCreateCourse> query = new BmobQuery<>();
            query.addWhereEqualTo("courseId",courseId);
            query.findObjects(new FindListener<TeacherCreateCourse>() {
                @Override
                public void done(List<TeacherCreateCourse> list, BmobException e) {
                listener.done(list,e);
                }
            });
        }
        public void findTeacherById(String teacherNo, FindListener<Teacher> listener){
            BmobQuery<Teacher> query = new BmobQuery<>();
            query.addWhereEqualTo("teacherNo",teacherNo);
            query.findObjects(new FindListener<Teacher>() {
                @Override
                public void done(List<Teacher> list, BmobException e) {
               listener.done(list, e);
                }
            });
        }
        public void queryTeacherByPhone(String phone,FindListener<Teacher> listener){
            BmobQuery<Teacher> query = new BmobQuery<>();
            query.addWhereEqualTo("phone",phone);
            query.findObjects(new FindListener<Teacher>() {
                @Override
                public void done(List<Teacher> list, BmobException e) {
               listener.done(list,e);
                }
            });
        }
}
