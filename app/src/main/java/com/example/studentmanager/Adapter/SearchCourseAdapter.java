package com.example.studentmanager.Adapter;

import android.content.Context;
import android.widget.TextView;

import com.example.studentmanager.R;
import com.example.studentmanager.entity.Course;

import java.util.List;

public class SearchCourseAdapter extends BaseRecyclerViewAdapter<Course> {

    public SearchCourseAdapter(Context context, List<Course> data) {
        super(context, data, R.layout.item_course);
    }

    @Override
    public void onBindData(BaseRecyclerHolder holder, Course item, int position) {
        ((TextView)holder.getView(R.id.item_course_name)).setText(item.getCourseName());
        ((TextView)holder.getView(R.id.item_course_teacher)).setText(item.getTeacherName());
        ((TextView)holder.getView(R.id.item_course_desc)).setText(item.getDesc());
    }
}
