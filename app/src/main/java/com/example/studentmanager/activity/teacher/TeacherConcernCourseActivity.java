package com.example.studentmanager.activity.teacher;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanager.Adapter.TeacherCourseAdapter;
import com.example.studentmanager.Model.CourseModel;
import com.example.studentmanager.R;
import com.example.studentmanager.View.BackTitleView;
import com.example.studentmanager.entity.Course;
import com.example.studentmanager.entity.TeacherCreateCourse;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class TeacherConcernCourseActivity extends AppCompatActivity {
    private TeacherCourseAdapter adapter;
    private RecyclerView course_list;
    private BackTitleView course_list_title;
    private Context context;
    private Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_course_list);
        context = this;
        course_list_title = (BackTitleView)findViewById(R.id.concern_course_title);
        course_list_title.setTitleText("所有课程");
        course_list = (RecyclerView)findViewById(R.id.concern_course_list);
        course_list.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        initView();
    }
    private void initView(){
        CourseModel.getInstance().queryTeacherAllCourse1(new FindListener<TeacherCreateCourse>() {
            @Override
            public void done(List<TeacherCreateCourse> list, BmobException e) {
                adapter = new TeacherCourseAdapter(context,list);
                course_list.setAdapter(adapter);
                adapter.setOnItemClickListener(new TeacherCourseAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        intent = new Intent(context,TeacherContactActivity.class);
                        intent.putExtra("course",list.get(position));
                        startActivity(intent);
                    }
                });
            }
        });
    }
}
