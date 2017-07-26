package com.lzl.wanxiyouapp.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lzl.wanxiyouapp.R;

import java.util.List;
import java.util.Map;

/**
 * Created by LZL on 2017/7/16.
 */

public class ScoresListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Map<String,String>> scores_list;
    List<Map<String,String>> planList;
    public static final int SCORES_MODE = 1;
    public static final int PLAN_MDOE = 2;
    int Mode = SCORES_MODE;


    public ScoresListAdapter(List<Map<String,String>> planList,int Mode)
    {
        this.Mode = PLAN_MDOE;
        this.planList = planList;
    }

    public ScoresListAdapter(List<Map<String,String>> list)
    {
        Mode = SCORES_MODE;
        scores_list = list;
    }

    public static class ScoresViewHodler extends RecyclerView.ViewHolder
    {
        TextView name;          //课程名称
        TextView high_scores;   //最高分
        TextView daily_scores;  //平时成绩
        TextView type;          //课程类型
        TextView credit;        //学分
        TextView middle_scores; //期中成绩
        TextView final_scores;  //期末成绩
        public ScoresViewHodler(View view)
        {
            super(view);
            name = (TextView)view.findViewById(R.id.scores_lesson_name);
            high_scores = (TextView)view.findViewById(R.id.scores_lesson_score);
            daily_scores = (TextView)view.findViewById(R.id.scores_lesson_pingshi);
            type = (TextView)view.findViewById(R.id.scores_lesson_type);
            credit = (TextView)view.findViewById(R.id.scores_lesson_xf);
            middle_scores = (TextView)view.findViewById(R.id.scores_lesson_qzcj);
            final_scores = (TextView)view.findViewById(R.id.scores_lesson_qmcj);
        }

        public void onBind(int position,List<Map<String,String>> scores_list)
        {
            this.name.setText(scores_list.get(position).get("kcmc"));
            this.high_scores.setText("最高成绩："+scores_list.get(position).get("cj"));
            this.type.setText("课程类型："+scores_list.get(position).get("kcxz"));
            this.credit.setText("学分："+scores_list.get(position).get("xf"));
            this.middle_scores.setText("期中成绩：无");
            this.final_scores.setText("期末成绩："+scores_list.get(position).get("cj"));
            this.daily_scores.setText("平时成绩：无");
        }
    }

    public static class PlanViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;          //课程名称
        TextView high_scores;   //最高分
        TextView daily_scores;  //平时成绩
        TextView type;          //课程类型
        TextView credit;        //学分
        TextView middle_scores; //期中成绩
        TextView final_scores;  //期末成绩
        public PlanViewHolder(View view)
        {
            super(view);
            name = (TextView)view.findViewById(R.id.scores_lesson_name);
            high_scores = (TextView)view.findViewById(R.id.scores_lesson_score);
            daily_scores = (TextView)view.findViewById(R.id.scores_lesson_pingshi);
            type = (TextView)view.findViewById(R.id.scores_lesson_type);
            credit = (TextView)view.findViewById(R.id.scores_lesson_xf);
            middle_scores = (TextView)view.findViewById(R.id.scores_lesson_qzcj);
            final_scores = (TextView)view.findViewById(R.id.scores_lesson_qmcj);
        }

        public void onBind(int position,List<Map<String,String>> scores_list)
        {
            this.name.setText(scores_list.get(position).get("课程名称"));
            this.credit.setText("课程代码："+scores_list.get(position).get("课程代码"));
            this.type.setText("学分："+scores_list.get(position).get("学分"));
            this.high_scores.setText("周学时："+scores_list.get(position).get("周学时"));
            this.middle_scores.setText("考核方式："+scores_list.get(position).get("考核方式"));
            this.final_scores.setText("课程性质："+scores_list.get(position).get("课程性质"));
            this.daily_scores.setText("起始结束周："+scores_list.get(position).get("起始结束周"));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(Mode==SCORES_MODE)
        {
            ((ScoresViewHodler)holder).onBind(position,scores_list);
        }
        else if(Mode==PLAN_MDOE)
        {
            ((PlanViewHolder)holder).onBind(position,planList);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scores_list_item,parent,false);
        RecyclerView.ViewHolder holder = null;
        if(Mode==SCORES_MODE)
            holder = new ScoresViewHodler(view);
        else
            holder = new PlanViewHolder(view);
        return holder;
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if(Mode==SCORES_MODE)
            size = scores_list.size();
        if(Mode==PLAN_MDOE)
            size = planList.size();
        return size;
    }
}
