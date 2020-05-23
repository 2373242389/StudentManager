package com.example.studentmanager.entity;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class File extends BmobObject {
    private BmobFile file;
    

    public BmobFile getFiles() {
        return file;
    }

    public void setFile(BmobFile file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "File{" +
                "file=" + file +
                '}';
    }
}
