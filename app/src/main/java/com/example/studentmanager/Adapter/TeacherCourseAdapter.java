package com.example.studentmanager.Adapter;

import android.content.Context;
import android.widget.TextView;

import com.example.studentmanager.Model.CourseModel;
import com.example.studentmanager.R;
import com.example.studentmanager.Utils.ToastUtils;
import com.example.studentmanager.entity.Course;
import com.example.studentmanager.entity.StuJoinCourse;
import com.example.studentmanager.entity.TeacherCreateCourse;
import com.orhanobut.logger.Logger;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class TeacherCourseAdapter extends BaseRecyclerViewAdapter<TeacherCreateCourse>{

    public TeacherCourseAdapter(Context context, List<TeacherCreateCourse> data) {
        super(context, data, R.layout.teacher_course_item);
    }

    @Override
    public void onBindData(BaseRecyclerHolder holder, TeacherCreateCourse teacherCreateCourse, int position) {
        CourseModel.getInstance().queryCourse(teacherCreateCourse.getCourseId(), new FindListener<Course>() {
            @Override
            public void done(List<Course> list, BmobException e) {
                ((TextView)holder.getView(R.id.teacher_course_name)).setText(list.get(0).getCourseName());
                ((TextView)holder.getView(R.id.teacher_course_desc)).setText(list.get(0).getDesc());
               ((TextView)holder.getView(R.id.renshu)).setText(String.valueOf(list.get(0).getStuCount()));
            }
        });
        CourseModel.getInstance().queryCourseStudentNumber(teacherCreateCourse.getCourseId(), new FindListener<StuJoinCourse>() {
            @Override
            public void done(List<StuJoinCourse> list, BmobException e) {
                if (list.size() >  0) {
                    ((TextView) holder.getView(R.id.stuTureNumber)).setText(String.valueOf(list.size()));
                }else {
                    ((TextView) holder.getView(R.id.stuTureNumber)).setText("0");
                }
            }
        });
    }
}
