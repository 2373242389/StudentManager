package com.example.studentmanager.activity.student;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanager.Adapter.PPTAdapter;
import com.example.studentmanager.Model.PPTMoudel;
import com.example.studentmanager.R;
import com.example.studentmanager.Utils.Constant;
import com.example.studentmanager.Utils.ToastUtils;
import com.example.studentmanager.View.BackTitleView;
import com.example.studentmanager.entity.PPTFile;
import com.example.studentmanager.entity.StuCoursePPT;
import com.example.studentmanager.entity.StuJoinCourse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class CoursePPTShowActivity extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView course_ppt_list;
    private Button okDownload;
    private BackTitleView ppt_title;
    StuJoinCourse stuJoinCourse;
    Context context;
    private List<PPTFile> checkList = new ArrayList<>();
    private PPTAdapter pptAdapter;
    private String[] urls;
    StuCoursePPT stuCoursePPT;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_ppt_layout);
        context = this;
        ppt_title = (BackTitleView)findViewById(R.id.ppt_title);
        ppt_title.setTitleText("课程课件下载");
        course_ppt_list = (RecyclerView) findViewById(R.id.course_ppt);
        course_ppt_list.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false));
        okDownload = (Button)findViewById(R.id.okDownload);
        okDownload.setOnClickListener(this);
        stuJoinCourse = (StuJoinCourse) getIntent().getSerializableExtra("course");
        initPPT();
    }

    private void initPPT(){
            PPTMoudel.getInstance().queryCoursePPT(stuJoinCourse.getCourseId(), new FindListener<PPTFile>() {
                @Override
                public void done(List<PPTFile> list, BmobException e) {
                    if(list.size() > 0) {
                        pptAdapter = new PPTAdapter(context, list);
                        course_ppt_list.setAdapter(pptAdapter);
                        pptAdapter.setOnItemClickListener(new PPTAdapter.OnItemClickListener() {
                            @Override
                            public void onClick(View view, int position) {
                                Log.e("点击选中的pos：", position + "");
                                if (checkList.contains(list.get(position))) {
                                    checkList.remove(list.get(position));
                                } else {
                                    checkList.add(list.get(position));
                                }
                            }
                        });
                    }else{
                        ToastUtils.showMessage(context,"该课程暂无课件");
                    }
                }
            });
    }

    private void downloadFile(BmobFile file){
        //允许设置下载文件的存储路径，默认下载文件的目录为：context.getApplicationContext().getCacheDir()+"/bmob/"
        File saveFile = new File(Environment.getExternalStorageDirectory(),file.getFilename());
        file.download(saveFile, new DownloadFileListener() {
            @Override
            public void onStart() {
                ToastUtils.showMessage(context,"开始下载...");
            }

            @Override
            public void done(String savePath,BmobException e) {
                if(e==null){
                    ToastUtils.showMessage(context,"下载成功,保存路径:"+savePath);
                }else{
                    ToastUtils.showMessage(context,"下载失败："+e.getErrorCode()+","+e.getMessage());
                }
            }
            @Override
            public void onProgress(Integer value, long newworkSpeed) {
                Log.i("bmob","下载进度："+value+","+newworkSpeed);
            }

        });
    }

    private void Alldownload(){
        stuCoursePPT = new StuCoursePPT();
        stuCoursePPT.setStuNo(Constant.student.getStuno());
        stuCoursePPT.setCourseId(stuJoinCourse.getCourseId());
        stuCoursePPT.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
            if (e == null){
                for (int i = 0; i<checkList.size();i++){
                    downloadFile(checkList.get(i).getFile());
                }
            }
            }
        });

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.okDownload:
                Alldownload();
                break;
        }
    }
}
