package com.example.studentmanager.activity.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studentmanager.Fragment.TeacherNewsFragment;
import com.example.studentmanager.R;

public class TeacherNewsActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView friends;
    private ImageView add;
    private Intent intent;
    private TeacherNewsFragment seekMainFragment = new TeacherNewsFragment();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().add(R.id.container,seekMainFragment);
        friends = (ImageView)findViewById(R.id.friends);
        friends.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.friends:
                intent = new Intent(TeacherNewsActivity.this,TeacherContactActivity.class);
               startActivity(intent);
                break;
        }
    }
}
