package com.example.studentmanager.activity.student;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanager.Adapter.PPTAdapter;
import com.example.studentmanager.Model.PPTMoudel;
import com.example.studentmanager.R;
import com.example.studentmanager.Utils.ToastUtils;
import com.example.studentmanager.View.BackTitleView;
import com.example.studentmanager.entity.PPTFile;
import com.example.studentmanager.entity.StuJoinCourse;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class StuPPTShowActivity extends AppCompatActivity implements View.OnClickListener{
    private BackTitleView title;
    private RecyclerView ppt_list;
    private PPTAdapter adapter;
    private Button download_ppt;
    Context context;
    StuJoinCourse stuJoinCourse;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_ppt_show);
        context = this;
        stuJoinCourse = (StuJoinCourse) getIntent().getSerializableExtra("course");
        title = (BackTitleView)findViewById(R.id.stu_ppt_title);
        title.setTitleText("学生下载PPT列表");
        ppt_list = (RecyclerView)findViewById(R.id.my_download_ppt);
        download_ppt = (Button)findViewById(R.id.download_ppt);
        download_ppt.setOnClickListener(this);
    }

    private void init(){
        PPTMoudel.getInstance().queryCoursePPT(stuJoinCourse.getCourseId(), new FindListener<PPTFile>() {
            @Override
            public void done(List<PPTFile> list, BmobException e) {
                ToastUtils.showMessage(context, String.valueOf(list.size()));
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.download_ppt:
                Intent intent = new Intent(context,CoursePPTShowActivity.class);
                intent.putExtra("course",stuJoinCourse);
                startActivity(intent);
        }
    }
}
