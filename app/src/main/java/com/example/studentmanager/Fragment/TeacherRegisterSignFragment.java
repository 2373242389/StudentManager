package com.example.studentmanager.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.studentmanager.Adapter.CourseAdapter;
import com.example.studentmanager.Adapter.SearchCourseAdapter;
import com.example.studentmanager.Adapter.StudentAdapter;
import com.example.studentmanager.Adapter.TeacherCourseAdapter;
import com.example.studentmanager.Model.CourseModel;
import com.example.studentmanager.Model.StudentModel;
import com.example.studentmanager.R;
import com.example.studentmanager.Utils.Constant;
import com.example.studentmanager.Utils.ToastUtils;
import com.example.studentmanager.activity.teacher.JoinCourseStudentActivity;
import com.example.studentmanager.activity.teacher.TeacherStartSignActivity;
import com.example.studentmanager.entity.Course;
import com.example.studentmanager.entity.Student;
import com.example.studentmanager.entity.Teacher;
import com.example.studentmanager.entity.TeacherCreateCourse;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class TeacherRegisterSignFragment extends Fragment implements View.OnClickListener {
    private Button btn_save;

    private Button btn_cancel;

    private EditText course_name;

    private EditText course_code;

    private EditText course_desc;

    private EditText course_people_number;

    private TeacherCourseAdapter courseAdapter;

    private Course  course = new Course();

    private Intent intent;

    private TeacherCreateCourse teacherCreateCourse;

    private RecyclerView createCourseList;

    private ArrayList<Course> mcourses = new ArrayList<Course>();
    private ArrayList<TeacherCreateCourse> teacherCreateCourses = new ArrayList<>();
    private SwipeRefreshLayout refresh;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_addcourse_layout, container, false);
        ImageView addCourse = (ImageView) v.findViewById(R.id.add_course);
        createCourseList = (RecyclerView)v.findViewById(R.id.course_list);
        refresh = v.findViewById(R.id.teacher_refresh);
        createCourseList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        initView();
        addCourse.setOnClickListener(this);
        setSwipe();
        return v;
    }
    private void setSwipe(){
        refresh.setColorSchemeResources(R.color.color_4d,R.color.tab_color_s);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initView();
                refresh.setRefreshing(false);
            }
        });

    }
    private void initView(){
        CourseModel.getInstance().queryTeacherAllCourse1(new FindListener<TeacherCreateCourse>() {
            @Override
            public void done(List<TeacherCreateCourse> list, BmobException e) {
                if (e == null){
                    courseAdapter = new TeacherCourseAdapter(getActivity(),list);
                    createCourseList.setAdapter(courseAdapter);
                    courseAdapter.setOnItemClickListener(new TeacherCourseAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position){
                            intent = new Intent(getActivity(),TeacherStartSignActivity.class);
                            intent.putExtra("course",list.get(position));
                            startActivity(intent);
                        }
                    });
                    courseAdapter.setOnItemLongClickListener(new TeacherCourseAdapter.OnItemLongClickListener() {
                        @Override
                        public void onIntemLongClickListener(View v, final int position) {
                            PopupMenu popupMenu = new PopupMenu(getActivity(),v);
                            popupMenu.getMenuInflater().inflate(R.menu.popmenu,popupMenu.getMenu());
                            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                                @Override
                                public boolean onMenuItemClick(MenuItem item) {
                                    if (item.getItemId() == R.id.lookStudents){
                                        intent = new Intent(getActivity(), JoinCourseStudentActivity.class);
                                        intent.putExtra("course",list.get(position));
                                        startActivity(intent);
                                    }
                                    if (item.getItemId() == R.id.updateItem){
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                        View view = LayoutInflater.from(getActivity()).inflate(R.layout.view_course_dialog, null);
                                        btn_save = view.findViewById(R.id.btn_course_save);
                                        btn_cancel = view.findViewById(R.id.btn_course_cancel);
                                        course_name = (EditText) view.findViewById(R.id.course_name);
                                        course_code = (EditText) view.findViewById(R.id.course_code);
                                        course_desc = (EditText) view.findViewById(R.id.course_desc);
                                        course_people_number = (EditText) view.findViewById(R.id.course_people_number);
                                        final Dialog dialog = builder.create();
                                        dialog.show();
                                        dialog.getWindow().setContentView(view);
                                        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                                        CourseModel.getInstance().queryCourse(list.get(position).getCourseId(), new FindListener<Course>() {
                                            @Override
                                            public void done(List<Course> courses, BmobException e) {
                                                if (e == null) {
                                                    course_name.setText(courses.get(0).getCourseName());
                                                    course_code.setText(courses.get(0).getCode());
                                                    course_desc.setText(courses.get(0).getDesc());
                                                    course_people_number.setText(String.valueOf(courses.get(0).getStuCount()));
                                                }
                                            }
                                        });
                                        btn_save.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                CourseModel.getInstance().queryCourse(list.get(position).getCourseId(), new FindListener<Course>() {
                                                    @Override
                                                    public void done(List<Course> list, BmobException e) {
                                                        list.get(0).setCourseName(course_name.getText().toString());
                                                        list.get(0).setStuCount(Integer.parseInt(course_people_number.getText().toString()));
                                                        list.get(0).setCode(course_code.getText().toString());
                                                        list.get(0).setDesc(course_desc.getText().toString());
                                                        list.get(0).update(new UpdateListener() {
                                                            @Override
                                                            public void done(BmobException e) {
                                                                if (e == null){
                                                                    ToastUtils.showMessage(getActivity(),"修改成功");
                                                                }else {
                                                                    ToastUtils.showMessage(getActivity(),e.toString());
                                                                }
                                                            }
                                                        });
                                                    }
                                                });

                                            }
                                        });
                                        btn_cancel.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });

                                    }
                                    if (item.getItemId() == R.id.removeItem){
                                        list.get(position).delete(new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                                ToastUtils.showMessage(getActivity(),"删除成功");
                                            }
                                        });
                                        courseAdapter.delete(position);
                                    }
                                    return false;
                                }

                            });
                            popupMenu.show();
                        }
                    });
                }else
                {
                    ToastUtils.showMessage(getActivity(),"暂无课程，请去创建");
                }
            }
        });

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_course:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.view_course_dialog, null);
                btn_save = view.findViewById(R.id.btn_course_save);
                btn_cancel = view.findViewById(R.id.btn_course_cancel);
                course_name = (EditText) view.findViewById(R.id.course_name);
                course_code = (EditText) view.findViewById(R.id.course_code);
                course_desc = (EditText) view.findViewById(R.id.course_desc);
                course_people_number = (EditText) view.findViewById(R.id.course_people_number);
                final Dialog dialog = builder.create();
                dialog.show();
                dialog.getWindow().setContentView(view);
                //使editext可以唤起软键盘
                dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        course.setTeacherNo(Constant.teacher.getTeacherNo());
                        course.setCode(course_code.getText().toString());
                        course.setDesc(course_desc.getText().toString());
                        course.setStuCount(Integer.parseInt(course_people_number.getText().toString()));
                        course.setCourseName(course_name.getText().toString());
                        course.setTeacherName(Constant.teacher.getName());
                        course.setTeacherDepartment(Constant.teacher.getDepartment());
                        course.setCommand("0");
                        course.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null){
                                    teacherCreateCourse = new TeacherCreateCourse();
                                    teacherCreateCourse.setCourseId(course.getObjectId());
                                    teacherCreateCourse.setTeacherNo(Constant.teacher.getTeacherNo());
                                    teacherCreateCourse.save(new SaveListener<String>() {
                                        @Override
                                        public void done(String s, BmobException e) {
                                            if (e ==null){
                                                courseAdapter.insert(teacherCreateCourse,0);
                                            }
                                        }
                                    });
                                }else{
                                    ToastUtils.showMessage(getActivity(),"添加课程失败");
                                }
                            }
                        });
                        dialog.dismiss();
                    }
                });
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
        }
    }

    ;
}
