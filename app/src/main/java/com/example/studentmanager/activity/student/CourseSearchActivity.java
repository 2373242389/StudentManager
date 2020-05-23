package com.example.studentmanager.activity.student;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanager.Adapter.CourseAdapter;
import com.example.studentmanager.Adapter.SearchCourseAdapter;
import com.example.studentmanager.R;
import com.example.studentmanager.entity.Course;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class CourseSearchActivity extends AppCompatActivity{
    private SearchView courseSearch;
    private RecyclerView search_result;
    private SearchCourseAdapter courseAdapter;
    private ArrayList<Course> filterCourses;
    private ArrayList<String> courseNames = new ArrayList<>();
    private ArrayList<Course> courses = new ArrayList<>();
    private Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_search_layout);
        courseSearch = (SearchView)findViewById(R.id.searcher);
        courseSearch.setIconified(false);
        courseSearch.setQueryHint("请输入关键字");
        search_result = (RecyclerView)findViewById(R.id.search_result);
        final BmobQuery<Course> courseBmobQuery = new BmobQuery<Course>();
        courseBmobQuery.addQueryKeys("CourseName,teacherName,desc");
        courseBmobQuery.findObjects(new FindListener<Course>() {
            @Override
            public void done(List<Course> list, BmobException e) {
                if (e == null){
                    Message message = handler.obtainMessage();
                    message.what = 0;
                    message.obj = list;
                    handler.sendMessage(message);
            }
            }
        });
        search_result.setLayoutManager(new LinearLayoutManager(CourseSearchActivity.this, LinearLayoutManager.VERTICAL, false));
        courseSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterCourses = filter(courses,newText);
                if (!TextUtils.isEmpty(newText)){
                    courseAdapter = new SearchCourseAdapter(CourseSearchActivity.this,filterCourses);
                    search_result.setAdapter(courseAdapter);
                    courseAdapter.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            intent = new Intent(CourseSearchActivity.this, AddCourseActivity.class);
                            startActivity(intent);
                        }
                    });
                }
                    return true;
            }
        });
    }
    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:courses = (ArrayList<Course>) msg.obj;
                for (int i = 0;i < courses.size(); i++) {
                    courseNames.add(courses.get(i).getCourseName());
                }
                break;
            }
        }
    };

    private ArrayList<Course> filter(ArrayList<Course> filterCourse,String text){
        filterCourses = new ArrayList<>();
        for (String name:courseNames){
            if (name.contains(text)){
                for (int i = 0; i < courses.size();i ++) {
                    if (courses.get(i).getCourseName()== name){
                        filterCourses.add(courses.get(i));
                    }
                }
            }
        }
        return filterCourses;
    }

}
