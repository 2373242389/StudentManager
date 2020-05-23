package com.example.studentmanager.activity.student;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanager.Adapter.HomeworklevelAdapter;
import com.example.studentmanager.Model.HomeworkModel;
import com.example.studentmanager.R;
import com.example.studentmanager.Utils.Constant;
import com.example.studentmanager.Utils.ToastUtils;
import com.example.studentmanager.View.BackTitleView;
import com.example.studentmanager.entity.StuCommitHomework;
import com.example.studentmanager.entity.TArrHomework;
import com.example.studentmanager.entity.TCorrectHomework;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class StuHomeworkLevelActivity extends AppCompatActivity {
    private BackTitleView backTitleView;
    private RecyclerView homework_level_list;
    private Context mcontext;
    private HomeworklevelAdapter adapter;
    private List<StuCommitHomework> stuCommitHomework = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_homework_level);
        mcontext = this;
        homework_level_list = (RecyclerView)findViewById(R.id.stu_homework_level_list);
        homework_level_list.setLayoutManager(new LinearLayoutManager(mcontext,LinearLayoutManager.VERTICAL,false));
        backTitleView = (BackTitleView) findViewById(R.id.homework_level_title);
        backTitleView.setTitleText("作业评价");
        initHomeworkEstimate();
    }
//作业评价
    private void initHomeworkEstimate(){
        HomeworkModel.getInstance().findAllTeacherCorrectHomework(new FindListener<TCorrectHomework>() {
            @Override
            public void done(List<TCorrectHomework> list, BmobException e) {
                for (int i = 0; i< list.size();i++) {
                    HomeworkModel.getInstance().findAllCommitHomework(list.get(i).getHomeworkId(),Constant.student.getStuno(), new FindListener<StuCommitHomework>() {
                        @Override
                        public void done(List<StuCommitHomework> list, BmobException e) {
                            if (list.size() != 0) {
                                stuCommitHomework.add(list.get(0));
                                Message message = handler.obtainMessage();
                                message.what = 1;
                                handler.sendMessage(message);
                            }else {
                                ToastUtils.showMessage(mcontext,"还未有已经批改的作业");
                            }
                        }
                    });
                }
            }
        });
    }
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
           switch (msg.what){
               case 1:
                   adapter = new HomeworklevelAdapter(mcontext,stuCommitHomework);
                   homework_level_list.setAdapter(adapter);
           }
        }
    };
}
