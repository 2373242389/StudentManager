package com.example.studentmanager.Adapter;

import android.content.Context;
import android.widget.TextView;

import com.example.studentmanager.Model.CourseModel;
import com.example.studentmanager.Model.HomeworkModel;
import com.example.studentmanager.R;
import com.example.studentmanager.entity.Course;
import com.example.studentmanager.entity.HomeWork;
import com.example.studentmanager.entity.StuCommitHomework;
import com.example.studentmanager.entity.TCorrectHomework;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class HomeworklevelAdapter extends BaseRecyclerViewAdapter<StuCommitHomework> {

    public HomeworklevelAdapter(Context context, List<StuCommitHomework> data) {
        super(context, data, R.layout.homeworklevel_item);
    }

    @Override
    public void onBindData(BaseRecyclerHolder holder, StuCommitHomework item, int position) {
        HomeworkModel.getInstance().findHomeworkById(item.getCommitHomeWorkId(), new FindListener<HomeWork>() {
            @Override
            public void done(List<HomeWork> homeWork, BmobException e) {
                ((TextView)holder.getView(R.id.homeworkDecs)).setText(homeWork.get(0).getHomeworkDecs());
                CourseModel.getInstance().findCourseById(homeWork.get(0).getCourseID(), new FindListener<Course>() {
                    @Override
                    public void done(List<Course> courses, BmobException e) {
                        ((TextView)holder.getView(R.id.CourseName)).setText(courses.get(0).getCourseName());
                    }
                });
            }
        });
        HomeworkModel.getInstance().findHomeworkLevelById(item.getCommitHomeWorkId(), new FindListener<TCorrectHomework>() {
            @Override
            public void done(List<TCorrectHomework> list, BmobException e) {
                ((TextView)holder.getView(R.id.level)).setText(list.get(0).getLevel());
            }
        });

    }
}
