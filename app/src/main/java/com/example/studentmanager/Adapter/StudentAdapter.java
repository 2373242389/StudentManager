package com.example.studentmanager.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanager.R;
import com.example.studentmanager.entity.Student;

import java.util.List;

public class StudentAdapter extends BaseRecyclerViewAdapter<Student> {

    private BaseRecyclerViewAdapter.OnDeleteClickLister mDeleteClickListener;

    public StudentAdapter(Context context, List<Student> data) {
        super(context, data, R.layout.student);
    }

    @Override
    public void onBindData(BaseRecyclerHolder holder, Student stu, int position) {
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
        ((TextView)holder.getView(R.id.stu_name)).setText(stu.getName());
        ((TextView)holder.getView(R.id.stu_no)).setText(stu.getStuno());

    }
    public void setOnDeleteClickListener(BaseRecyclerViewAdapter.OnDeleteClickLister listener) {
        this.mDeleteClickListener = listener;
    }
}
