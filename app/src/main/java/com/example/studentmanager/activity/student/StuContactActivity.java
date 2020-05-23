package com.example.studentmanager.activity.student;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanager.Adapter.StudentConcrenAdapter;
import com.example.studentmanager.Model.StudentModel;
import com.example.studentmanager.Model.TeacherModel;
import com.example.studentmanager.R;
import com.example.studentmanager.Utils.Constant;
import com.example.studentmanager.View.BackTitleView;
import com.example.studentmanager.activity.ChatActivity;
import com.example.studentmanager.entity.StuJoinCourse;
import com.example.studentmanager.entity.Student;
import com.example.studentmanager.entity.Teacher;
import com.example.studentmanager.entity.TeacherCreateCourse;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.http.I;
import cn.bmob.v3.listener.FindListener;

public class StuContactActivity extends AppCompatActivity {
    BackTitleView friendsListTitle;
    private RecyclerView contact_list;
    StudentConcrenAdapter studentConcrenAdapter;
    private Intent intent;
    private ArrayList<Teacher> teachers = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_friendslist_layout);
        friendsListTitle = (BackTitleView) findViewById(R.id.friendslist_title);
        friendsListTitle.setTitleText("老师列表");
        contact_list = (RecyclerView)findViewById(R.id.contact_list);
        contact_list.setLayoutManager(new LinearLayoutManager(StuContactActivity.this,LinearLayoutManager.VERTICAL,false));
        StudentModel.getInstance().findStudentAllCourse(new FindListener<StuJoinCourse>() {
            @Override
            public void done(List<StuJoinCourse> stuJoinCourses, BmobException e) {
                for (int i = 0;i < stuJoinCourses.size();i++){
                    TeacherModel.getInstance().queryCourseTeacher(stuJoinCourses.get(i).getCourseId(), new FindListener<TeacherCreateCourse>() {
                        @Override
                        public void done(List<TeacherCreateCourse> teacherCreateCourses, BmobException e) {
                            for (int i = 0;i < teacherCreateCourses.size();i++){
                                TeacherModel.getInstance().findTeacherById(teacherCreateCourses.get(i).getTeacherNo(), new FindListener<Teacher>() {
                                    @Override
                                    public void done(List<Teacher> list, BmobException e) {
                                        studentConcrenAdapter = new StudentConcrenAdapter(StuContactActivity.this,list);
                                        contact_list.setAdapter(studentConcrenAdapter);
                                        studentConcrenAdapter.setOnItemClickListener(new StudentConcrenAdapter.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(int position) {
                                                intent = new Intent(StuContactActivity.this, ChatActivity.class);
                                                intent.putExtra("t",list.get(position));
                                                startActivity(intent);
                                            }
                                        });
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }
}
