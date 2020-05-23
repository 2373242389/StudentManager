package com.example.studentmanager.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

public class CoursePPTResource extends BmobObject {
    private String courseId;
    private BmobRelation file;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public BmobRelation getFile() {
        return file;
    }

    public void setFile(BmobRelation file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "CoursePPTResource{" +
                "courseId='" + courseId + '\'' +
                ", file=" + file +
                '}';
    }
}
