package com.example.studentmanager.activity.teacher;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanager.Adapter.InventoryAdapter;
import com.example.studentmanager.Adapter.JoinCourseStudentAdapter;
import com.example.studentmanager.Adapter.StudentAdapter;
import com.example.studentmanager.Model.CourseModel;
import com.example.studentmanager.Model.StudentModel;
import com.example.studentmanager.R;
import com.example.studentmanager.Utils.ToastUtils;
import com.example.studentmanager.View.BackTitleView;
import com.example.studentmanager.View.SlideRecyclerView;
import com.example.studentmanager.entity.Course;
import com.example.studentmanager.entity.StuJoinCourse;
import com.example.studentmanager.entity.Student;
import com.example.studentmanager.entity.TeacherCreateCourse;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class JoinCourseStudentActivity extends AppCompatActivity {
    private SlideRecyclerView join_course_student_list;
    private JoinCourseStudentAdapter adapter;
    private TeacherCreateCourse teacherCreateCourse;
    private Context mcontext;
    BackTitleView course_student_title;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_course_student_list);
        mcontext = this;
        course_student_title = (BackTitleView)findViewById(R.id.course_student_title);
        course_student_title.setTitleText("学生列表");
        join_course_student_list = (SlideRecyclerView) findViewById(R.id.course_student_list);
        join_course_student_list.setLayoutManager(new LinearLayoutManager(mcontext,LinearLayoutManager.VERTICAL,false));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_inset));
        join_course_student_list.addItemDecoration(itemDecoration);
        teacherCreateCourse = (TeacherCreateCourse) getIntent().getSerializableExtra("course");
        initList();
    }
    private void initList(){
        StudentModel.getInstance().queryCourseAllStudent(teacherCreateCourse.getCourseId(), new FindListener<StuJoinCourse>() {
            @Override
            public void done(List<StuJoinCourse> list, BmobException e) {
            adapter = new JoinCourseStudentAdapter(mcontext,list);
            join_course_student_list.setAdapter(adapter);
                adapter.setOnDeleteClickListener(new JoinCourseStudentAdapter.OnDeleteClickLister() {
                    @Override
                    public void onDeleteClick(View view, int position) {

                        new AlertDialog.Builder(mcontext).setTitle("确认").setMessage("确认删除该学生吗？")
                                .setIcon(R.mipmap.ic_launcher).setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (list.size() != 0){
                                    int i = position;
                                    list.get(position).delete(new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                       if (e == null){
                                           list.remove(i);
                                           adapter.notifyDataSetChanged();
                                           ToastUtils.showMessage(mcontext,"删除成功");
                                       }else {
                                           ToastUtils.showMessage(mcontext,"删除失败");
                                       }

                                        }
                                    });
                                }

                            }
                        }).setNeutralButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        }).create().show();

                    }
                });
            }
        });
    }
}
