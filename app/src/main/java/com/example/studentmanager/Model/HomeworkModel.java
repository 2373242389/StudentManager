package com.example.studentmanager.Model;

import android.graphics.Point;
import android.util.Log;
import android.webkit.WebView;

import com.example.studentmanager.Utils.Constant;
import com.example.studentmanager.Utils.ToastUtils;
import com.example.studentmanager.entity.Course;
import com.example.studentmanager.entity.File;
import com.example.studentmanager.entity.HomeWork;
import com.example.studentmanager.entity.StuCommitHomework;
import com.example.studentmanager.entity.TArrHomework;
import com.example.studentmanager.entity.TCorrectHomework;
import com.example.studentmanager.entity.Teacher;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.Queue;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class HomeworkModel {
    private static HomeworkModel ourInstance = new HomeworkModel();

    public static HomeworkModel getInstance() {
        return ourInstance;
    }

    private HomeworkModel() {
    }

    public void AddHomework(String HomeworkDecs, List<BmobFile> files, String couseID, SaveListener listener) {
    }
    //通过课程id找到这个老师安排作业的对象
    public void CourseAllHomeWork(String courseID,FindListener findListener){
        BmobQuery<TArrHomework> query = new BmobQuery<>();
        query.addQueryKeys("courseID");
        query.findObjects(new FindListener<TArrHomework>() {
            @Override
            public void done(List<TArrHomework> list, BmobException e) {
           findListener.done(list,e);
            }
        });
    }

    //找到这个课程的所有作业
    public void findCourseAllHomework(TArrHomework tArrHomework,FindListener listener){
        BmobQuery<HomeWork> query = new BmobQuery<>();
        query.addWhereEqualTo("homeWork",new BmobPointer(tArrHomework));
        query.findObjects(new FindListener<HomeWork>() {
            @Override
            public void done(List<HomeWork> list, BmobException e) {
                listener.done(list,e);
            }
        });
    }

    //通过学号找到老师这个对象
    public void queryHomeworkTeacher(String teacherNo,FindListener listener){
        BmobQuery<Teacher> query = new BmobQuery();
        query.addWhereEqualTo("teacherNo",teacherNo);
        query.findObjects(new FindListener<Teacher>() {
            @Override
            public void done(List<Teacher> list, BmobException e) {
                listener.done(list,e);
            }
        });
    }
    //通过课程id找到这个课程对象
    public void queryHomeworkCourse(String courseID,FindListener listener){
        BmobQuery<Course> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId",courseID);
        query.findObjects(new FindListener<Course>() {
            @Override
            public void done(List<Course> list, BmobException e) {
           listener.done(list,e);
            }
        });
    }
    public void queryNoCommitHomework(String stuNo,FindListener<StuCommitHomework> listener){
        BmobQuery<StuCommitHomework> query = new BmobQuery<>();
        query.addWhereEqualTo("stuNo",stuNo);
        query.findObjects(new FindListener<StuCommitHomework>() {
            @Override
            public void done(List<StuCommitHomework> list, BmobException e) {
                listener.done(list,e);
            }
        });
    }
    public void byHomeworkIDFindHomework(String homeworkID,FindListener<HomeWork> listener){
        BmobQuery<HomeWork> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId",homeworkID);
        query.findObjects(new FindListener<HomeWork>() {
            @Override
            public void done(List<HomeWork> list, BmobException e) {
            listener.done(list,e);
            }
        });
    }
    public void ShowNoCommitHomework(String stuNo,FindListener<StuCommitHomework> listener){
        BmobQuery<StuCommitHomework> query = new BmobQuery<>();
        query.addWhereEqualTo("stuNo",stuNo);
        query.addWhereEqualTo("commit_flag",0);
        query.findObjects(new FindListener<StuCommitHomework>() {
            @Override
            public void done(List<StuCommitHomework> list, BmobException e) {
                listener.done(list,e);
            }
        });
    }
    public void DownloadHomeWork(String homeworkID,FindListener<HomeWork> listener){
        BmobQuery<HomeWork> query  = new BmobQuery<>();
        query.addWhereEqualTo("onjectId",homeworkID);
        query.findObjects(new FindListener<HomeWork>() {
            @Override
            public void done(List<HomeWork> list, BmobException e) {
                listener.done(list,e);
            }
        });
    }
    public void showHomeworkPicture(String homeID,FindListener<File> listener){
        BmobQuery<File> query = new BmobQuery<>();
        HomeWork homeWork = new HomeWork();
        homeWork.setObjectId(homeID);
        query.addWhereRelatedTo("file",new BmobPointer(homeWork));
        query.findObjects(new FindListener<File>() {
            @Override
            public void done(List<File> list, BmobException e) {
           if (e == null){
               listener.done(list,e);
           }
            }
        });
    }

    public void findAndCommithomework(String stuNo,String homeworkId, FindListener<StuCommitHomework> listener){
        BmobQuery<StuCommitHomework> query = new BmobQuery<>();
        query.addWhereEqualTo("homeWorkID",homeworkId);
        query.addWhereEqualTo("stuNo",stuNo);
        query.findObjects(new FindListener<StuCommitHomework>() {
            @Override
            public void done(List<StuCommitHomework> list, BmobException e) {
                listener.done(list,e);
            }
        });
    }
    public void findHomeworkById(String homeworkId,FindListener<HomeWork> listener){
        BmobQuery<HomeWork> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId",homeworkId);
        query.findObjects(new FindListener<HomeWork>() {
            @Override
            public void done(List<HomeWork> list, BmobException e) {
                listener.done(list,e);
            }
        });
    }
    public void findTeacherCreateHomework(String teacherNo,FindListener<TArrHomework> listener){
        BmobQuery<TArrHomework> query = new BmobQuery<>();
        query.addWhereEqualTo("teacherNo",teacherNo);
        query.findObjects(new FindListener<TArrHomework>() {
            @Override
            public void done(List<TArrHomework> list, BmobException e) {
                listener.done(list,e);
            }
        });
    }
    public void findHomeworkLevelById(String homeworkId, FindListener<TCorrectHomework> listener){
        BmobQuery<TCorrectHomework> query = new BmobQuery<>();
        query.addWhereEqualTo("homeworkId",homeworkId);
        query.findObjects(new FindListener<TCorrectHomework>() {
            @Override
            public void done(List<TCorrectHomework> list, BmobException e) {
                listener.done(list,e);
            }
        });
    }

    public void findAllTeacherCorrectHomework(FindListener<TCorrectHomework> listener){
        BmobQuery<TCorrectHomework> query = new BmobQuery<>();
        query.findObjects(new FindListener<TCorrectHomework>() {
            @Override
            public void done(List<TCorrectHomework> list, BmobException e) {
                listener.done(list,e);
            }
        });
    }
    public void findAllCommitHomework(String commitHomeworkId,String stuNo,FindListener<StuCommitHomework> listener){
        BmobQuery<StuCommitHomework> query = new BmobQuery<>();
        query.addWhereEqualTo("commitHomeWorkId",commitHomeworkId);
        query.addWhereEqualTo("stuNo",stuNo);
        query.addWhereEqualTo("commit_flag",1);
        query.findObjects(new FindListener<StuCommitHomework>() {
            @Override
            public void done(List<StuCommitHomework> list, BmobException e) {
                listener.done(list,e);
            }
        });
    }
    public void findNocommitCount(String stuNo,CountListener listener){
        BmobQuery<StuCommitHomework> query = new BmobQuery<>();
        query.addWhereEqualTo("stuNo",stuNo);
        query.addWhereEqualTo("commit_flag",0);
        query.count(StuCommitHomework.class, new CountListener() {
            @Override
            public void done(Integer count, BmobException e) {
                 listener.done(count,e);
            }
        });
    }
}
