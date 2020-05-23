package com.example.studentmanager.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import com.example.studentmanager.R;
import com.example.studentmanager.Utils.Constant;
import com.example.studentmanager.Utils.ToastUtils;
import com.example.studentmanager.activity.student.StuCommitHomeWorkActivity;
import com.example.studentmanager.activity.student.StuRegisterActivity;
import com.example.studentmanager.activity.student.StuTabActivity;
import com.example.studentmanager.activity.teacher.TeacherRegisterActivity;
import com.example.studentmanager.activity.teacher.TeacherTabActivity;
import com.example.studentmanager.entity.Student;
import com.example.studentmanager.entity.Teacher;
import com.example.studentmanager.event.RefreshEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Logger;

import java.util.List;

import cn.bmob.newim.BmobIM;
import cn.bmob.newim.bean.BmobIMUserInfo;
import cn.bmob.newim.listener.ConnectListener;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText NoEt;
    private EditText passwordEt;
    private Button register_btn;
    private Button login_btn;
    private Intent teacherIntent = null;
    private Intent stuIntent = null;
    String name;
    private Intent intent;
    String password;
    private CheckBox checkBox;
    private Button forgive_pwd;

    private  String REMEMBER_PWD_PREF = "rememberPwd";
    final String ACCOUNT_PREF = "account";
    final String PASSWORD_PREF = "password";
    SharedPreferences preferences;
    boolean isRemember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_login_layout);
        NoEt = (EditText) findViewById(R.id.login_input_stuNo);
        passwordEt = (EditText) findViewById(R.id.login_input_password);
        login_btn = (Button) findViewById(R.id.login_btn);
        register_btn = (Button) findViewById(R.id.register_btn);
        checkBox = (CheckBox)findViewById(R.id.remember_pwd);
        forgive_pwd = (Button)findViewById(R.id.forgive_pwd);
        register_btn.setOnClickListener(this);
        login_btn.setOnClickListener(this);
        forgive_pwd.setOnClickListener(this);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        isRemember = preferences.getBoolean(REMEMBER_PWD_PREF,false);
        if (isRemember){
            NoEt.setText(preferences.getString(ACCOUNT_PREF,""));
            passwordEt.setText(preferences.getString(PASSWORD_PREF,""));
            checkBox.setChecked(true);
    }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    name = data.getStringExtra("stuNo");
                    password = data.getStringExtra("stu_password");
                    NoEt.setText(name);
                    passwordEt.setText(password);
                }
                break;
            case 2:
                    if (resultCode == RESULT_OK){
                        name = data.getStringExtra("teacherNo");
                        password = data.getStringExtra("teacher_password");
                        NoEt.setText(name);
                        passwordEt.setText(password);
                }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_btn:
                new AlertDialog.Builder(LoginActivity.this).setTitle("请选择").setMessage("请选择注册身份")
                        .setIcon(R.mipmap.ic_launcher).setNegativeButton("学生", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        stuIntent = new Intent(LoginActivity.this, StuRegisterActivity.class);
                        startActivityForResult(stuIntent, 1);
                    }
                }).setNeutralButton("老师", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        teacherIntent = new Intent(LoginActivity.this, TeacherRegisterActivity.class);
                        startActivityForResult(teacherIntent, 2);
                    }
                }).create().show();
                break;
            case R.id.login_btn:
                SharedPreferences.Editor editor = preferences.edit();
                if (checkBox.isChecked()){
                    editor.putBoolean(REMEMBER_PWD_PREF,true);
                    editor.putString(ACCOUNT_PREF,NoEt.getText().toString());
                    editor.putString(PASSWORD_PREF,passwordEt.getText().toString());
                }else{
                    editor.clear();
                }
                editor.apply();
                BmobQuery<Student> stu_bmobQuery = new BmobQuery<Student>();
                BmobQuery<Teacher> teacher_bmobQuery = new BmobQuery<Teacher>();
                stu_bmobQuery.addWhereEqualTo("stuno", NoEt.getText().toString());
                teacher_bmobQuery.addWhereEqualTo("teacherNo", NoEt.getText().toString());
                if (NoEt.getText().toString().length() == 8) {
                    teacher_bmobQuery.findObjects(new FindListener<Teacher>() {
                        @Override
                        public void done(List<Teacher> list, BmobException e) {
                            if (e == null) {
                                if (!TextUtils.equals(list.get(0).getPassword(), passwordEt.getText().toString())) {
                                    ToastUtils.showMessage(LoginActivity.this,"密码错误");
                                } else {
                                    teacherIntent = new Intent(LoginActivity.this, TeacherTabActivity.class);
                                    Constant.teacher = list.get(0);
                                    BmobIM.connect(Constant.teacher.getTeacherNo(), new ConnectListener() {
                                        @Override
                                        public void done(String s, BmobException e) {
                                            if (e == null){   EventBus.getDefault().post(new RefreshEvent());}
                                            else{ ToastUtils.showMessage(LoginActivity.this,e.toString());}
                                        }
                                    });
                                    BmobIM.getInstance().updateUserInfo(new BmobIMUserInfo(Constant.teacher.getTeacherNo(),Constant.teacher.getName(),null));
                                    startActivity(teacherIntent);
                                }
                            } else {
                                ToastUtils.showMessage(LoginActivity.this,e.toString());
                            }
                        }
                    });
                }else if (NoEt.getText().toString().length() == 9){
                    stu_bmobQuery.findObjects(new FindListener<Student>() {
                        @Override
                        public void done(List<Student> list, BmobException e) {
                            if (e == null) {
                                if (!TextUtils.equals(list.get(0).getPassword(), passwordEt.getText().toString())) {
                                    ToastUtils.showMessage(LoginActivity.this, list.get(0).getPassword().toString());
                                } else {
                                    stuIntent = new Intent(LoginActivity.this, StuTabActivity.class);
                                    Constant.student = list.get(0);
                                    BmobIM.connect(Constant.student.getStuno(), new ConnectListener() {
                                        @Override
                                        public void done(String s, BmobException e) {
                                            if (e == null){   EventBus.getDefault().post(new RefreshEvent());}
                                            else{ ToastUtils.showMessage(LoginActivity.this,e.toString());}
                                        }
                                    });
                                    BmobIM.getInstance().updateUserInfo(new BmobIMUserInfo(Constant.student.getStuno(),Constant.student.getName(),null));
                                    startActivity(stuIntent);
                                }
                            } else {
                                ToastUtils.showMessage(LoginActivity.this, e.toString());
                            }
                        }
                    });
                }else{
                    ToastUtils.showMessage(LoginActivity.this,"请检查输入账号密码是否合法或者为空");
                }
                break;

            case R.id.forgive_pwd:
                 intent = new Intent(this,updatePasswordActivity.class);
                startActivity(intent);
        }
    }

}

