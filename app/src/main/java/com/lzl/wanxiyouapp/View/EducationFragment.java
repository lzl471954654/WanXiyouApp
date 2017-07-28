package com.lzl.wanxiyouapp.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.RotateAnimation;

import com.lzl.wanxiyouapp.Adapter.EducationPagerAdapter;
import com.lzl.wanxiyouapp.R;
import com.lzl.wanxiyouapp.View.ViewInterface.ViewUpdateInterface;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by LZL on 2017/7/27.
 */

public class EducationFragment extends Fragment implements View.OnClickListener {
    FloatingActionButton refreshButton;
    ViewPager viewPager;
    TabLayout tabLayout;
    View root;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.education_fragment,container,false);
        initView();
        refreshButton = (FloatingActionButton)root.findViewById(R.id.edu_refresh_button);
        refreshButton.setOnClickListener(this);
        //refreshButton.setAnimation(new RotateAnimation(0,359));
        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.edu_refresh_button:
            {
                EducationPagerAdapter adapter = (EducationPagerAdapter)viewPager.getAdapter();
                ViewUpdateInterface updateInterface = (ViewUpdateInterface)adapter.getItem(viewPager.getCurrentItem());
                updateInterface.updateView();
                break;
            }
        }
    }

    public void initView()
    {
        viewPager = (ViewPager)root.findViewById(R.id.main_menu_viewpager);
        tabLayout = (TabLayout)root.findViewById(R.id.main_menu_tabLayout);

        List<Fragment> fragments = new LinkedList<>();
        fragments.add(new LessonFragment());
        fragments.add(new ScoreFragment());
        fragments.add(new StudyPlanFragment());
        fragments.add(new ScoreSheet());
        viewPager.setAdapter(new EducationPagerAdapter(getChildFragmentManager(),fragments));
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);

    }
}
