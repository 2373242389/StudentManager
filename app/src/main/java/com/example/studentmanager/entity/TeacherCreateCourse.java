package com.example.studentmanager.entity;

import cn.bmob.v3.BmobObject;

public class TeacherCreateCourse extends BmobObject {
    private String teacherNo;
    private String courseId;

    public String getTeacherNo() {
        return teacherNo;
    }

    public void setTeacherNo(String teacherNo) {
        this.teacherNo = teacherNo;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "TeacherCreateCourse{" +
                "teacherNo='" + teacherNo + '\'' +
                ", courseId='" + courseId + '\'' +
                '}';
    }
}
