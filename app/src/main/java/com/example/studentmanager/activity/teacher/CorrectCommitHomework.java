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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studentmanager.Adapter.GridViewAdapter;
import com.example.studentmanager.Model.HomeworkModel;
import com.example.studentmanager.PictureSelectorConfig;
import com.example.studentmanager.R;
import com.example.studentmanager.Utils.MainConstant;
import com.example.studentmanager.Utils.ToastUtils;
import com.example.studentmanager.activity.PlusImageActivity;
import com.example.studentmanager.entity.Course;
import com.example.studentmanager.entity.File;
import com.example.studentmanager.entity.HomeWork;
import com.example.studentmanager.entity.StuCommitHomework;
import com.example.studentmanager.entity.TArrHomework;
import com.example.studentmanager.entity.TCorrectHomework;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.orhanobut.logger.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class CorrectCommitHomework extends AppCompatActivity implements View.OnClickListener{
    private Context mcontext;
    private GridView download_homeWork_grid;          //网格显示缩略图
    private Button buttonOK;            //发布按钮
    private ArrayList<String> showPicList =new ArrayList<>();
    private GridViewAdapter mGridViewAddImgAdapter; //展示上传的图片的适配器
    private TextView homeworkDecs;
    StuCommitHomework stuCommitHomework;
    private RadioButton score_choice;
    private RadioGroup score_group;
    private TCorrectHomework correctHomework;
    private String text;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_correct_homework);
        buttonOK = (Button)findViewById(R.id.teacher_correct_homework);
        homeworkDecs = (TextView) findViewById(R.id.teacher_correct_homework_decs);
        download_homeWork_grid = (GridView) findViewById(R.id.download_student_homework);
        mcontext = this;
        stuCommitHomework = (StuCommitHomework) getIntent().getSerializableExtra("stuCommitHomework");
        stuCommitHomework.getCommitHomeWorkId();
        score_group = (RadioGroup)findViewById(R.id.scoreGroup);
        buttonOK.setOnClickListener(this);
        setListener();
        initShow();
    }

    private void setListener(){
         score_group.setOnCheckedChangeListener(mylistener);
    }
    RadioGroup.OnCheckedChangeListener mylistener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            selectButton();
        }
    };
private void selectButton(){
        score_choice = (RadioButton)findViewById(score_group.getCheckedRadioButtonId());
        text = score_choice.getText().toString();
}

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    mGridViewAddImgAdapter = new GridViewAdapter(mcontext,showPicList,0);
                    download_homeWork_grid.setAdapter(mGridViewAddImgAdapter);
                    download_homeWork_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            viewPluImg(position,showPicList);
                        }
                    });
                    break;
            }
        }
    };
    private void initShow(){
        HomeworkModel.getInstance().findHomeworkById(stuCommitHomework.getHomeWorkID(), new FindListener<HomeWork>() {
            @Override
            public void done(List<HomeWork> list, BmobException e) {
                homeworkDecs.setText(list.get(0).getHomeworkDecs());
            }
        });
        HomeworkModel.getInstance().showHomeworkPicture(stuCommitHomework.getHomeWorkID(), new FindListener<File>() {
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
            //查看大图页面删除了图片
            ArrayList<String> toDeletePicList = data.getStringArrayListExtra(MainConstant.IMG_LIST); //要删除的图片的集合
            showPicList.clear();
            showPicList.addAll(toDeletePicList);
            mGridViewAddImgAdapter.notifyDataSetChanged();
        }
    }


    /*
     * Dialog对话框提示用户删除操作
     * position为删除图片位置
     */

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
                showPicList.add(compressPath); //把图片添加到将要上传的图片数组中
                mGridViewAddImgAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onClick(View v) {
                switch (v.getId()){
                    case R.id.teacher_correct_homework:
                        commitHomeworkId();
                        break;
                }
    }
    private void commitHomeworkId(){
            correctHomework = new TCorrectHomework();
            correctHomework.setHomeworkId(stuCommitHomework.getCommitHomeWorkId());
            correctHomework.setLevel(text);
            correctHomework.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null){
                        ToastUtils.showMessage(mcontext,"批改完成");
                    }else {
                        ToastUtils.showMessage(mcontext,e.toString());
                    }
                }
            });
    }
}
