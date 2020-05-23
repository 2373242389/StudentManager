package com.example.studentmanager.activity.teacher;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanager.Adapter.SignStudentAdapter;
import com.example.studentmanager.Adapter.StudentAdapter;
import com.example.studentmanager.Model.CourseModel;
import com.example.studentmanager.Model.MessageModel;
import com.example.studentmanager.Model.SignModel;
import com.example.studentmanager.Model.StudentModel;
import com.example.studentmanager.R;
import com.example.studentmanager.Utils.Constant;
import com.example.studentmanager.Utils.ToastUtils;
import com.example.studentmanager.entity.Course;
import com.example.studentmanager.entity.StuSign;
import com.example.studentmanager.entity.Student;
import com.example.studentmanager.entity.Teacher;
import com.example.studentmanager.entity.TeacherCreateCourse;
import com.example.studentmanager.entity.TstartandEndSign;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class TeacherStartSignActivity extends AppCompatActivity implements View.OnClickListener{
    EditText sign_command;
    Button startSign_btn;
    Button endSign_btn;
    private Intent intent;
    private TeacherCreateCourse teacherCreateCourse;
    private EditText command;
    private RecyclerView sign_list;
    private SignStudentAdapter adapter;
    private TstartandEndSign tstartandEndSign;
    Date start_time;
    Message message;
    private Context mcontext;
    SimpleDateFormat sdf;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_sign_layout);
        mcontext = this;
        sign_list = (RecyclerView)findViewById(R.id.noSign_list);
        sign_list.setLayoutManager(new LinearLayoutManager(TeacherStartSignActivity.this,LinearLayoutManager.VERTICAL,false));
        startSign_btn = (Button)findViewById(R.id.start_signed_btn);
        endSign_btn = (Button)findViewById(R.id.end_signed_btn);
        command = (EditText)findViewById(R.id.sign_command_edit);
         sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        endSign_btn.setEnabled(false);
        intent = getIntent();
        teacherCreateCourse = (TeacherCreateCourse) intent.getSerializableExtra("course");
        findExistSign();
        endSign_btn.setEnabled(true);
        startSign_btn.setOnClickListener(this);
        endSign_btn.setOnClickListener(this);
    }

    public void findExistSign(){
        SignModel.getInstance().findExistSign(teacherCreateCourse.getCourseId(), new FindListener<TstartandEndSign>() {
            @Override
            public void done(List<TstartandEndSign> list, BmobException e) {
                if (list.size() == 0){
                    message = handler.obtainMessage();
                    message.what = 1;
                    handler.sendMessage(message);
                }else{
                    message = handler.obtainMessage();
                    message.obj = list.get(0);
                    message.what = 2;
                    handler.sendMessage(message);
                }
            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1: tstartandEndSign = new TstartandEndSign();
                break;
                case 2:tstartandEndSign = (TstartandEndSign) msg.obj;
                break;
            }
        }
    };
    public void showSignStudent() throws ParseException {


        String createdAtStart = tstartandEndSign.getCreatedAt();
        Date createdAtDateStart = sdf.parse(createdAtStart);
        BmobDate start = new BmobDate(createdAtDateStart);

        String createdAtEnd = tstartandEndSign.getUpdatedAt();
        Date createdAtDateEnd = sdf.parse(createdAtEnd);
        BmobDate end = new BmobDate(createdAtDateEnd);

        StudentModel.getInstance().findSignStudent(teacherCreateCourse.getCourseId(), start, end, new FindListener<StuSign>() {
            @Override
            public void done(List<StuSign> list, BmobException e) {
                adapter = new SignStudentAdapter(mcontext,list);
            sign_list.setAdapter(adapter);

            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start_signed_btn:
                startSign();
                break;
            case R.id.end_signed_btn:
                endSign();
                try {
                    showSignStudent();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void endSign() {
        tstartandEndSign.setStart_flag(0);
        tstartandEndSign.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    ToastUtils.showMessage(TeacherStartSignActivity.this, "结束签到");
                } else {
                    ToastUtils.showMessage(TeacherStartSignActivity.this, e.toString());
                }
            }
        });
    }
    private void startSign() {
                    SignModel.getInstance().findExistSign(teacherCreateCourse.getCourseId(), new FindListener<TstartandEndSign>() {
                        @Override
                        public void done(List<TstartandEndSign> list, BmobException e) {
                            if (list.size() == 0){
                                tstartandEndSign.setTeacherNo(Constant.teacher.getTeacherNo());
                                tstartandEndSign.setCourseId(teacherCreateCourse.getCourseId());
                                tstartandEndSign.setStart_flag(1);
                                try {
                                     start_time = sdf.parse(sdf.format(new Date()));
                                    tstartandEndSign.setStart_Time(new BmobDate(start_time));
                                } catch (ParseException ex) {
                                    ex.printStackTrace();
                                }

                                CourseModel.getInstance().queryCourse(teacherCreateCourse.getCourseId(), new FindListener<Course>() {
                                    @Override
                                    public void done(List<Course> list, BmobException e) {
                                        list.get(0).setCommand(String.valueOf(command.getText()));
                                        list.get(0).update(new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                            }
                                        });
                                    }
                                });
                                tstartandEndSign.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        if (e == null){
                                            ToastUtils.showMessage(TeacherStartSignActivity.this,"签到已开启");
                                            startSign_btn.setEnabled(false);
                                            endSign_btn.setEnabled(true);
                                        }else {
                                            ToastUtils.showMessage(TeacherStartSignActivity.this,"开启失败");
                                        }
                                    }
                                });
                            }else {
                                tstartandEndSign.setStart_flag(1);
                                try {
                                    start_time = sdf.parse(sdf.format(new Date()));
                                    tstartandEndSign.setStart_Time(new BmobDate(start_time));
                                } catch (ParseException ex) {
                                    ex.printStackTrace();
                                }
                                tstartandEndSign.update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null){
                                            ToastUtils.showMessage(TeacherStartSignActivity.this,"签到已开启");
                                            startSign_btn.setEnabled(false);
                                            endSign_btn.setEnabled(true);
                                        }else {
                                            ToastUtils.showMessage(TeacherStartSignActivity.this,"开启失败");
                                        }
                                    }
                                });
                            }
                        }
                    });

                }
}
