package com.lzl.wanxiyouapp.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by LZL on 2017/7/27.
 */

public class EducationPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> fragmentList;
    FragmentManager manager;
    String[] titles = {"我的课表","我的成绩","培养计划","学分统计"};
    public EducationPagerAdapter(FragmentManager manager,List<Fragment> list)
    {
        super(manager);
        this.manager = manager;
        if(list==null)
            fragmentList = new LinkedList<>();
        else
            this.fragmentList = list;
    }


    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
