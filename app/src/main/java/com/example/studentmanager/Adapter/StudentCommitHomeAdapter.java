package com.example.studentmanager.Adapter;

import android.content.Context;
import android.widget.TextView;

import com.example.studentmanager.Model.CourseModel;
import com.example.studentmanager.Model.HomeworkModel;
import com.example.studentmanager.Model.StudentModel;
import com.example.studentmanager.R;
import com.example.studentmanager.entity.Course;
import com.example.studentmanager.entity.HomeWork;
import com.example.studentmanager.entity.StuCommitHomework;
import com.example.studentmanager.entity.Student;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class StudentCommitHomeAdapter extends BaseRecyclerViewAdapter<StuCommitHomework> {



    public StudentCommitHomeAdapter(Context context, List<StuCommitHomework> data) {
        super(context, data,R.layout.commit_homework_student_item);
    }

    @Override
    public void onBindData(BaseRecyclerHolder holder, StuCommitHomework item, int position) {
        ((TextView)holder.getView(R.id.item_stuNo)).setText(item.getStuNo());
        StudentModel.getInstance().findStudentById(item.getStuNo(), new FindListener<Student>() {
            @Override
            public void done(List<Student> list, BmobException e) {
                ((TextView)holder.getView(R.id.item_stuName)).setText(list.get(0).getName());
            }
        });
        HomeworkModel.getInstance().findHomeworkById(item.getHomeWorkID(), new FindListener<HomeWork>() {
            @Override
            public void done(List<HomeWork> list, BmobException e) {
                ((TextView)holder.getView(R.id.item_homeworkDecs)).setText(list.get(0).getHomeworkDecs());
                CourseModel.getInstance().findCourseById(list.get(0).getCourseID(), new FindListener<Course>() {
                    @Override
                    public void done(List<Course> list, BmobException e) {
                        ((TextView)holder.getView(R.id.item_course)).setText(list.get(0).getCourseName());
                    }
                });
            }
        });
    }
}
