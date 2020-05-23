package com.example.studentmanager.entity;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class PPTFile extends BmobObject {
    private BmobFile file;
    public BmobFile getFile() {
        return file;
    }

    public void setFile(BmobFile file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "PPTFile{" +
                "file=" + file +
                '}';
    }
}
