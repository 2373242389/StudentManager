package com.example.studentmanager.entity;

import java.lang.ref.PhantomReference;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobRelation;

public class StuCoursePPT extends BmobObject {
    private String stuNo;
    private String CourseId;
    private BmobRelation pptFile;

    public String getStuNo() {
        return stuNo;
    }

    public void setStuNo(String stuNo) {
        this.stuNo = stuNo;
    }

    public String getCourseId() {
        return CourseId;
    }

    public void setCourseId(String courseId) {
        CourseId = courseId;
    }

    public BmobRelation getPptFile() {
        return pptFile;
    }

    public void setPptFile(BmobRelation pptFile) {
        this.pptFile = pptFile;
    }

    @Override
    public String toString() {
        return "StuCoursePPT{" +
                "stuNo='" + stuNo + '\'' +
                ", CourseId='" + CourseId + '\'' +
                ", pptFile=" + pptFile +
                '}';
    }
}
