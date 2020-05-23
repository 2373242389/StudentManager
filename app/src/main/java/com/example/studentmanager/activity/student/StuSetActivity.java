package com.example.studentmanager.activity.student;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studentmanager.R;
import com.example.studentmanager.Utils.Constant;
import com.example.studentmanager.View.BackTitleView;

import java.util.Calendar;

public class StuSetActivity extends AppCompatActivity implements View.OnClickListener {
    private BackTitleView stu_change_title;
    private EditText stu_change_name_input;
    private Calendar calendar;// 用来装日期的
    private DatePickerDialog dialog;
    private Spinner stu_change_addyears_spinner;
    private Spinner stu_change_department_spinner;
    private EditText stu_class_input;
    private EditText stu_change_input_phone;
    private TextView stu_change_birthday;
    private EditText stu_change_check_num;
    private Button stu_change_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_set_change);
        stu_change_title = (BackTitleView)findViewById(R.id.stu_change_title);
        stu_change_title.setTitleText("修改个人信息");
        stu_change_name_input = (EditText)findViewById(R.id.stu_change_name_input);
        stu_change_addyears_spinner = (Spinner)findViewById(R.id.stu_change_addyears_spinner);
        stu_change_department_spinner = (Spinner)findViewById(R.id.stu_change_department_spinner);
        stu_class_input = (EditText)findViewById(R.id.stu_class_input);
        stu_change_input_phone = (EditText)findViewById(R.id.stu_change_input_phone);
        stu_change_birthday = (TextView)findViewById(R.id.stu_change_birthday);
        stu_change_check_num = (EditText) findViewById(R.id.stu_change_check_num);
        stu_change_btn = (Button)findViewById(R.id.stu_change_btn);
        stu_change_btn.setOnClickListener(this);
        initView();
    }

    private void initView(){
        stu_change_name_input.setText(Constant.student.getName());
        stu_class_input.setText(Constant.student.getClassroom());
        stu_change_birthday.setText(Constant.student.getBirthday());
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.stu_change_btn:
                Constant.student.setName(stu_change_name_input.getText().toString());
                Constant.student.setClassroom(stu_class_input.getText().toString());
                Constant.student.setBirthday(stu_change_birthday.getText().toString());
                Constant.student.setPhone(stu_change_input_phone.getText().toString());
                stu_change_department_spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                break;
        }
    }
}
