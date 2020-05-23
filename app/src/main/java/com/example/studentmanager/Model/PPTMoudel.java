package com.example.studentmanager.Model;

import com.example.studentmanager.entity.Course;
import com.example.studentmanager.entity.CoursePPTResource;
import com.example.studentmanager.entity.PPTFile;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class PPTMoudel {
    private static PPTMoudel ourInstance = new PPTMoudel();
    public static PPTMoudel getInstance(){return ourInstance;}
    private PPTMoudel(){}

    public void queryExists(String courseId, FindListener<CoursePPTResource> listener){
        BmobQuery<CoursePPTResource> query = new BmobQuery<>();
        query.addWhereEqualTo("courseId",courseId);
        query.findObjects(new FindListener<CoursePPTResource>() {
            @Override
            public void done(List<CoursePPTResource> list, BmobException e) {
                listener.done(list,e);
            }
        });
    }
    public void queryCoursePPT(String courseId, FindListener<PPTFile> listener){
        BmobQuery<PPTFile> query = new BmobQuery<>();
        BmobQuery<CoursePPTResource>  query1 = new BmobQuery<>();
        query1.addWhereEqualTo("courseId",courseId);
        query1.findObjects(new FindListener<CoursePPTResource>() {
            @Override
            public void done(List<CoursePPTResource> list, BmobException e) {
                CoursePPTResource course = new CoursePPTResource();
                if (list.size() > 0) {
                    course.setObjectId(list.get(0).getObjectId());
                    query.addWhereRelatedTo("file", new BmobPointer(course));
                    query.findObjects(new FindListener<PPTFile>() {
                        @Override
                        public void done(List<PPTFile> pptFiles, BmobException e) {
                            listener.done(pptFiles, e);
                        }
                    });
                }
            }
        });

    }
}
