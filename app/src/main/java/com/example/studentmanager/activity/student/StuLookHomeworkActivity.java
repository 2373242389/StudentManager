package com.example.studentmanager.activity.student;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanager.Adapter.StuHomeWorkAdapter;
import com.example.studentmanager.Model.HomeworkModel;
import com.example.studentmanager.R;
import com.example.studentmanager.Utils.Constant;
import com.example.studentmanager.Utils.ToastUtils;
import com.example.studentmanager.entity.HomeWork;
import com.example.studentmanager.entity.StuCommitHomework;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class StuLookHomeworkActivity extends AppCompatActivity {
    private RecyclerView homework_list;
    private StuHomeWorkAdapter homeWorkAdapter;
    private Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_look_homework);
        homework_list = (RecyclerView)findViewById(R.id.homework_list);
        homework_list.setLayoutManager(new LinearLayoutManager(StuLookHomeworkActivity.this, LinearLayoutManager.VERTICAL,false));
        lookNoCommitHomework();
    }
    private void lookNoCommitHomework(){
        HomeworkModel.getInstance().ShowNoCommitHomework(Constant.student.getStuno(), new FindListener<StuCommitHomework>() {
            @Override
            public void done(List<StuCommitHomework> list, BmobException e) {
                homeWorkAdapter = new StuHomeWorkAdapter(StuLookHomeworkActivity.this,list);
                homework_list.setAdapter(homeWorkAdapter);
                homeWorkAdapter.setOnItemClickListener(new StuHomeWorkAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        intent = new Intent(StuLookHomeworkActivity.this,StuCommitHomeWorkActivity.class);
                        intent.putExtra("homework",list.get(position));
                        startActivity(intent);
                    }
                });
            }
        });
    }

}
