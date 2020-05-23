package com.example.studentmanager.Adapter;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanager.Model.CourseModel;
import com.example.studentmanager.R;
import com.example.studentmanager.entity.Course;
import com.example.studentmanager.entity.StuJoinCourse;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class CourseAdapter extends BaseRecyclerViewAdapter<StuJoinCourse>{
    public CourseAdapter(Context context, List<StuJoinCourse> data) {
        super(context, data, R.layout.item_course);
    }

    @Override
    public void onBindData(BaseRecyclerHolder holder, StuJoinCourse stuJoinCourse, int position) {
        CourseModel.getInstance().queryCourse(stuJoinCourse.getCourseId(), new FindListener<Course>() {
            @Override
            public void done(List<Course> list, BmobException e) {
                ((TextView)holder.getView(R.id.item_course_name)).setText(list.get(0).getCourseName());
                ((TextView)holder.getView(R.id.item_course_teacher)).setText(list.get(0).getTeacherName());
                ((TextView)holder.getView(R.id.item_course_desc)).setText(list.get(0).getDesc());
            }
        });


    }
}
