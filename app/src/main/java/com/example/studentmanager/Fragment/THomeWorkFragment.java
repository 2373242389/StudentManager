package com.example.studentmanager.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.studentmanager.R;
import com.example.studentmanager.activity.teacher.TArrCourseActivity;
import com.example.studentmanager.activity.teacher.THomeworkActivity;
import com.example.studentmanager.activity.teacher.TeacherPPTCourseActivity;
import com.example.studentmanager.activity.teacher.UploadCourseFileActivity;

import cn.bmob.v3.http.I;

public class THomeWorkFragment extends Fragment implements View.OnClickListener {
   private Button tArrayHomework;
   private Button tCorrectHomework;
   private Intent intent;
   private Button uploadFile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.teacher_homework_fragment,container,false);
         tArrayHomework = (Button) v.findViewById(R.id.teacherArr_Homework);
        tCorrectHomework =(Button) v.findViewById(R.id.teacherCorrect_Homework);
        uploadFile = (Button)v.findViewById(R.id.courseFile);
        tArrayHomework.setOnClickListener(this);
        tCorrectHomework.setOnClickListener(this);
        uploadFile.setOnClickListener(this);
        return v;
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.teacherArr_Homework:
                    intent = new Intent(getActivity(), TArrCourseActivity.class);
                    startActivity(intent);
                break;
            case R.id.teacherCorrect_Homework:
                intent = new Intent(getActivity(), THomeworkActivity.class);
                startActivity(intent);
                break;
            case R.id.courseFile:
                intent = new Intent(getActivity(), TeacherPPTCourseActivity.class);
                startActivity(intent);

        }
    }
}
