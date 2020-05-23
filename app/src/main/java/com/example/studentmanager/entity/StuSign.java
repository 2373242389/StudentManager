package com.example.studentmanager.entity;

import java.io.Serializable;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;

public class StuSign extends BmobObject implements Serializable {
    private String stuNo;
    private String courseId;
    private String place;
    private int sign_flag;

    public String getStuNo() {
        return stuNo;
    }

    public void setStuNo(String stuNo) {
        this.stuNo = stuNo;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getSign_flag() {
        return sign_flag;
    }

    public void setSign_flag(int sign_flag) {
        this.sign_flag = sign_flag;
    }

    @Override
    public String toString() {
        return "StuSign{" +
                "stuNo='" + stuNo + '\'' +
                ", courseId='" + courseId + '\'' +
                ", place='" + place + '\'' +
                '}';
    }
}
