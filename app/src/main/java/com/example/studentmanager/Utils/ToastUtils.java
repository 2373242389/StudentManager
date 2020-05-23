package com.example.studentmanager.Utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

    private static Toast toast;

    public static void showMessage(Context context, String msg){
        if (toast == null) {
            toast = Toast.makeText(context.getApplicationContext(),msg,Toast.LENGTH_SHORT);
        }else{
            toast.setText(msg);
        }

        toast.show();
    }
}