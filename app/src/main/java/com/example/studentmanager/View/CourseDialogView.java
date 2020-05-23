package com.example.studentmanager.View;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;

import com.example.studentmanager.R;
import com.example.studentmanager.entity.Course;
import com.example.studentmanager.entity.Teacher;

public class CourseDialogView extends Dialog {
    Activity context;

    private Button btn_save;

    private Button btn_cancel;

    private EditText course_name;

    private EditText course_no;

    private EditText course_desc;

    private EditText course_people_number;

    CourseDialogView courseDialogView;

    Teacher teacher;

    Course course;
    private View.OnClickListener mClickListener;


    public CourseDialogView(Activity context) {
        super(context);
        this.context = context;
    }

    public CourseDialogView(Activity context, int theme, View.OnClickListener clickListener) {
        super(context, theme);
        this.context = context;
        this.mClickListener = clickListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 指定布局
        this.setContentView(R.layout.view_course_dialog);

        course_name = (EditText) findViewById(R.id.course_name);
        course_no = (EditText) findViewById(R.id.course_code);
        course_desc = (EditText)findViewById(R.id.course_desc);
        course_people_number = (EditText)findViewById(R.id.course_people_number);
        // 根据id在布局中找到控件对象
        btn_save = (Button) findViewById(R.id.btn_course_save);
        btn_cancel = (Button)findViewById(R.id.btn_course_cancel);
        // 为按钮绑定点击事件监听器
        btn_save.setOnClickListener(mClickListener);
        btn_cancel.setOnClickListener(mClickListener);

        /*
         * 获取圣诞框的窗口对象及参数对象以修改对话框的布局设置, 可以直接调用getWindow(),表示获得这个Activity的Window
         * 对象,这样这可以以同样的方式改变这个Activity的属性.
         */
        Window dialogWindow = this.getWindow();

        WindowManager m = context.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        // p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.6
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.8
        dialogWindow.setAttributes(p);
        this.setCancelable(true);
    }

    public void showEditDialog(View view) {
        courseDialogView.show();
    }

//    @Override
//    public void onClick(View v) {
//            switch (v.getId()){
//                case R.id.btn_course_save:
//                    ToastUtils.showMessage(getOwnerActivity(),"我点了你");
//                    teacher = BmobUser.getCurrentUser(Teacher.class);
//                    course = new Course();
//                    course.setTeacher_No(teacher.getTeacherNo());
//                    course.setCode(course_desc.getText().toString());
//                    course.setCourseName(course_name.getText().toString());
//                    course.setStuCount(Integer.parseInt(course_people_number.getText().toString()));
//                    course.setDesc(course_desc.getText().toString());
//                    course.setCourseTeacher(teacher);
//                    course.save(new SaveListener<String>() {
//                        @Override
//                        public void done(String s, BmobException e) {
//                        if (e == null){
//                            ToastUtils.showMessage(getOwnerActivity(),"添加成功");
//                        }else {
//                            ToastUtils.showMessage(getOwnerActivity(),"添加失败");
//                        }
//                        }
//                    });
//                    break;
//
//                case R.id.btn_course_cancel:
//                    break;
//            }
//    }
}

