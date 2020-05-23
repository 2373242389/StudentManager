package com.example.studentmanager.View;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.studentmanager.R;

public class BackTitleView extends FrameLayout {
    private TextView titleText;
    private ImageView back;
    public BackTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_backtitle_layout,this);
        titleText = (TextView) findViewById(R.id.title_text);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });
    }
    /**
     * 设置标题
     * @param text
     */
    public void setTitleText(String text) {
        titleText.setText(text);
    }
    /**
     * 隐藏返回按钮
     */
    public void hideBackImage(){
        back.setVisibility(View.GONE);
    }

}
