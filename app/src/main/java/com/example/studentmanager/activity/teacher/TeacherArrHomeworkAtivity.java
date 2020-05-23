package com.example.studentmanager.activity.teacher;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studentmanager.Adapter.GridViewAdapter;
import com.example.studentmanager.Model.CourseModel;
import com.example.studentmanager.Model.HomeworkModel;
import com.example.studentmanager.Model.StudentModel;
import com.example.studentmanager.PictureSelectorConfig;
import com.example.studentmanager.R;
import com.example.studentmanager.Utils.Constant;
import com.example.studentmanager.Utils.MainConstant;
import com.example.studentmanager.Utils.ToastUtils;
import com.example.studentmanager.activity.PlusImageActivity;
import com.example.studentmanager.activity.student.StuCommitHomeWorkActivity;
import com.example.studentmanager.entity.Course;
import com.example.studentmanager.entity.File;
import com.example.studentmanager.entity.HomeWork;
import com.example.studentmanager.entity.StuCommitHomework;
import com.example.studentmanager.entity.StuJoinCourse;
import com.example.studentmanager.entity.Student;
import com.example.studentmanager.entity.TArrHomework;
import com.example.studentmanager.entity.TeacherCreateCourse;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobInstallationManager;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.InstallationListener;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.http.I;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadBatchListener;
import cn.bmob.v3.listener.UploadFileListener;

public class TeacherArrHomeworkAtivity extends AppCompatActivity implements View.OnClickListener{
    private Context mcontext;
    private GridView teacher_homeWork_grid;          //网格显示缩略图
    private Button buttonPublish;            //发布按钮
    private ArrayList<String> mPicList =new ArrayList<>();
    private GridViewAdapter mGridViewAddImgAdapter; //展示上传的图片的适配器
    private TeacherCreateCourse teacherCreateCourse;
    private File file;
    private EditText homeworkDecs;
    String[] array;
    private HomeWork homeWork;
    private TArrHomework tArrHomework;
    private String picUrl;
    private Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_array_homework);
        buttonPublish = (Button)findViewById(R.id.teacher_arrayHomework_button);
        homeworkDecs = (EditText)findViewById(R.id.teacher_arrayHomework_edit);
        teacher_homeWork_grid = (GridView) findViewById(R.id.teacher_homeWork_grid);
        mcontext = this;
        teacherCreateCourse = (TeacherCreateCourse) getIntent().getSerializableExtra("course");
        initGridView();
        buttonPublish.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    refreshAdapter(PictureSelector.obtainMultipleResult(data));
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    break;
            }
        }
        if (requestCode == MainConstant.REQUEST_CODE_MAIN && resultCode == MainConstant.RESULT_CODE_VIEW_IMG) {
            //查看大图页面删除了图片
            ArrayList<String> toDeletePicList = data.getStringArrayListExtra(MainConstant.IMG_LIST); //要删除的图片的集合
            mPicList.clear();
            mPicList.addAll(toDeletePicList);
            mGridViewAddImgAdapter.notifyDataSetChanged();
        }
    }


    /*
     * Dialog对话框提示用户删除操作
     * position为删除图片位置
     */
    private void initGridView(){
        mGridViewAddImgAdapter = new GridViewAdapter(mcontext,mPicList,1);
        teacher_homeWork_grid.setAdapter(mGridViewAddImgAdapter);
        teacher_homeWork_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == parent.getChildCount() - 1) {
                    if(mPicList.size() == MainConstant.MAX_SELECT_PIC_NUM){
                        viewPluImg(position);
                    }else{
                        selectPic(MainConstant.MAX_SELECT_PIC_NUM-mPicList.size());
                    }
                }else {
                    viewPluImg(position);
                }

            }
        });
    }

    private void selectPic(int maxTotal) {
        PictureSelectorConfig.initMultiConfig(this, maxTotal);
    }

    private void viewPluImg(int position) {
        Intent intent = new Intent(mcontext, PlusImageActivity.class);
        intent.putStringArrayListExtra(MainConstant.IMG_LIST, mPicList);
        intent.putExtra(MainConstant.POSITION, position);
        startActivityForResult(intent, MainConstant.REQUEST_CODE_MAIN);
    }
    private void refreshAdapter(List<LocalMedia> picList) {
        for (LocalMedia localMedia : picList) {
            //被压缩后的图片路径
            if (localMedia.isCompressed()) {
                String compressPath = localMedia.getCompressPath(); //压缩后的图片路径
                mPicList.add(compressPath); //把图片添加到将要上传的图片数组中
                mGridViewAddImgAdapter.notifyDataSetChanged();
            }
        }
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    StudentModel.getInstance().queryCourseStudent(teacherCreateCourse.getCourseId(), new FindListener<StuJoinCourse>() {
                        @Override
                        public void done(List<StuJoinCourse> list, BmobException e) {
                            for (int i= 0;i<list.size();i++){
                                StuCommitHomework stuCommitHomework = new StuCommitHomework();
                                stuCommitHomework.setHomeWorkID(homeWork.getObjectId());
                                stuCommitHomework.setStuNo(list.get(i).getStuNo());
                                stuCommitHomework.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {

                                    }
                                });
                            }
                        }
                    });
                        break;
            }
        }
    };
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.teacher_arrayHomework_button:
                homeWork = new HomeWork();
                homeWork.setHomeworkDecs(homeworkDecs.getText().toString());
                homeWork.setCourseID(teacherCreateCourse.getCourseId());
                homeWork.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        tArrHomework = new TArrHomework();
                        tArrHomework.setTeacherNo(Constant.teacher.getTeacherNo());
                        tArrHomework.setHomeworkId(homeWork.getObjectId());
                        tArrHomework.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                Message message = handler.obtainMessage();
                                message.what = 1;
                                handler.sendMessage(message);
                            }
                        });
                    }
                });

                for (int i = 0 ;i < mPicList.size(); i++){
                        BmobRelation relation = new BmobRelation();
                        BmobFile bmobFile = new BmobFile(new java.io.File(mPicList.get(i)));
                        bmobFile.uploadblock(new UploadFileListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null){
                                file = new File();
                                file.setFile(bmobFile);
                                file.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        if(e == null){
                                            ToastUtils.showMessage(TeacherArrHomeworkAtivity.this,"作业发布成功");
                                            relation.add(file);
                                            homeWork.setFile(relation);
                                                    homeWork.update(new UpdateListener() {
                                                        @Override
                                                        public void done(BmobException e) {
                                                            intent = new Intent(mcontext,TeacherTabActivity.class);
                                                            startActivity(intent);
                                                        }
                                                    });
                                        }else{
                                            ToastUtils.showMessage(TeacherArrHomeworkAtivity.this,e.toString());
                                        }
                                    }
                                });
                            }else{
                                ToastUtils.showMessage(TeacherArrHomeworkAtivity.this,e.toString());
                            }
                        }
                    });
                }
                break;
        }
    }
}
