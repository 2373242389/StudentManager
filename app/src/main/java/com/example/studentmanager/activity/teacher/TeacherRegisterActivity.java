package com.example.studentmanager.activity.teacher;

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
import com.example.studentmanager.entity.Teacher;

import java.util.Calendar;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class TeacherRegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private String teacher_department;
    private String teacher_year;
    private TextView teacher_getTime;
    private Calendar calendar;// 用来装日期的
    private DatePickerDialog dialog;
    private EditText teacher_NoEt;
    private EditText teacher_NameEt;
    private EditText teacher_PasswordEt;
    private EditText teacher_checkpasswordEt;
    private EditText teacher_phoneEt;
    private EditText teacher_codeEt;
    private Spinner teacher_departmentSpinner;
    private Button teacher_sure_btn;
    private RadioGroup teacher_sex_rg;
    private RadioButton teacher_sexButton;
    private Teacher teacher;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_teacherregister_layout);
        teacher_NoEt = (EditText)findViewById(R.id.teacher_register_input_No);
        teacher_NameEt = (EditText)findViewById(R.id.teacher_register_input_name);
        teacher_codeEt = (EditText)findViewById(R.id.teacher_check_num);
        teacher_sex_rg = (RadioGroup)findViewById(R.id.teacher_sex_rg);
        teacher_sexButton = (RadioButton)findViewById(teacher_sex_rg.getCheckedRadioButtonId());
        teacher_PasswordEt = (EditText)findViewById(R.id.teacher_register_input_password);
        teacher_checkpasswordEt = (EditText)findViewById(R.id.teacher_register_check_password);
        teacher_phoneEt = (EditText)findViewById(R.id.teacher_register_input_phone);
        teacher_getTime = (TextView)findViewById(R.id.teacher_birthday);
        teacher_departmentSpinner = (Spinner)findViewById(R.id.teacher_department_spinner);
        teacher_sure_btn = (Button)findViewById(R.id.teacher_sure_btn);
        teacher_sure_btn.setOnClickListener(this);

        teacher_departmentSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                teacher_department = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        teacher_getTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                dialog = new DatePickerDialog(TeacherRegisterActivity.this, AlertDialog.THEME_HOLO_DARK,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                teacher_getTime.setText(year + "年"+ monthOfYear+ "月"+ dayOfMonth +"日");
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
            case R.id.teacher_sure_btn:
                if (TextUtils.isEmpty(teacher_NoEt.getText().toString())||TextUtils.isEmpty(teacher_NameEt.getText().toString())||TextUtils.isEmpty(teacher_PasswordEt.getText().toString())||TextUtils.isEmpty(teacher_checkpasswordEt.getText().toString())||TextUtils.isEmpty(teacher_getTime.getText().toString())||TextUtils.isEmpty(teacher_phoneEt.getText().toString()) || TextUtils.isEmpty(teacher_codeEt.getText().toString())) {
                    Toast.makeText(TeacherRegisterActivity.this, "输入信息不能为空", Toast.LENGTH_SHORT).show();
                    if (!TextUtils.equals(teacher_PasswordEt.getText().toString(), teacher_checkpasswordEt.getText().toString())) {
                        Toast.makeText(TeacherRegisterActivity.this, "密码输入不一致", Toast.LENGTH_SHORT).show();
                    }
                }
                if (teacher_NoEt.getText().toString().length() != 8){
                    ToastUtils.showMessage(TeacherRegisterActivity.this,"请输入8位学工号");
                }
                else{
                    teacher = new Teacher();
                    teacher.setTeacherNo(teacher_NoEt.getText().toString());
                    teacher.setName(teacher_NameEt.getText().toString());
                    teacher.setPassword(teacher_PasswordEt.getText().toString());
                    teacher.setSex(teacher_sexButton.getText().toString());
                    teacher.setDepartment(teacher_department);
                    teacher.setBirthday(teacher_getTime.getText().toString());
                    teacher.setPhone(teacher_phoneEt.getText().toString());
                    teacher.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId, BmobException e) {
                            if (e == null){
                                ToastUtils.showMessage(TeacherRegisterActivity.this,"注册成功");
                            }else {
                                ToastUtils.showMessage(TeacherRegisterActivity.this,"添加失败,用户名已存在");
                            }
                        }
                    });
                    Intent intent = new Intent(TeacherRegisterActivity.this, LoginActivity.class);
                    intent.putExtra("teacherNo",teacher_NoEt.getText().toString());
                    intent.putExtra("teacher_password",teacher_PasswordEt.getText().toString());
                    setResult(RESULT_OK,intent);
                    finish();
                }
                break;
        }

    }
}
