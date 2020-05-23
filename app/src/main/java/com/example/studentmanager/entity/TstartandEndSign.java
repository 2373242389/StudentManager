package com.example.studentmanager.entity;

import java.util.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

public class TstartandEndSign extends BmobObject {
    private String teacherNo;
    private String courseId;
    private int start_flag;
    private BmobDate start_Time;

    public BmobDate getStart_Time() {
        return start_Time;
    }

    public void setStart_Time(BmobDate start_Time) {
        this.start_Time = start_Time;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public int getStart_flag() {
        return start_flag;
    }

    public void setStart_flag(int start_flag) {
        this.start_flag = start_flag;
    }

    public String getTeacherNo() {
        return teacherNo;
    }

    public void setTeacherNo(String teacherNo) {
        this.teacherNo = teacherNo;
    }

    @Override
    public String toString() {
        return "TstartandEndSign{" +
                "teacherNo='" + teacherNo + '\'' +
                ", courseId='" + courseId + '\'' +
                ", start_flag=" + start_flag +
                '}';
    }
}
