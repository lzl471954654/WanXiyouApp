package com.lzl.wanxiyouapp.View;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopeer.cardstack.CardStackView;
import com.lzl.wanxiyouapp.Adapter.ScoresCardStackAdapter;
import com.lzl.wanxiyouapp.Presenter.PresenterInterface.IStudyPlanPresenter;
import com.lzl.wanxiyouapp.Presenter.StudyPlanFragmentPresenter;
import com.lzl.wanxiyouapp.R;
import com.lzl.wanxiyouapp.View.ViewInterface.IStudyPlanFragment;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Created by LZL on 2017/7/25.
 */

public class StudyPlanFragment extends Fragment implements IStudyPlanFragment{
    ProgressDialog progressDialog;
    AlertDialog.Builder errorDialog;
    CardStackView cardStackView;
    View root;

    IStudyPlanPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new StudyPlanFragmentPresenter(this,getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.plan_fragment_layout,container,false);
        initView();
        return root;
    }

    public void initView()
    {
        cardStackView = (CardStackView)root.findViewById(R.id.plan_cardStackView);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        presenter.checkData();
    }

    @Override
    public void checkData() {
        presenter.checkData();
    }

    @Override
    public void showProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("请稍后正在加载");
        progressDialog.setCancelable(false);
        progressDialog.create();
        progressDialog.show();
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
    public void onRequestSuccess(List<List<Map<String, String>>> data) {
        ScoresCardStackAdapter adapter = new ScoresCardStackAdapter(getContext());
        adapter.updateData(Arrays.asList(color),data);
        cardStackView.setAdapter(adapter);
    }

    @Override
    public void onRequestError(String msg) {
        errorDialog = new AlertDialog.Builder(getContext());
        errorDialog.setCancelable(false);
        errorDialog.setMessage(msg);
        errorDialog.setPositiveButton("确定",null);
        errorDialog.setNegativeButton("重试", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.requestData();
            }
        });
        errorDialog.create().show();
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
}
