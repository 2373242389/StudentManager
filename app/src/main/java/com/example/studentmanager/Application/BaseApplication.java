package com.example.studentmanager.Application;

import android.app.Application;

import cn.bmob.v3.Bmob;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this,"0dfaf8f6ed181173d1961ccde23b9da3");
    }
}
