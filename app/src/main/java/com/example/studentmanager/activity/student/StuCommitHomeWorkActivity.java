package com.example.studentmanager.activity.student;

import android.app.AlertDialog;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studentmanager.Adapter.GridViewAdapter;
import com.example.studentmanager.Model.CourseModel;
import com.example.studentmanager.Model.HomeworkModel;
import com.example.studentmanager.PictureSelectorConfig;
import com.example.studentmanager.R;
import com.example.studentmanager.Utils.Constant;
import com.example.studentmanager.Utils.MainConstant;
import com.example.studentmanager.Utils.ToastUtils;
import com.example.studentmanager.activity.PlusImageActivity;
import com.example.studentmanager.activity.teacher.TeacherArrHomeworkAtivity;
import com.example.studentmanager.entity.Course;
import com.example.studentmanager.entity.File;
import com.example.studentmanager.entity.HomeWork;
import com.example.studentmanager.entity.StuCommitHomework;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.orhanobut.logger.Logger;

import java.io.Serializable;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class StuCommitHomeWorkActivity extends AppCompatActivity implements View.OnClickListener{
    private Context mcontext;
    private GridView showHomeworkGrid;
    private GridView gridView;              //网格显示缩略图
    private Button buttonPublish;            //发布按钮
    private ArrayList<String> mPicList =new ArrayList<>();
    private GridViewAdapter mGridViewAddImgAdapter; //展示上传的图片的适配器
    private ArrayList<String> showPicList = new ArrayList<>();
    private GridViewAdapter homeworkImgAdapter;
    String homeWorkID;
    private TextView homework_decs;
    private File file;
    private Button stu_commit_homework;
    HomeWork commithomeWork;
    StuCommitHomework homework;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stu_homework_commit);
        stu_commit_homework = (Button)findViewById(R.id.stu_commit_homework);
        showHomeworkGrid = (GridView)findViewById(R.id.download_homework);
        gridView = (GridView) findViewById(R.id.stu_commitHomework_grid);
        homework_decs = (TextView)findViewById(R.id.showHomework_decs);
        homework = (StuCommitHomework) getIntent().getSerializableExtra("homework");
        homeWorkID = homework.getHomeWorkID();
        HomeworkModel.getInstance().byHomeworkIDFindHomework(homeWorkID, new FindListener<HomeWork>() {
            @Override
            public void done(List<HomeWork> list, BmobException e) {
                homework_decs.setText(list.get(0).getHomeworkDecs());
            }
        });
        mcontext = this;
        initGridView();
        initHomework();
        stu_commit_homework.setOnClickListener(this);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message message){
                switch (message.what){
                    case 1:
                        homeworkImgAdapter = new GridViewAdapter(mcontext,showPicList,0);
                        showHomeworkGrid.setAdapter(homeworkImgAdapter);
                        showHomeworkGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                             viewPluImg(position,showPicList);
                            }
                        });
                        break;
                    case 2:
                        HomeworkModel.getInstance().findAndCommithomework(Constant.student.getStuno(), homeWorkID, new FindListener<StuCommitHomework>() {
                            @Override
                            public void done(List<StuCommitHomework> list, BmobException e) {
                              list.get(0).setCommitHomeWorkId(commithomeWork.getObjectId());
                              list.get(0).setCommit_flag(1);
                              list.get(0).update(new UpdateListener() {
                                  @Override
                                  public void done(BmobException e) {
                                      intent = new Intent(mcontext,StuTabActivity.class);
                                      startActivity(intent);
                                  }
                              });
                            }
                        });
                }
        }
    };
    private void initHomework(){

        HomeworkModel.getInstance().showHomeworkPicture(homeWorkID, new FindListener<File>() {
            @Override
            public void done(List<File> list, BmobException e) {
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).getFiles().download(new DownloadFileListener() {
                        @Override
                        public void onFinish() {
                            Message message = handler.obtainMessage();
                            message.what = 1;
                            handler.sendMessage(message);
                        }

                        @Override
                        public void done(String s, BmobException e) {
                            showPicList.add(s);
                        }
                        @Override
                        public void onProgress(Integer integer, long l) {

                        }
                    });
                }
            }
        });
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
        gridView.setAdapter(mGridViewAddImgAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == parent.getChildCount() - 1) {
                    if(mPicList.size() == MainConstant.MAX_SELECT_PIC_NUM){
                        viewPluImg(position,mPicList);
                    }else{
                        selectPic(MainConstant.MAX_SELECT_PIC_NUM-mPicList.size());
                    }
                }else {
                       viewPluImg(position,mPicList);
                }
                }
            });
    }

        private void selectPic(int maxTotal) {
            PictureSelectorConfig.initMultiConfig(this, maxTotal);
        }

    private void viewPluImg(int position,ArrayList<String> list) {
        Intent intent = new Intent(mcontext, PlusImageActivity.class);
        intent.putStringArrayListExtra(MainConstant.IMG_LIST, list);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
          case R.id.stu_commit_homework:
                commithomeWork = new HomeWork();
            HomeworkModel.getInstance().findHomeworkById(homework.getHomeWorkID(), new FindListener<HomeWork>() {
               @Override
               public void done(List<HomeWork> list, BmobException e) {
                   CourseModel.getInstance().findCourseById(list.get(0).getCourseID(), new FindListener<Course>() {
                       @Override
                       public void done(List<Course> list, BmobException e) {
                           commithomeWork.setCourseID(list.get(0).getObjectId());
                       }
                   });
               }
           });
                commithomeWork.setHomeworkDecs(homework_decs.getText().toString());
                commithomeWork.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {

                    }
                });
                if (mPicList.size() != 0){
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
                                                ToastUtils.showMessage(StuCommitHomeWorkActivity.this,"作业提交成功");
                                                relation.add(file);
                                                commithomeWork.setFile(relation);
                                                commithomeWork.update(new UpdateListener() {
                                                    @Override
                                                    public void done(BmobException e) {
                                                        Message message = handler.obtainMessage();
                                                        message.what = 2;
                                                        handler.sendMessage(message);

                                                    }
                                                });
                                            }else{
                                                ToastUtils.showMessage(StuCommitHomeWorkActivity.this,e.toString());
                                            }
                                        }
                                    });
                                }else{
                                    ToastUtils.showMessage(StuCommitHomeWorkActivity.this,e.toString());
                                }
                            }
                        });
                    }
                }else{
                    ToastUtils.showMessage(mcontext,"请不要上传空文件");
                }


        }
    }
}
