package com.example.studentmanager.entity;

import cn.bmob.v3.BmobObject;

public class StuJoinCourse extends BmobObject {
    private String stuNo;
    private String CourseId;

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

    @Override
    public String toString() {
        return "JoinCourse{" +
                "stuNo='" + stuNo + '\'' +
                ", CourseId='" + CourseId + '\'' +
                '}';
    }
}
