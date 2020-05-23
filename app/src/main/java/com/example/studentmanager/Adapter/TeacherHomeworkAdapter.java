package com.example.studentmanager.Adapter;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.example.studentmanager.Model.CourseModel;
import com.example.studentmanager.Model.HomeworkModel;
import com.example.studentmanager.R;
import com.example.studentmanager.entity.Course;
import com.example.studentmanager.entity.HomeWork;
import com.example.studentmanager.entity.TArrHomework;
import com.orhanobut.logger.Logger;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class TeacherHomeworkAdapter extends BaseRecyclerViewAdapter<TArrHomework>{
    public TeacherHomeworkAdapter(Context context, List<TArrHomework> data) {
        super(context, data,R.layout.homework_item);
    }

    @Override
    public void onBindData(BaseRecyclerHolder holder, TArrHomework item, int position) {
        HomeworkModel.getInstance().findHomeworkById(item.getHomeworkId(), new FindListener<HomeWork>() {
            @Override
            public void done(List<HomeWork> list, BmobException e) {
                ((TextView)holder.getView(R.id.homework_teacher_name)).setText(list.get(0).getHomeworkDecs());
                CourseModel.getInstance().findCourseById(list.get(0).getCourseID(), new FindListener<Course>() {
                    @Override
                    public void done(List<Course> list, BmobException e) {
                        ((TextView)holder.getView(R.id.homework_course_name)).setText(list.get(0).getCourseName());
                    }
                });
            }
        });


    }
}
