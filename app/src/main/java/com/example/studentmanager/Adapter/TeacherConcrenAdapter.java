package com.example.studentmanager.Adapter;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanager.Model.StudentModel;
import com.example.studentmanager.R;
import com.example.studentmanager.entity.StuJoinCourse;
import com.example.studentmanager.entity.Student;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class TeacherConcrenAdapter extends BaseRecyclerViewAdapter<StuJoinCourse>{

    public TeacherConcrenAdapter(Context context, List data) {
        super(context, data, R.layout.concern_item);
    }

    @Override
    public void onBindData(BaseRecyclerHolder holder, StuJoinCourse item, int position) {
        StudentModel.getInstance().findStudentById(item.getStuNo(), new FindListener<Student>() {
            @Override
            public void done(List<Student> list, BmobException e) {
                ((TextView)holder.getView(R.id.item_No)).setText(list.get(0).getStuno());
                ((TextView)holder.getView(R.id.item_Name)).setText(list.get(0).getName());
                ((TextView)holder.getView(R.id.item_Department)).setText(list.get(0).getDepartment());
            }
        });


    }
}
