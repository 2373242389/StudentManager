package com.example.studentmanager.activity.teacher;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Adapter;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanager.Adapter.BaseRecyclerViewAdapter;
import com.example.studentmanager.Adapter.TeacherHomeworkAdapter;
import com.example.studentmanager.Model.HomeworkModel;
import com.example.studentmanager.R;
import com.example.studentmanager.Utils.Constant;
import com.example.studentmanager.Utils.ToastUtils;
import com.example.studentmanager.entity.HomeWork;
import com.example.studentmanager.entity.TArrHomework;
import com.example.studentmanager.entity.TCorrectHomework;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class THomeworkActivity extends AppCompatActivity {
    private RecyclerView homeworkView;
    private ArrayList<TArrHomework> homeworkList = new ArrayList<>();
    private TeacherHomeworkAdapter homeWorkAdapter;
    private Intent intent;
    private Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_all_course);
        context = this;
        homeworkView = (RecyclerView)findViewById(R.id.TeacherCourse_list);
        homeworkView.setLayoutManager(new LinearLayoutManager(THomeworkActivity.this, LinearLayoutManager.VERTICAL,false));
        initHomework();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:homeWorkAdapter = new TeacherHomeworkAdapter(THomeworkActivity.this,homeworkList);
                      homeworkView.setAdapter(homeWorkAdapter);
                      homeWorkAdapter.setOnItemClickListener(new TeacherHomeworkAdapter.OnItemClickListener() {
                          @Override
                          public void onItemClick(int position) {
                              intent = new Intent(THomeworkActivity.this,HomeworkCommitActivity.class);
                              intent.putExtra("arrayHomework",homeworkList.get(position));
                              startActivity(intent);
                          }
                      });
                break;
            }
        }
    };
  private void initHomework(){

         HomeworkModel.getInstance().findTeacherCreateHomework(Constant.teacher.getTeacherNo(), new FindListener<TArrHomework>() {
          @Override
          public void done(List<TArrHomework> list, BmobException e) {
              if (list.size() != 0) {
                  ToastUtils.showMessage(THomeworkActivity.this, String.valueOf(list.size()));
                  homeworkList.addAll(list);
                  Message message = handler.obtainMessage();
                  message.what = 1;
                  handler.sendMessage(message);
              }else {
                  ToastUtils.showMessage(context,"没有提交作业的学生");
              }
          }
      });
    }
}
