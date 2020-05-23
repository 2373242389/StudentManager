package com.example.studentmanager.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import com.example.studentmanager.R;

public class MySearchView extends android.widget.SearchView {
    public MySearchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_searchbar_layout,this);

    }
}
