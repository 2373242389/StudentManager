package com.example.studentmanager.activity.student;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studentmanager.Model.CourseModel;
import com.example.studentmanager.R;
import com.example.studentmanager.Utils.Constant;
import com.example.studentmanager.Utils.ToastUtils;
import com.example.studentmanager.entity.Course;
import com.example.studentmanager.entity.Installation;
import com.example.studentmanager.entity.StuJoinCourse;
import com.example.studentmanager.entity.Student;
import com.example.studentmanager.entity.Teacher;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallationManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class AddCourseActivity extends AppCompatActivity implements View.OnClickListener{
    EditText codeEt;
    Button joinBtn;
    StuJoinCourse stuJoinCourse;
    private Context context;
     @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_add_course);
         context = this;
        codeEt = (EditText)findViewById(R.id.code_edit);
        joinBtn = (Button)findViewById(R.id.join_btn);
        joinBtn.setOnClickListener(this);
        ToastUtils.showMessage(AddCourseActivity.this,Constant.student.toString());
    }

    private void joinClass(){
        BmobQuery<Course> courseBmobQuery = new BmobQuery<>();
        courseBmobQuery.addWhereEqualTo("code",codeEt.getText().toString());
        courseBmobQuery.findObjects(new FindListener<Course>() {
            @Override
            public void done(List<Course> courses, BmobException e) {
                if (e == null){
                    CourseModel.getInstance().judgeExistCourse(courses.get(0).getObjectId(), new FindListener<StuJoinCourse>() {
                        @Override
                        public void done(List<StuJoinCourse> list, BmobException e) {
                            if (list.size() == 0){
                                stuJoinCourse = new StuJoinCourse();
                                stuJoinCourse.setCourseId(courses.get(0).getObjectId());
                                stuJoinCourse.setStuNo(Constant.student.getStuno());
                                stuJoinCourse.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        if (e == null){
                                            ToastUtils.showMessage(context,"添加成功");
                                        }else{
                                            ToastUtils.showMessage(context,"添加失败");
                                        }
                                    }
                                });
                            }else {
                                ToastUtils.showMessage(context,"该课程已经存在");
                            }
                        }
                    });
                }else{
                    ToastUtils.showMessage(AddCourseActivity.this,"未查到此课程,请重新写输入");
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
         switch (v.getId()){
             case R.id.join_btn:
                 joinClass();
                 break;
         }

    }
}
