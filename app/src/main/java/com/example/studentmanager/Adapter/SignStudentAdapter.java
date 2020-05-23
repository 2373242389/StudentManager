package com.example.studentmanager.Adapter;

import android.content.Context;
import android.widget.TextView;

import com.example.studentmanager.Model.StudentModel;
import com.example.studentmanager.R;
import com.example.studentmanager.entity.StuSign;
import com.example.studentmanager.entity.Student;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class SignStudentAdapter extends BaseRecyclerViewAdapter<StuSign> {

    public SignStudentAdapter(Context context, List<StuSign> data) {
        super(context, data, R.layout.sign_stu_item);
    }

    @Override
    public void onBindData(BaseRecyclerHolder holder, StuSign item, int position) {
        StudentModel.getInstance().findStudentById(item.getStuNo(), new FindListener<Student>() {
            @Override
            public void done(List<Student> list, BmobException e) {
                ((TextView)holder.getView(R.id.sign_stu_no)).setText(list.get(0).getStuno());
                ((TextView)holder.getView(R.id.sign_stu_name)).setText(list.get(0).getName());
            }
        });
        ((TextView)holder.getView(R.id.sign_place)).setText(item.getPlace());
    }
}
