//导航按钮和页面的联动
package com.example.studentmanager.activity.teacher;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.studentmanager.Fragment.BlankFragment;
import com.example.studentmanager.Fragment.StuSetFragment;
import com.example.studentmanager.Fragment.THomeWorkFragment;
import com.example.studentmanager.Fragment.TeacherNewsFragment;
import com.example.studentmanager.Fragment.TeacherRegisterSignFragment;
import com.example.studentmanager.R;
import com.example.studentmanager.Utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class TeacherTabActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private RadioGroup mTabRadioGroup;

    private List<Fragment> mFragments;
    private FragmentPagerAdapter mAdapter;
    private TeacherRegisterSignFragment teacherRegisterSignFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab_layout);
        initView();
    }


    private void initView(){
        mViewPager = findViewById(R.id.frament_vp);
        mTabRadioGroup = findViewById(R.id.tabs_rg);
        mFragments = new ArrayList<>(4);
        mFragments.add(new TeacherRegisterSignFragment());
        mFragments.add(new THomeWorkFragment());
        mFragments.add(new TeacherNewsFragment());
        mFragments.add(new BlankFragment());
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),mFragments);//得到Fragment
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.addOnPageChangeListener(mPageChageListener);
        mTabRadioGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewPager.removeOnPageChangeListener(mPageChageListener);
        Constant.teacher = null;
    }
    private ViewPager.OnPageChangeListener mPageChageListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            RadioButton radioButton = (RadioButton)mTabRadioGroup.getChildAt(position);
            radioButton.setChecked(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    private RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
            for (int i = 0; i < radioGroup.getChildCount(); i++){
                if (radioGroup.getChildAt(i).getId() == checkedId){
                    mViewPager.setCurrentItem(i);
                    return;
                }
            }
        }
    };
    private class MyFragmentPagerAdapter extends FragmentPagerAdapter{
        private List<Fragment> mList;

        public MyFragmentPagerAdapter( FragmentManager fm,List<Fragment> list) {
            super(fm);
            this.mList = list;
        }

        @Override
        public Fragment getItem(int position) {
            return this.mList == null ? null : this.mList.get(position);
        }

        @Override
        public int getCount() {
            return this.mList == null ? 0: this.mList.size();
        }
    }
}
