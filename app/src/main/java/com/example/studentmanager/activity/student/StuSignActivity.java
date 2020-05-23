package com.example.studentmanager.activity.student;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.studentmanager.Model.CourseModel;
import com.example.studentmanager.R;
import com.example.studentmanager.Utils.Constant;
import com.example.studentmanager.Utils.ToastUtils;
import com.example.studentmanager.entity.Course;
import com.example.studentmanager.entity.StuJoinCourse;
import com.example.studentmanager.entity.StuSign;
import com.example.studentmanager.entity.TstartandEndSign;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class StuSignActivity extends AppCompatActivity implements View.OnClickListener {
    public LocationClient mLocationClient;
    private MapView mapView;
    private TextView position_text;
    private BaiduMap baiduMap;
    private boolean isFirstLocate = true;
    private EditText sign_command_et;
    private Button sign_btn;
    private Intent intent;
    StuJoinCourse stuJoinCourse;
    Course course;
    StringBuilder currentPosition;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        intent = getIntent();
        stuJoinCourse= (StuJoinCourse) intent.getSerializableExtra("course");
        stuCourseInfo();
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.stu_sign_layout);
        position_text = (TextView)findViewById(R.id.postion_text);
        sign_command_et = (EditText)findViewById(R.id.course_command_edit);
        sign_btn = (Button)findViewById(R.id.stu_start_btn);
        mapView = (MapView)findViewById(R.id.bmapView);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        requestLocation();
        judgeSignStart();
        sign_btn.setOnClickListener(this);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
           switch (msg.what){
               case 1:
                   course = (Course) msg.obj;
                   break;
           }
        }
    };
    public void stuCourseInfo(){
        CourseModel.getInstance().queryCourse(stuJoinCourse.getCourseId(), new FindListener<Course>() {
            @Override
            public void done(List<Course> list, BmobException e) {
                Message message = handler.obtainMessage();
                message.what = 1;
                message.obj = list.get(0);
                handler.sendMessage(message);
            }
        });
    }

    private void judgeSignStart() {
        CourseModel.getInstance().queryExistsCourse(stuJoinCourse.getCourseId(), new FindListener<TstartandEndSign>() {
            @Override
            public void done(List<TstartandEndSign> list, BmobException e) {
                if (e == null) {
                    if (list.get(0).getStart_flag() == 0) {
                        sign_btn.setEnabled(false);
                        sign_btn.setText("签到未开启");
                    }
                }
            }
        });

    }
    private void navigateTo(BDLocation location){
        if (isFirstLocate){
            LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }
        MyLocationData.Builder builder = new MyLocationData.Builder();
        builder.latitude(location.getLatitude());
        builder.longitude(location.getLongitude());
        MyLocationData locationData = builder.build();
        baiduMap.setMyLocationData(locationData);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    private void requestLocation(){
        initLocation();
        mLocationClient.start();
    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.stu_start_btn:
                if (TextUtils.equals(course.getCommand(),sign_command_et.getText().toString())) {
                    StuSign stuSign = new StuSign();
                    stuSign.setStuNo(Constant.student.getStuno());
                    stuSign.setCourseId(course.getObjectId());
                    stuSign.setPlace(String.valueOf(currentPosition));
                    stuSign.setSign_flag(1);
                    stuSign.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                sign_btn.setText("签到成功");
                                sign_btn.setEnabled(false);
                            }else{
                                ToastUtils.showMessage(StuSignActivity.this,"签到失败，请重试");
                            }
                        }
                    });
                }
                else{ToastUtils.showMessage(StuSignActivity.this,"签到码错误，请重新输入");}

        }
    }

    public class MyLocationListener implements BDLocationListener{

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            currentPosition = new StringBuilder();
            currentPosition.append("城市").append(bdLocation.getCity());
            currentPosition.append("区县").append(bdLocation.getDistrict());
            currentPosition.append("街道").append(bdLocation.getStreet());
            position_text.setText(currentPosition);
            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation || bdLocation.getLocType() == BDLocation.TypeNetWorkLocation){
                navigateTo(bdLocation);
            }
        }

    }
    private boolean isRange(){
        return true;
    }
}
