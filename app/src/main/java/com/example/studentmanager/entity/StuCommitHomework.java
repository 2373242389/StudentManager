package com.example.studentmanager.entity;

import cn.bmob.v3.BmobObject;

public class StuCommitHomework extends BmobObject {
    private String stuNo;
    private String homeWorkID;
    private String commitHomeWorkId;
    private int commit_flag;

    public String getCommitHomeWorkId() {
        return commitHomeWorkId;
    }

    public void setCommitHomeWorkId(String commitHomeWorkId) {
        this.commitHomeWorkId = commitHomeWorkId;
    }

    public String getHomeWorkID() {
        return homeWorkID;
    }

    public void setHomeWorkID(String homeWorkID) {
        this.homeWorkID = homeWorkID;
    }

    public String getStuNo() {
        return stuNo;
    }

    public void setStuNo(String stuNo) {
        this.stuNo = stuNo;
    }

    public int getCommit_flag() {
        return commit_flag;
    }

    public void setCommit_flag(int commit_flag) {
        this.commit_flag = commit_flag;
    }

    @Override
    public String toString() {
        return "StuCommitHomework{" +
                "stuNo='" + stuNo + '\'' +
                ", homeWorkID='" + homeWorkID + '\'' +
                ", commit_flag=" + commit_flag +
                '}';
    }
}
