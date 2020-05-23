package com.example.studentmanager.entity;

import com.example.studentmanager.Model.HomeworkModel;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

public class TArrHomework extends BmobObject {
    private String teacherNo;
    private String  homeworkId;

    public String getTeacherNo() {
        return teacherNo;
    }

    public void setTeacherNo(String teacherNo) {
        this.teacherNo = teacherNo;
    }

    public String getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(String homeworkId) {
        this.homeworkId = homeworkId;
    }

    @Override
    public String toString() {
        return "TArrHomework{" +
                "teacherNo='" + teacherNo + '\'' +
                ", homeworkId='" + homeworkId + '\'' +
                '}';
    }
}
