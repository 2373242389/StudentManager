package com.example.studentmanager.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.studentmanager.Model.StudentModel;
import com.example.studentmanager.R;
import com.example.studentmanager.Utils.Constant;
import com.example.studentmanager.activity.student.StuSetActivity;
import com.example.studentmanager.entity.Course;
import com.example.studentmanager.entity.StuJoinCourse;
import com.example.studentmanager.entity.Student;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.http.I;
import cn.bmob.v3.listener.FindListener;

public class StuSetFragment extends Fragment implements View.OnClickListener {
private TextView me_class;
private ImageView me_login;
private TextView me_name;
private TextView me_joinnum_text;
private Button logout_btn;
private Intent intent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_set_layout, container, false);
       me_class = v.findViewById(R.id.me_class);
       me_login =  v.findViewById(R.id.me_login);
       me_name =  v.findViewById(R.id.me_name);
       me_joinnum_text =  v.findViewById(R.id.me_joinnum_text);
       logout_btn = v.findViewById(R.id.logout_btn);
       logout_btn.setOnClickListener(this);
       me_login.setOnClickListener(this);
       initSet();
        return v;
    }
    private void initSet(){
        StudentModel.getInstance().findStudentById(Constant.student.getStuno(), new FindListener<Student>() {
            @Override
            public void done(List<Student> list, BmobException e) {
                me_class.setText(list.get(0).getClassroom());
                me_name.setText(list.get(0).getName());
            }
        });
        StudentModel.getInstance().findStudentAllCourse(new FindListener<StuJoinCourse>() {
            @Override
            public void done(List<StuJoinCourse> list, BmobException e) {
                if (list.size() != 0) {
                    me_joinnum_text.setText(String.valueOf(list.size()));
                }else {
                    me_joinnum_text.setText("0");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logout_btn:
                    intent = new Intent(getActivity(), StuSetActivity.class);
                    startActivity(intent);
        }
    }
}
