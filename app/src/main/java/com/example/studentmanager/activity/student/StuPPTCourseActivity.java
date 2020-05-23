package com.example.studentmanager.activity.student;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanager.Adapter.StuCourseAdapter;
import com.example.studentmanager.Adapter.TeacherConcrenAdapter;
import com.example.studentmanager.Adapter.TeacherCourseAdapter;
import com.example.studentmanager.Model.CourseModel;
import com.example.studentmanager.R;
import com.example.studentmanager.View.BackTitleView;
import com.example.studentmanager.activity.teacher.TArrCourseActivity;
import com.example.studentmanager.activity.teacher.TeacherArrHomeworkAtivity;
import com.example.studentmanager.entity.StuJoinCourse;
import com.example.studentmanager.entity.TeacherCreateCourse;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class StuPPTCourseActivity extends AppCompatActivity {
    private Context mcontext;
    private RecyclerView course_list;
    private BackTitleView backTitleView;
    private StuCourseAdapter adapter;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sut_ppt_course);
        mcontext = this;
        course_list = (RecyclerView)findViewById(R.id.stuCourse_list);
        course_list.setLayoutManager(new LinearLayoutManager(mcontext,LinearLayoutManager.VERTICAL,false));
        backTitleView = (BackTitleView)findViewById(R.id.stuCourse_title);
        backTitleView.setTitleText("拥有课程");
        initCourse();
    }

    private void initCourse(){

        CourseModel.getInstance().queryStudentAllCourse(new FindListener<StuJoinCourse>() {
            @Override
            public void done(List<StuJoinCourse> list, BmobException e) {
               adapter  = new StuCourseAdapter(mcontext,list);
                course_list.setAdapter(adapter);
                adapter.setOnItemClickListener(new StuCourseAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        intent = new Intent(mcontext,CoursePPTShowActivity.class);
                        intent.putExtra("course",list.get(position));
                        startActivity(intent);
                    }
                });
            }
        });
    }
}
