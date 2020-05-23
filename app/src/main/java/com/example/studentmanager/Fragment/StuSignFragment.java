package com.example.studentmanager.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.studentmanager.Adapter.CourseAdapter;
import com.example.studentmanager.Model.CourseModel;
import com.example.studentmanager.R;
import com.example.studentmanager.Utils.Constant;
import com.example.studentmanager.Utils.ToastUtils;
import com.example.studentmanager.activity.student.CourseSearchActivity;
import com.example.studentmanager.activity.student.StuSignActivity;
import com.example.studentmanager.entity.Course;
import com.example.studentmanager.entity.StuJoinCourse;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class StuSignFragment extends Fragment implements View.OnClickListener{
    private ArrayList<Course> courses = new ArrayList<>();
    private ImageView join_class;
    private RecyclerView join_class_list;
    private CourseAdapter courseAdapter;
    private Intent intent;
    private SwipeRefreshLayout refresh;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_join_class,container,false);
        join_class = (ImageView)v.findViewById(R.id.join_course);
        join_class.setOnClickListener(this);
        refresh = v.findViewById(R.id.refresh);
        join_class_list = (RecyclerView)v.findViewById(R.id.join_class_list);
        join_class_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false));
        showJoinCourse();
        setSwipe();
        return v;
    }
private void setSwipe(){
        refresh.setColorSchemeResources(R.color.color_4d,R.color.tab_color_s);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showJoinCourse();
                refresh.setRefreshing(false);
            }
        });

}
    private void showJoinCourse(){

        CourseModel.getInstance().queryJoinCourse(Constant.student.getStuno(), new FindListener<StuJoinCourse>() {
            @Override
            public void done(List<StuJoinCourse> list, BmobException e) {
                if (list.size() != 0){
                    courseAdapter = new CourseAdapter(getActivity(), list);
                join_class_list.setAdapter(courseAdapter);
                courseAdapter.setOnItemClickListener(new CourseAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        intent = new Intent(getActivity(), StuSignActivity.class);
                        intent.putExtra("course", list.get(position));
                        startActivity(intent);
                    }

                });
            }else{
                    ToastUtils.showMessage(getActivity(),"暂无加入任何课程");
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.join_course:
                Intent intent = new Intent(getActivity(),CourseSearchActivity.class);
                startActivity(intent);
                break;
        }

    }
}
