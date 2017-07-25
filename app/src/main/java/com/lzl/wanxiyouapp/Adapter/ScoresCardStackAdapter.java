package com.lzl.wanxiyouapp.Adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.StackAdapter;
import com.lzl.wanxiyouapp.R;

import java.util.List;
import java.util.Map;

import static android.R.attr.mode;

/**
 * Created by LZL on 2017/7/16.
 */

public class ScoresCardStackAdapter extends StackAdapter<Integer> {
    //Context mContext;、
    public static final int SCORES_MODE = 1;
    public static final int PLAN_MODE = 2;
    int Mode = SCORES_MODE;
    Map<String, List<Map<String, String>>> scoreMap;
    List<List<Map<String,String>>> planList;
    public ScoresCardStackAdapter(Context context)
    {
        super(context);
    }


    public void updateData(List data,Map<String, List<Map<String, String>>> scoreMap) {
        Mode = SCORES_MODE;
        this.scoreMap = scoreMap;
        updateData(data);
        System.out.println("Updata!");
    }

    public void updateData(List data,List<List<Map<String,String>>> planList)
    {
        Mode = PLAN_MODE;
        this.planList = planList;
        updateData(data);
    }

    @Override
    protected CardStackView.ViewHolder onCreateView(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scores_card_item,parent,false);
        CardStackView.ViewHolder holder = null;
        if(Mode==SCORES_MODE)
            holder = new CardViewHolder(view);
        else if(Mode==PLAN_MODE)
            holder = new PlanViewHolder(view);
        System.out.println("onCreateView");
        return holder;
    }

    @Override
    public void bindView(Integer data, int position, CardStackView.ViewHolder holder) {
        if(holder instanceof CardViewHolder)
        {
            CardViewHolder cardHolder = (CardViewHolder)holder;
            cardHolder.onBind(data,position,scoreMap);
        }
        if(holder instanceof PlanViewHolder)
        {
            PlanViewHolder holder1 = (PlanViewHolder)holder;
            holder1.onBind(data,position,planList);
        }
        System.out.println("bindView");
    }


    @Override
    public int getItemCount() {
        int size = 0;
        if(Mode==PLAN_MODE)
            size = planList.size();
        if(Mode==SCORES_MODE)
            size = scoreMap.size();
        return size;
    }

    @Override
    public int getItemViewType(int position) {
        System.out.println("getItemViewType");
        return super.getItemViewType(position);
    }

    @Override
    public Integer getItem(int position) {
        System.out.println("getItem");
        return super.getItem(position);
    }

    public static class PlanViewHolder extends CardStackView.ViewHolder
    {
        View root;
        FrameLayout cardTitle;
        RecyclerView scoreList;
        TextView titleText;
        public PlanViewHolder(View view)
        {
            super(view);
            root = view;
            cardTitle = (FrameLayout)view.findViewById(R.id.card_title);
            titleText = (TextView)view.findViewById(R.id.card_title_text);
            scoreList = (RecyclerView)view.findViewById(R.id.scores_list);
            System.out.println("CardViewHolder constructor");
        }

        public void onBind(Integer backgroundColorId,int position,List<List<Map<String,String>>> scoreMap)
        {
            String[] number =  {"一","二","三","四","五","六","七","八"};
            cardTitle.getBackground().setColorFilter(ContextCompat.getColor(getContext(),backgroundColorId), PorterDuff.Mode.SRC_IN);
            titleText.setText("第"+number[position]+"学期");
            ScoresListAdapter adapter = new ScoresListAdapter(scoreMap,PLAN_MODE);
            scoreList.setLayoutManager(new LinearLayoutManager(getContext()));
            scoreList.setAdapter(adapter);
            System.out.println("holder onBind");
        }

        @Override
        public void onItemExpand(boolean b) {
            scoreList.setVisibility(b ? View.VISIBLE : View.GONE);
            System.out.println("holder onItemExpand");
        }
    }

    public static class CardViewHolder extends CardStackView.ViewHolder
    {
        View root;
        FrameLayout cardTitle;
        RecyclerView scoreList;
        TextView titleText;
        public CardViewHolder(View view)
        {
            super(view);
            root = view;
            cardTitle = (FrameLayout)view.findViewById(R.id.card_title);
            titleText = (TextView)view.findViewById(R.id.card_title_text);
            scoreList = (RecyclerView)view.findViewById(R.id.scores_list);
            System.out.println("CardViewHolder constructor");
        }

        public void onBind(Integer backgroundColorId,int position,Map<String, List<Map<String, String>>> scoreMap)
        {
            cardTitle.getBackground().setColorFilter(ContextCompat.getColor(getContext(),backgroundColorId), PorterDuff.Mode.SRC_IN);
            titleText.setText(scoreMap.keySet().toArray()[position].toString());
            ScoresListAdapter adapter = new ScoresListAdapter(scoreMap.get(scoreMap.keySet().toArray()[position].toString()));
            scoreList.setLayoutManager(new LinearLayoutManager(getContext()));
            scoreList.setAdapter(adapter);
            System.out.println("holder onBind");
        }

        @Override
        public void onItemExpand(boolean b) {
            scoreList.setVisibility(b ? View.VISIBLE : View.GONE);
            System.out.println("holder onItemExpand");
        }
    }
}
