package com.example.studentmanager.Adapter;

import android.content.Context;
import android.widget.TextView;

import com.example.studentmanager.Model.CourseModel;
import com.example.studentmanager.Model.HomeworkModel;
import com.example.studentmanager.R;
import com.example.studentmanager.entity.Course;
import com.example.studentmanager.entity.HomeWork;
import com.example.studentmanager.entity.StuCommitHomework;
import com.example.studentmanager.entity.TArrHomework;
import com.example.studentmanager.entity.Teacher;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class StuHomeWorkAdapter extends BaseRecyclerViewAdapter<StuCommitHomework>{


    public StuHomeWorkAdapter(Context context, List<StuCommitHomework> data) {
        super(context, data, R.layout.homework_item);
    }

    @Override
    public void onBindData(BaseRecyclerHolder holder, StuCommitHomework item, int position) {
        if (item.getCommit_flag() == 0){
            HomeworkModel.getInstance().findHomeworkById(item.getHomeWorkID(), new FindListener<HomeWork>() {
                @Override
                public void done(List<HomeWork> list, BmobException e) {
                   CourseModel.getInstance().findCourseById(list.get(0).getCourseID(), new FindListener<Course>() {
                       @Override
                       public void done(List<Course> list, BmobException e) {
                           ((TextView)holder.getView(R.id.homework_course_name)).setText(list.get(0).getCourseName());
                           ((TextView)holder.getView(R.id.homework_teacher_name)).setText(list.get(0).getTeacherName());
                       }
                   });
                }
            });
            HomeworkModel.getInstance().findHomeworkById(item.getHomeWorkID(), new FindListener<HomeWork>() {
                @Override
                public void done(List<HomeWork> list, BmobException e) {
                    ((TextView)holder.getView(R.id.homework_decs)).setText(list.get(0).getHomeworkDecs());
                }
            });
        }

    }
}
