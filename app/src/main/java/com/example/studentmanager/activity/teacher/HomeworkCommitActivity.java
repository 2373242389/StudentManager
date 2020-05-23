package com.example.studentmanager.activity.teacher;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanager.Adapter.StudentCommitHomeAdapter;
import com.example.studentmanager.Model.StudentModel;
import com.example.studentmanager.R;
import com.example.studentmanager.entity.StuCommitHomework;
import com.example.studentmanager.entity.Student;
import com.example.studentmanager.entity.TArrHomework;
import com.orhanobut.logger.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class HomeworkCommitActivity extends AppCompatActivity {
    private RecyclerView student_homework_commit_view;
    private ArrayList<StuCommitHomework> stuCommitHomeworks = new ArrayList<>();
    private StudentCommitHomeAdapter commitHomeAdapter;
    private Context context;
    TArrHomework arrayHomework;
    Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_commit_homework);
        context = this;
        student_homework_commit_view = (RecyclerView)findViewById(R.id.commit_homework_list);
        arrayHomework = (TArrHomework) getIntent().getSerializableExtra("arrayHomework");
        student_homework_commit_view.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        StudentModel.getInstance().FindCommitStudentByHomeworkId(arrayHomework.getHomeworkId(), new FindListener<StuCommitHomework>() {
            @Override
            public void done(List<StuCommitHomework> list, BmobException e) {
               for (int i = 0;i < list.size();i++){
                       stuCommitHomeworks.add(list.get(i));
               }
                Message message = handler.obtainMessage();
                message.what = 1;
                handler.sendMessage(message);
            }
        });
    }
    Handler handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what){
                        case 1:
                            commitHomeAdapter = new StudentCommitHomeAdapter(context,stuCommitHomeworks);
                            student_homework_commit_view.setAdapter(commitHomeAdapter);
                            commitHomeAdapter.setOnItemClickListener(new StudentCommitHomeAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(int position) {
                                    intent = new Intent(context,CorrectCommitHomework.class);
                                    intent.putExtra("stuCommitHomework",stuCommitHomeworks.get(position));
                                    startActivity(intent);
                                }
                            });
                    }
        }
    };
}
