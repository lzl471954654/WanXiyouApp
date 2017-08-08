package com.lzl.wanxiyouapp.View;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzl.wanxiyouapp.Presenter.LessonPresenter;
import com.lzl.wanxiyouapp.Presenter.PresenterInterface.ILessonPresenter;
import com.lzl.wanxiyouapp.R;
import com.lzl.wanxiyouapp.View.ViewInterface.ILessonFragment;
import com.lzl.wanxiyouapp.View.ViewInterface.ViewUpdateInterface;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by LZL on 2017/7/22.
 */

public class LessonFragment extends Fragment implements ILessonFragment,ViewUpdateInterface{
    View root;
    FrameLayout addLesson_layout;
    ProgressDialog progressDialog;
    AlertDialog.Builder errorDialog;
    ILessonPresenter presenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new LessonPresenter(this,getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lessons_layout,container,false);
        root = view;
        initView();
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        presenter.checkHasData();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int first = calendar.getFirstDayOfWeek();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        System.out.println("dayOfWeek:"+dayOfWeek+"\t first:"+first);
        int day = dayOfWeek-first-1;
        if(day<0)
            day = 0;
        TextView weekView = (TextView)root.findViewById(week[day]);
        weekView.getBackground().setColorFilter(ContextCompat.getColor(getContext(),R.color.accent), PorterDuff.Mode.SRC_IN);
    }

    @Override
    public void updateView() {
        addLesson_layout.removeAllViews();
        presenter.checkHasData();
    }

    public void initView()
    {
        addLesson_layout = (FrameLayout)root.findViewById(R.id.addLesson_layout);
    }

    @Override
    public void showProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("请稍后课程表正在加载");
        progressDialog.setCancelable(false);
        progressDialog.create();
        progressDialog.show();
    }

    @Override
    public void showErrorDialog(String msg) {
        errorDialog = new AlertDialog.Builder(getContext());
        errorDialog.setCancelable(true);
        errorDialog.setMessage(msg);
        errorDialog.setPositiveButton("确定",null);
        errorDialog.setNegativeButton("重试", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.requestLessonData();
            }
        });
        errorDialog.create().show();
    }


    @Override
    public void dissmisProgressDialog() {
        if(progressDialog!=null)
        {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    public void showLessonTable(List<List<Map<String, String>>> lesson_list) {
        if(lesson_list==null)
            return;
        for(int i = 0;i<lesson_list.size();i++)
        {
            List<Map<String,String>> mapList = lesson_list.get(i);
            for(int j = 0;j<mapList.size();j++)
            {
                Map<String,String> map = mapList.get(j);
                if(map.get("empty").equals("0"))
                {
                    FrameLayout frameLayout = new FrameLayout(getActivity());
                    frameLayout.setBackgroundResource(R.drawable.lesson_card_backgraound);
                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams((1080-getRealPixel(35))/7,getRealPixel(110));
                    frameLayout.setLayoutParams(params);
                    frameLayout.setPadding(5,5,5,5);

                    TextView textView = new TextView(getContext());
                    ViewGroup.LayoutParams params1 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    textView.setLayoutParams(params1);
                    textView.setGravity(Gravity.CENTER);
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
                    textView.setTextColor(ContextCompat.getColor(getContext(),R.color.icons));

                    String lessonData = map.get("lesson");
                    lessonData = checkSame(lessonData);
                    textView.setText(lessonData);
                    textView.setBackgroundResource(R.drawable.lesson_card_backgraound);
                    textView.getBackground().setColorFilter(ContextCompat.getColor(getContext(),chooseColor()), PorterDuff.Mode.SRC_IN);
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams((1080-getRealPixel(35))/7,getRealPixel(110));
                    layoutParams.setMargins(getRealPixel(getRealDP((1080-getRealPixel(35))/7)*j),getRealPixel(i*110),0,0);
                    frameLayout.addView(textView);
                    addLesson_layout.addView(frameLayout,layoutParams);
                }
            }
        }
    }

    public String checkSame(String s)
    {
        String pre = s.substring(0,s.length()/2);
        String back = s.substring(s.length()/2,s.length());
        pre = pre.substring(0,2);
        back = back.substring(0,2);
        if(pre.equals(back))
            return s.substring(0,s.length()/2);
        else
            return s;
    }

    public int getRealPixel(int dp)
    {

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics(); //Get the screen dpi
        int DPI = displayMetrics.densityDpi;
        return dp*DPI/160;
    }

    public int getRealDP(int pixel)
    {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics(); //Get the screen dpi
        int DPI = displayMetrics.densityDpi;
        return pixel*160/DPI;
    }

    public int chooseColor()
    {
        int number = (int)(Math.random()*color.length);
        return color[number];
    }

    static int[] color = {
            R.color.holo_blue_bright,
            R.color.holo_blue_light,
            R.color.holo_orange_light,
            R.color.holo_green_light,
            R.color.color_1,
            R.color.color_3,
            R.color.color_6,
            R.color.color_9,
            R.color.color_13,
            R.color.color_14,
            R.color.color_15,
            R.color.color_16,
            R.color.color_17,
            R.color.color_18,
            R.color.color_20,
            R.color.color_21,
            R.color.color_22,
    };

    int week[] = {
            R.id.week1,
            R.id.week2,
            R.id.week3,
            R.id.week4,
            R.id.week5,
            R.id.week6,
            R.id.week7
    };
}
