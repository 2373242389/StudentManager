package com.example.studentmanager.Adapter;

import android.content.Context;
import android.widget.TextView;

import com.example.studentmanager.R;
import com.example.studentmanager.entity.Teacher;
import com.google.gson.internal.$Gson$Preconditions;

import java.util.List;

public class StudentConcrenAdapter extends BaseRecyclerViewAdapter<Teacher> {
    public StudentConcrenAdapter(Context context, List<Teacher> data) {
        super(context, data, R.layout.concern_item);
    }

    @Override
    public void onBindData(BaseRecyclerHolder holder, Teacher teacher, int position) {
        ((TextView)holder.getView(R.id.item_No)).setText(teacher.getTeacherNo());
        ((TextView)holder.getView(R.id.item_Name)).setText(teacher.getName());
        ((TextView)holder.getView(R.id.item_Department)).setText(teacher.getDepartment());
    }
}
