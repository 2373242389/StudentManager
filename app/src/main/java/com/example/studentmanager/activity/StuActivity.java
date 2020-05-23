package com.example.studentmanager.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.studentmanager.Adapter.InventoryAdapter;
import com.example.studentmanager.Adapter.StudentAdapter;
import com.example.studentmanager.R;
import com.example.studentmanager.View.SlideRecyclerView;
import com.example.studentmanager.entity.Student;

import java.util.ArrayList;
import java.util.List;

public class StuActivity extends AppCompatActivity {
    private SlideRecyclerView recycler_view_list;
    private List<Student> students;
    private StudentAdapter stuAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_news_layout);
        recycler_view_list = (SlideRecyclerView)findViewById(R.id.news_list);
        recycler_view_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_inset));
        recycler_view_list.addItemDecoration(itemDecoration);
        students = new ArrayList<>();
        for (int i = 0; i < 20 ;i ++){
            Student stu = new Student();
            stu.setStuno("我爱你" + i);
            stu.setName("2016006" + i);
            students.add(stu);
        }
        stuAdapter = new StudentAdapter(this,students);
        recycler_view_list.setAdapter(stuAdapter);
        stuAdapter.setOnDeleteClickListener(new InventoryAdapter.OnDeleteClickLister() {
            @Override
            public void onDeleteClick(View view, int position) {
                students.remove(position);
                stuAdapter.notifyDataSetChanged();
                recycler_view_list.closeMenu();
            }
        });

    }
}
