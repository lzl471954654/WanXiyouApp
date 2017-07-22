package com.lzl.wanxiyouapp.View;

import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzl.wanxiyouapp.R;
import com.lzl.wanxiyouapp.View.ViewInterface.ILessonFragment;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by LZL on 2017/7/22.
 */

public class LessonFragment extends Fragment implements ILessonFragment{
    FrameLayout addLesson_layout;


    @Override
    public void showLessonTable(List<List<Map<String, String>>> lesson_list) {

        List<TextView> textViewList = new LinkedList<>();

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
                    TextView textView = new TextView(getContext());
                    textView.setWidth((1080-getRealPixel(35))/7);
                    textView.setHeight(getRealPixel(110));
                    String name = map.get("lesson_name");
                    String time = map.get("lesson_time");
                    String teacher = map.get("lesson_teacher");
                    String place = map.get("lesson_place");
                    String data = "";
                    if(place!=null)
                        data = name+"\n"+"@"+teacher+"\n"+place;
                    else
                        data = name+"\n"+"@"+teacher;
                    textView.setText(data);
                    textView.setGravity(Gravity.CENTER);
                    //textView.setBackgroundResource(R.drawable.textcorner);
                    //textView.setBackgroundColor(chooseColor());
                    textView.setBackgroundResource(chooseColor());
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((1080-getRealPixel(35))/5,getRealPixel(110));
                    //layoutParams.leftMargin = (j)*70;
                    //layoutParams.topMargin = (i)*55;
                    layoutParams.setMargins(getRealPixel(getRealDP((1080-getRealPixel(35))/5)*j),getRealPixel(i*110),0,0);
                    //layoutParams.leftMargin = (j-1)*((1080-getRealPixel(35))/5);
                    //layoutParams.topMargin = (i-1)*getRealPixel(110);
                    //RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((j-1)*((1080-getRealPixel(35))/7),(i-1)*getRealPixel(55));
                    addLesson_layout.addView(textView,layoutParams);
                    textViewList.add(textView);
                    System.out.println("pix:"+(1080-getRealPixel(35))/5);
                }
            }
        }
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
        int[] color = {R.color.holo_blue_bright,R.color.holo_blue_dark,R.color.holo_blue_light,R.color.holo_red_light,R.color.holo_orange_light,R.color.};
        int number = (int)(Math.random()*5);
        return color[number];
    }
}
