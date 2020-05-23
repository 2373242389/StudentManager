package com.example.studentmanager.entity;

import android.provider.ContactsContract;

import java.io.Serializable;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

public class Teacher extends BmobObject implements Serializable {
    private String teacherNo;
    private String name;
    private String password;
    private String department;
    private BmobRelation createCourse;
    private String sex;
    private String birthday;
    private String phone;
    private BmobRelation student;

    public BmobRelation getStudent() {
        return student;
    }

    public void setStudent(BmobRelation student) {
        this.student = student;
    }

    public BmobRelation getCreateCourse() {
        return createCourse;
    }

    public void setCreateCourse(BmobRelation createCourse) {
        this.createCourse = createCourse;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacherNo() {
        return teacherNo;
    }

    public void setTeacherNo(String teacherNo) {
        this.teacherNo = teacherNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherNo='" + teacherNo + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", department='" + department + '\'' +
                ", Course=" + createCourse +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
