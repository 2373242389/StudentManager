package com.example.studentmanager.Model.i;


import cn.bmob.newim.listener.BmobListener1;
import cn.bmob.v3.exception.BmobException;

/**
 * @author :smile
 * @project:QueryUserListener
 * @date :2016-02-01-16:23
 */
public abstract class QueryUserListener extends BmobListener1<Object> {

    public abstract void done(Object s, BmobException e);

    @Override
    protected void postDone(Object o, BmobException e) {
        done(o, e);
    }
}
