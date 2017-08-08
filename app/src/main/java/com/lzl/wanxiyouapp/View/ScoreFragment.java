package com.lzl.wanxiyouapp.View;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopeer.cardstack.CardStackView;
import com.lzl.wanxiyouapp.Adapter.ScoresCardStackAdapter;
import com.lzl.wanxiyouapp.Moudle.ScoresMoudle;
import com.lzl.wanxiyouapp.Presenter.ScoresFragmentPresenter;
import com.lzl.wanxiyouapp.R;
import com.lzl.wanxiyouapp.View.ViewInterface.IScoresFragmen;
import com.lzl.wanxiyouapp.View.ViewInterface.ViewUpdateInterface;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by LZL on 2017/7/22.
 */

public class ScoreFragment extends Fragment implements IScoresFragmen,CardStackView.ItemExpendListener,ViewUpdateInterface{
    View root;
    ScoresFragmentPresenter presenter;
    ScoresMoudle moudle;
    CardStackView cardStackView;
    ProgressDialog progressDialog;
    static Integer[] color = {
            R.color.colorList_1,
            R.color.colorList_2,
            R.color.colorList_3,
            R.color.colorList_4,
            R.color.colorList_5,
            R.color.colorList_6,
            R.color.colorList_7,
            R.color.colorList_8,
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.score_fragment_layout,container,false);
        root = view;
        initView();
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ScoresFragmentPresenter(getContext());
        moudle  = new ScoresMoudle();
        moudle.setPresenter(presenter);
        presenter.setMoudle(moudle);
        presenter.setFragmen(this);

    }

    @Override
    public void updateView() {
        System.out.println("updateview");
        cardStackView.removeAllViews();
        //cardStackView.setAdapter(null);
        presenter.checkData();
    }

    public void initView()
    {
        cardStackView = (CardStackView)root.findViewById(R.id.scores_cardStackView);
        presenter.requestScoresList();
    }

    @Override
    public void onItemExpend(boolean expend) {

    }

    @Override
    public void refreshLayout(Map<String, List<Map<String, String>>> scoreMap) {
        ScoresCardStackAdapter adapter = new ScoresCardStackAdapter(getContext());
        adapter.updateData(Arrays.asList(color),scoreMap);
        cardStackView.setAdapter(adapter);
        cardStackView.setItemExpendListener(this);
    }

    @Override
    public void showProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("请稍后正在努力加载成绩数据");
        progressDialog.setCancelable(true);
        progressDialog.create();
        progressDialog.show();
    }

    @Override
    public void dissmisProgressDialog() {
        if(progressDialog!=null)
        {
            progressDialog.dismiss();
            progressDialog=null;
        }
    }

    @Override
    public void showRequestErrorMsg(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(msg);
        builder.setTitle("请求出错啦");
        builder.setPositiveButton("确定",null);
        builder.create().show();
    }

}
