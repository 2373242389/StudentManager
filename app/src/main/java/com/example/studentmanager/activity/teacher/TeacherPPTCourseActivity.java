package com.example.studentmanager.activity.teacher;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanager.Adapter.TeacherConcrenAdapter;
import com.example.studentmanager.Adapter.TeacherCourseAdapter;
import com.example.studentmanager.Model.CourseModel;
import com.example.studentmanager.R;
import com.example.studentmanager.View.BackTitleView;
import com.example.studentmanager.entity.TeacherCreateCourse;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class TeacherPPTCourseActivity extends AppCompatActivity {
    private RecyclerView course_list;
    private BackTitleView backTitleView;
    private TeacherCourseAdapter teacherCourseAdapter;
    private Intent intent;
    private Context mcontext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_all_course);
        mcontext = this;
        course_list = (RecyclerView)findViewById(R.id.TeacherCourse_list);
        course_list.setLayoutManager(new LinearLayoutManager(mcontext,LinearLayoutManager.VERTICAL,false));
        backTitleView = (BackTitleView)findViewById(R.id.teacherCourse_title);
        backTitleView.setTitleText("所教课程");
        initCourse();
    }

    private void initCourse(){

        CourseModel.getInstance().queryTeacherAllCourse1(new FindListener<TeacherCreateCourse>() {
            @Override
            public void done(List<TeacherCreateCourse> list, BmobException e) {
                teacherCourseAdapter = new TeacherCourseAdapter(mcontext,list);
                course_list.setAdapter(teacherCourseAdapter);
                teacherCourseAdapter.setOnItemClickListener(new TeacherConcrenAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        intent = new Intent(mcontext,UploadCourseFileActivity.class);
                        intent.putExtra("course",list.get(position));
                        startActivity(intent);
                    }
                });
            }
        });
//        CourseModel.getInstance().queryTeacherAllCourse(new FindListener<Course>() {
//            @Override
//            public void done(List<Course> list, BmobException e) {
//                ToastUtils.showMessage(TArrCourseActivity.this, String.valueOf(list.size()));
//                teacherCourseAdapter = new TeacherCourseAdapter(TArrCourseActivity.this,list);
//                course_list.setAdapter(teacherCourseAdapter);
//
//            }
//        });
    }
}
