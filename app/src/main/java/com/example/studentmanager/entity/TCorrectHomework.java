package com.example.studentmanager.entity;

import cn.bmob.v3.BmobObject;

public class TCorrectHomework extends BmobObject {
   private String homeworkId;
   private String level;

    public String getHomeworkId() {
        return homeworkId;
    }

    public void setHomeworkId(String homeworkId) {
        this.homeworkId = homeworkId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "TCorrectHomework{" +
                "homeworkId='" + homeworkId + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
