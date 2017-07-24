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
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by LZL on 2017/7/22.
 */

public class ScoreFragment extends Fragment implements IScoresFragmen,CardStackView.ItemExpendListener{
    View root;
    ScoresFragmentPresenter presenter;
    ScoresMoudle moudle;
    CardStackView cardStackView;
    ProgressDialog progressDialog;
    Integer[] color = {
            R.color.primary_dark,
            R.color.holo_orange_light,
            R.color.holo_purple,
            R.color.holo_red_dark,
            R.color.primary_dark,
            R.color.holo_orange_light,
            R.color.holo_purple,
            R.color.holo_red_dark
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

    public void initView()
    {
        cardStackView = (CardStackView)root.findViewById(R.id.scores_cardStackView);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        cardStackView.setItemExpendListener(this);
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

    }

    @Override
    public void showProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("请稍后正在努力加载课表数据");
        progressDialog.setCancelable(false);
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
