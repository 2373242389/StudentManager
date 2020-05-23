package com.example.studentmanager.activity.student;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studentmanager.R;
import com.example.studentmanager.Utils.ToastUtils;
import com.example.studentmanager.activity.LoginActivity;
import com.example.studentmanager.entity.Student;

import java.util.Calendar;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class StuRegisterActivity extends AppCompatActivity implements View.OnClickListener{

       private String stu_department;
       private String stu_year;
       private TextView stu_getTime;
       private Calendar calendar;// 用来装日期的
       private DatePickerDialog dialog;
       private EditText stuNoEt;
       private EditText stuNameEt;
       private EditText stuPasswordEt;
       private EditText checkpasswordEt;
       private EditText stu_phoneEt;
       private EditText stu_codeEt;
       private EditText stu_classroomEt;
       private Spinner stu_departmentSpinner;
       private Spinner stu_addYearSpinner;
       private Button stu_sure_btn;
       private RadioGroup stu_sex_rg;
       private RadioButton stu_sexButton;
       private Student stu;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_sturegister_layout);
        stuNoEt = (EditText)findViewById(R.id.stu_register_input_No);
        stuNameEt = (EditText)findViewById(R.id.stu_register_input_name);
        stu_codeEt = (EditText)findViewById(R.id.stu_check_num);
        stu_sex_rg = (RadioGroup)findViewById(R.id.stu_sex_rg);
        stu_sexButton = (RadioButton)findViewById(stu_sex_rg.getCheckedRadioButtonId());
        stuPasswordEt = (EditText)findViewById(R.id.stu_register_input_password);
        checkpasswordEt = (EditText)findViewById(R.id.stu_register_check_password);
        stu_phoneEt = (EditText)findViewById(R.id.stu_register_input_phone);
        stu_classroomEt  = (EditText)findViewById(R.id.stu_class_input);
        stu_getTime = (TextView)findViewById(R.id.stu_birthday);
        stu_departmentSpinner = (Spinner)findViewById(R.id.stu_department_spinner);
        stu_addYearSpinner = (Spinner)findViewById(R.id.stu_addyears_spinner);
        stu_sure_btn = (Button)findViewById(R.id.stu_sure_btn);
        stu_sure_btn.setOnClickListener(this);

        stu_departmentSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stu_department = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        stu_addYearSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stu_year = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        stu_getTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                dialog = new DatePickerDialog(StuRegisterActivity.this,AlertDialog.THEME_HOLO_DARK,
                        new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        stu_getTime.setText(year + "年"+ monthOfYear+ "月"+ dayOfMonth +"日");
                    }
                },calendar.get(Calendar.YEAR),calendar
                        .get(Calendar.MONTH),calendar
                        .get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.stu_sure_btn:
                if (TextUtils.isEmpty(stuNoEt.getText().toString())||TextUtils.isEmpty(stuNameEt.getText().toString())||TextUtils.isEmpty(stuPasswordEt.getText().toString())||TextUtils.isEmpty(checkpasswordEt.getText().toString())||TextUtils.isEmpty(stu_getTime.getText().toString())||TextUtils.isEmpty(stu_classroomEt.getText().toString())||TextUtils.isEmpty(stu_phoneEt.getText().toString()) || TextUtils.isEmpty(stu_codeEt.getText().toString())) {
                    Toast.makeText(StuRegisterActivity.this, "输入信息不能为空", Toast.LENGTH_SHORT).show();
                    if (!TextUtils.equals(stuPasswordEt.getText().toString(), checkpasswordEt.getText().toString())) {
                        Toast.makeText(StuRegisterActivity.this, "密码输入不一致", Toast.LENGTH_SHORT).show();
                    }
                }  if (stuNoEt.getText().toString().length() != 9){
                ToastUtils.showMessage(StuRegisterActivity.this,"请输入9位学号");
            }

                    else{
                        stu = new Student();
                        stu.setStuno(stuNoEt.getText().toString());
                        stu.setName(stuNameEt.getText().toString());
                        stu.setPassword(stuPasswordEt.getText().toString());
                        stu.setSex(stu_sexButton.getText().toString());
                        stu.setDepartment(stu_department);
                        stu.setAddYear(stu_year);
                        stu.setClassroom(stu_classroomEt.getText().toString());
                        stu.setBirthday(stu_getTime.getText().toString());
                        stu.setPhone(stu_phoneEt.getText().toString());
                        stu.save(new SaveListener<String>() {
                          @Override
                          public void done(String objectId, BmobException e) {
                              if (e == null){
                                  ToastUtils.showMessage(StuRegisterActivity.this,"注册成功");
                              }else {
                                  ToastUtils.showMessage(StuRegisterActivity.this,"添加失败,用户名已存在");
                              }

                          }
                      });
                        Intent intent = new Intent(StuRegisterActivity.this, LoginActivity.class);
                        intent.putExtra("stuNo",stuNoEt.getText().toString());
                        intent.putExtra("stu_password",stuPasswordEt.getText().toString());
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                    break;
            }

        }
    }
