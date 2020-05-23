package com.example.studentmanager.activity.teacher;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanager.Adapter.PPTAdapter;
import com.example.studentmanager.Adapter.TeacherPPTAdapter;
import com.example.studentmanager.Model.PPTMoudel;
import com.example.studentmanager.R;
import com.example.studentmanager.Utils.ToastUtils;
import com.example.studentmanager.View.SlideRecyclerView;
import com.example.studentmanager.entity.CoursePPTResource;
import com.example.studentmanager.entity.PPTFile;
import com.example.studentmanager.entity.TeacherCreateCourse;
import com.orhanobut.logger.Logger;


import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadBatchListener;

public class UploadCourseFileActivity  extends AppCompatActivity implements View.OnClickListener{
    private SlideRecyclerView ppt_list;
    private TeacherPPTAdapter adapter;
    private ArrayList<String> ppts = new ArrayList<>();
    private Button add_ppt;
    private String paths[];
    private Button sure_upload;
    CoursePPTResource coursePPTResource;
    String path;
    Context context;
    PPTFile pptFile;
    private TeacherCreateCourse teacherCreateCourse;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ppt_upload);
        context = this;
        teacherCreateCourse = (TeacherCreateCourse) getIntent().getSerializableExtra("course");
        ppt_list = (SlideRecyclerView) findViewById(R.id.ppt_list);
        ppt_list.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false));
        add_ppt = (Button)findViewById(R.id.add_ppt);
        sure_upload = (Button)findViewById(R.id.sure_upload);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_inset));
        ppt_list.addItemDecoration(itemDecoration);
        add_ppt.setOnClickListener(this);
        sure_upload.setOnClickListener(this);
}

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_ppt:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                //intent.setType(“image/*”);//选择图片
                //intent.setType(“audio/*”); //选择音频
                //intent.setType(“video/*”); //选择视频 （mp4 3gp 是android支持的视频格式）
                //intent.setType(“video/*;image/*”);//同时选择视频和图片
                intent.setType("*/*");//无类型限制
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                startActivityForResult(intent, 1);
                break;
            case R.id.sure_upload:
                PPTMoudel.getInstance().queryExists(teacherCreateCourse.getCourseId(), new FindListener<CoursePPTResource>() {
                    @Override
                    public void done(List<CoursePPTResource> list, BmobException e) {
                        if (list.size() == 0){
                            coursePPTResource  = new CoursePPTResource();
                            coursePPTResource.setCourseId(teacherCreateCourse.getCourseId());
                            coursePPTResource.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                }
                            });
                        }
                        else{
                            coursePPTResource = list.get(0);
                        }
                    }
                });

                BmobFile.uploadBatch(paths, new UploadBatchListener() {
                    @Override
                    public void onSuccess(List<BmobFile> list, List<String> urls) {
                        for (int i = 0;i < list.size();i++){
                            pptFile = new PPTFile();
                            pptFile.setFile(list.get(i));
                            pptFile.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                if (e == null){
                                    BmobRelation relation = new BmobRelation();
                                    relation.add(pptFile);
                                    coursePPTResource.setFile(relation);
                                    coursePPTResource.update(new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                        if (e == null){
                                        }
                                        }
                                    });
                                }
                                }
                            });
                            if ( list.size() == paths.length){
                                ToastUtils.showMessage(context,"上传完成");
                            }
                        }
                    }

                    @Override
                    public void onProgress(int i, int i1, int i2, int i3) {

                    }

                    @Override
                    public void onError(int i, String s) {

                    }
                });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
                ClipData clipData = data.getClipData();
                if (clipData != null) {
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        path = getPath(this, clipData.getItemAt(i).getUri());
                        ppts.add(path);
                        Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
                    }
                }else{
                    path = getPath(this, uri);
                    ppts.add(path);
                }
                paths = ppts.toArray(new String[ppts.size()]);
                adapter = new TeacherPPTAdapter(context,ppts);
                ppt_list.setAdapter(adapter);
                adapter.setOnDeleteClickListener(new TeacherPPTAdapter.OnDeleteClickLister() {
                    @Override
                    public void onDeleteClick(View view, int position) {
                        adapter.delete(position);
                        ToastUtils.showMessage(context, String.valueOf(ppts.size()));
                        for (int i=0;i < ppts.size();i++){
                            paths[i] = ppts.get(i);
                        }
                    }
                });

            } else {//4.4以下下系统调用方法
                path = getRealPathFromURI(uri);
            }
        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if(null!=cursor&&cursor.moveToFirst()){;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
            cursor.close();
        }
        return res;
    }

    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */
    @SuppressLint("NewApi")
    public String getPath(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public String getDataColumn(Context context, Uri uri, String selection,
                                String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
