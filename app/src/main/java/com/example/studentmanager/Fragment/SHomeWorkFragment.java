package com.example.studentmanager.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.studentmanager.Model.HomeworkModel;
import com.example.studentmanager.R;
import com.example.studentmanager.Utils.Constant;
import com.example.studentmanager.activity.student.StuHomeworkLevelActivity;
import com.example.studentmanager.activity.student.StuLookHomeworkActivity;
import com.example.studentmanager.activity.student.StuPPTCourseActivity;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;

public class SHomeWorkFragment extends Fragment implements View.OnClickListener {

    private Button stu_look_Homework;
    private Button stu_check_Homework;
    private TextView nocommitCount;
    private Button stu_downloadPPT;
    private Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.stu_homework_fragment,container,false);
        stu_look_Homework = (Button) v.findViewById(R.id.stu_look_Homework);
        stu_check_Homework =(Button) v.findViewById(R.id.stu_check_Homework);
        stu_downloadPPT = (Button)v.findViewById(R.id.stu_downloadPPT);
        nocommitCount = (TextView)v.findViewById(R.id.noCommitCount);
        stu_check_Homework.setOnClickListener(this);
        stu_look_Homework.setOnClickListener(this);
        stu_downloadPPT.setOnClickListener(this);
        noCommitCount();
        return v;
    }
    private void noCommitCount(){
        HomeworkModel.getInstance().findNocommitCount(Constant.student.getStuno(), new CountListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (integer.intValue() > 0) {
                    nocommitCount.setText(String.valueOf(integer.intValue()));
                }else{
                    nocommitCount.setText("0");
                }
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.stu_look_Homework:
                intent = new Intent(getActivity(), StuLookHomeworkActivity.class);
                startActivity(intent);
                break;
            case R.id.stu_check_Homework:
                intent = new Intent(getActivity(), StuHomeworkLevelActivity.class);
                startActivity(intent);
                break;
            case R.id.stu_downloadPPT:
                intent = new Intent(getActivity(), StuPPTCourseActivity.class);
                startActivity(intent);
                break;
        }
    }
}
