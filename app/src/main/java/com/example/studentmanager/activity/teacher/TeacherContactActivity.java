package com.example.studentmanager.activity.teacher;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanager.Adapter.TeacherConcrenAdapter;
import com.example.studentmanager.Model.CourseModel;
import com.example.studentmanager.Model.StudentModel;
import com.example.studentmanager.Model.TeacherModel;
import com.example.studentmanager.R;
import com.example.studentmanager.Utils.Constant;
import com.example.studentmanager.Utils.ToastUtils;
import com.example.studentmanager.View.BackTitleView;
import com.example.studentmanager.activity.ChatActivity;
import com.example.studentmanager.entity.Course;
import com.example.studentmanager.entity.StuJoinCourse;
import com.example.studentmanager.entity.Student;
import com.example.studentmanager.entity.Teacher;
import com.example.studentmanager.entity.TeacherCreateCourse;

import java.util.List;
import java.util.WeakHashMap;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.http.I;
import cn.bmob.v3.listener.FindListener;

public class TeacherContactActivity extends AppCompatActivity {
    BackTitleView friendsListTitle;
    RecyclerView stu_list;
    Context context;
    private TeacherCreateCourse teacherCreateCourse;
    TeacherConcrenAdapter teacherConcrenAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_friendslist_layout);
        friendsListTitle = (BackTitleView)findViewById(R.id.friendslist_title);
        context = this;
        stu_list = (RecyclerView)findViewById(R.id.contact_list);
        teacherCreateCourse = (TeacherCreateCourse) getIntent().getSerializableExtra("course");
        stu_list.setLayoutManager(new LinearLayoutManager(TeacherContactActivity.this,LinearLayoutManager.VERTICAL,false));
        friendsListTitle.setTitleText("学生列表");
        StudentModel.getInstance().queryCourseStudent(teacherCreateCourse.getCourseId(),new FindListener<StuJoinCourse>() {
            @Override
            public void done(List<StuJoinCourse> list, BmobException e) {
                teacherConcrenAdapter = new TeacherConcrenAdapter(context,list);
                stu_list.setAdapter(teacherConcrenAdapter);
                teacherConcrenAdapter.setOnItemClickListener(new TeacherConcrenAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        StudentModel.getInstance().findStudentById(list.get(position).getStuNo(), new FindListener<Student>() {
                            @Override
                            public void done(List<Student> students, BmobException e) {
                                Intent intent = new Intent(context, ChatActivity.class);
                                intent.putExtra("u",students.get(0));
                                startActivity(intent);
                            }
                        });

                    }
                });
            }
        });
    }
}
