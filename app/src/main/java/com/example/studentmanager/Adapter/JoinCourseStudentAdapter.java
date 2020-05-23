package com.example.studentmanager.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.studentmanager.Model.StudentModel;
import com.example.studentmanager.R;
import com.example.studentmanager.entity.StuJoinCourse;
import com.example.studentmanager.entity.Student;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class JoinCourseStudentAdapter extends  BaseRecyclerViewAdapter<StuJoinCourse>{

    private JoinCourseStudentAdapter.OnDeleteClickLister mDeleteClickListener;
    public JoinCourseStudentAdapter(Context context, List<StuJoinCourse> data) {
        super(context, data, R.layout.student);
    }

    @Override
    public void onBindData(BaseRecyclerHolder holder, StuJoinCourse item, int position) {
        View view = holder.getView(R.id.tv_delete);
        view.setTag(position);
        if (!view.hasOnClickListeners()) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDeleteClickListener != null) {
                        mDeleteClickListener.onDeleteClick(v, (Integer) v.getTag());
                    }
                }
            });
        }
        StudentModel.getInstance().findStudentById(item.getStuNo(), new FindListener<Student>() {
            @Override
            public void done(List<Student> list, BmobException e) {
                ((TextView)holder.getView(R.id.stu_name)).setText(list.get(0).getName());
                ((TextView)holder.getView(R.id.stu_no)).setText(list.get(0).getStuno());
            }
        });


    }
    public void setOnDeleteClickListener(JoinCourseStudentAdapter.OnDeleteClickLister listener) {
        this.mDeleteClickListener = listener;
    }
}
