package com.example.studentmanager.entity;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

//一个老师多个课程，一个课程多个作业
public class HomeWork extends BmobObject {
    private String CourseID;
    private String homeworkDecs;
    private BmobRelation file;
    public String getHomeworkDecs() {
        return homeworkDecs;
    }

    public String getCourseID() {
        return CourseID;
    }

    public void setCourseID(String courseID) {
        CourseID = courseID;
    }

    public void setHomeworkDecs(String homeworkDecs) {
        this.homeworkDecs = homeworkDecs;
    }

    public BmobRelation getFile() {
        return file;
    }

    public void setFile(BmobRelation file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "HomeWork{" +
                "CourseID=" + CourseID +
                ", homeworkDecs='" + homeworkDecs + '\'' +
                ", file=" + file +
                '}';
    }
}
