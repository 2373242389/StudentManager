package com.example.studentmanager.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studentmanager.Model.StudentModel;
import com.example.studentmanager.Model.TeacherModel;
import com.example.studentmanager.R;
import com.example.studentmanager.Utils.ToastUtils;
import com.example.studentmanager.activity.student.StuRegisterActivity;
import com.example.studentmanager.entity.Student;
import com.example.studentmanager.entity.Teacher;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

public class updatePasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText phone;
    private EditText password;
    private EditText surePassword;
    private EditText sureCode;
    private Button btn_save;
    private Button btn_getCode;
    private Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_password);
        context = this;
        phone = (EditText)findViewById(R.id.phone);
        password = (EditText)findViewById(R.id.new_psw);
        sureCode = (EditText)findViewById(R.id.check_num);
        btn_getCode =(Button)findViewById(R.id.getCode);
        surePassword = (EditText)findViewById(R.id.et_new_psw_again);
        btn_save = (Button)findViewById(R.id.btn_save);
        btn_save.setOnClickListener(this);
    }

    public void updatePassword(){
        StudentModel.getInstance().queryStudentByPhone(phone.getText().toString(), new FindListener<Student>() {
            @Override
            public void done(List<Student> students, BmobException e) {
                if (students.size() == 0){
                    TeacherModel.getInstance().queryTeacherByPhone(phone.getText().toString(), new FindListener<Teacher>() {
                        @Override
                        public void done(List<Teacher> teachers, BmobException e) {
                            if (teachers.size() == 0){
                                ToastUtils.showMessage(context,"该手机号未被注册");
                            }else{
                                teachers.get(0).setPassword(password.getText().toString());
                                teachers.get(0).update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null){
                                            ToastUtils.showMessage(context,"修改成功");
                                        }else {
                                            ToastUtils.showMessage(context,"修改失败");
                                        }
                                    }
                                });
                            }
                        }
                    });
                }else {
                    students.get(0).setPassword(password.getText().toString());
                    students.get(0).update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                         if (e == null){
                             ToastUtils.showMessage(context,"修改成功");
                         }else {
                             ToastUtils.showMessage(context,"修改失败");
                         }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_save:
                if (TextUtils.equals(sureCode.getText().toString(),"1234")) {
                    if (TextUtils.isEmpty(phone.getText().toString())||TextUtils.isEmpty(password.getText().toString())||TextUtils.isEmpty(surePassword.getText().toString())||TextUtils.isEmpty(sureCode.getText().toString())) {
                        Toast.makeText(context, "输入信息不能为空", Toast.LENGTH_SHORT).show();
                    }
                        if (!TextUtils.equals(password.getText().toString(), surePassword.getText().toString())) {
                            Toast.makeText(context, "密码输入不一致", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            updatePassword();
                            ToastUtils.showMessage(context,"修改成功");
                        }
                }else {
                    ToastUtils.showMessage(context,"验证码错误");
                }
                break;
        }
    }
}
