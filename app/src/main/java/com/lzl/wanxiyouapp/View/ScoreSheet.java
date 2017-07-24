package com.lzl.wanxiyouapp.View;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lzl.wanxiyouapp.Presenter.PresenterInterface.IScoreSheetPresenter;
import com.lzl.wanxiyouapp.Presenter.ScoreSheetPresenter;
import com.lzl.wanxiyouapp.R;
import com.lzl.wanxiyouapp.View.ViewInterface.IScoreSheet;

import java.util.List;

/**
 * Created by LZL on 2017/7/24.
 */

public class ScoreSheet extends Fragment implements IScoreSheet {
    IScoreSheetPresenter presenter;
    View root;
    ProgressDialog progressDialog;
    AlertDialog.Builder errorDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ScoreSheetPresenter(getContext(),this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        root = inflater.inflate(R.layout.score_statistics_sheet,container,false);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initView();
        presenter.requestSheetData();
    }

    public void initView()
    {
        for(int i = 0;i<20;i++)
            sheetData[i] = (TextView)root.findViewById(sheet[i]);
    }

    public void showProgressDialog()
    {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("请稍后正在加载");
        progressDialog.setCancelable(false);
        progressDialog.create();
        progressDialog.show();
    }

    @Override
    public void onRrequestError(String msg) {
        dissmisDialog();
        showErrorDialog(msg);
    }

    public void dissmisDialog()
    {
        if(progressDialog!=null)
        {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    public void showErrorDialog(String msg) {
        errorDialog = new AlertDialog.Builder(getContext());
        errorDialog.setCancelable(false);
        errorDialog.setMessage(msg);
        errorDialog.setPositiveButton("确定",null);
        errorDialog.setNegativeButton("重试", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.requestSheetData();
            }
        });
        errorDialog.create().show();
    }


    public void onRequestSuccess(List<List<String>> data)
    {
        List<List<String>> lists = data;
        for(int i = 0;i<lists.size();i++)
        {
            for(int j = 0;j<lists.get(i).size();j++)
            {
                sheetData[i*5+j].setText(lists.get(i).get(j));
            }
        }
    }
    TextView[] sheetData = new TextView[20];
    int[] sheet = {
            R.id.sheet1,
            R.id.sheet2,
            R.id.sheet3,
            R.id.sheet4,
            R.id.sheet5,
            R.id.sheet6,
            R.id.sheet7,
            R.id.sheet8,
            R.id.sheet9,
            R.id.sheet10,
            R.id.sheet11,
            R.id.sheet12,
            R.id.sheet13,
            R.id.sheet14,
            R.id.sheet15,
            R.id.sheet16,
            R.id.sheet17,
            R.id.sheet18,
            R.id.sheet19,
            R.id.sheet20,
    };
}
