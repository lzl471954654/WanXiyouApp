package com.lzl.wanxiyouapp.View;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.style.UpdateAppearance;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzl.wanxiyouapp.Presenter.PresenterInterface.IScoreSheetPresenter;
import com.lzl.wanxiyouapp.Presenter.ScoreSheetPresenter;
import com.lzl.wanxiyouapp.R;
import com.lzl.wanxiyouapp.View.ViewInterface.IScoreSheet;
import com.lzl.wanxiyouapp.View.ViewInterface.ViewUpdateInterface;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * Created by LZL on 2017/7/24.
 */
public class ScoreSheet extends Fragment implements IScoreSheet,ViewUpdateInterface {
    @BindView(R.id.sheet_bixiu_1) View bixiu1;
    @BindView(R.id.sheet_bixiu_2) View bixiu2;
    @BindView(R.id.sheet_xuanxiu_1) View xuanxiu1;
    @BindView(R.id.sheet_xuanxiu_2) View xuanxiu2;
    @BindView(R.id.sheet_other1) View other1;
    @BindView(R.id.sheet_other2) View other2;
    IScoreSheetPresenter presenter;
    View root;
    ProgressDialog progressDialog;
    AlertDialog.Builder errorDialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ScoreSheetPresenter(getContext(),this);
    }

    @Override
    public void updateView() {
        presenter.checkData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        root = inflater.inflate(R.layout.score_statistics_sheet,container,false);
        ButterKnife.bind(this,root);
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
        progressDialog.setCancelable(true);
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
        errorDialog.setCancelable(true);
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
        System.out.println(data);
        List<List<String>> lists = data;
        if(lists.size()>3)
        {
            for(int i = 0;i<lists.size();i++)
            {
                for(int j = 0;j<lists.get(i).size();j++)
                {
                    sheetData[i*5+j].setText(lists.get(i).get(j));
                }
            }
        }
        else
        {
            for(int i = 0;i<lists.size();i++)
            {
                for(int j = 0;j<lists.get(i).size();j++)
                {
                    if(i==2)
                    {
                        sheetData[(i+1)*5+j].setText(lists.get(i).get(j));
                    }
                    else
                        sheetData[i*5+j].setText(lists.get(i).get(j));
                }
            }
        }


        int hight = bixiu1.getHeight();
        System.out.println("hight :"+hight);
        double bixiu_hight = (new Double(lists.get(1).get(2)))/(new Double(lists.get(1).get(1)))*hight;
        RelativeLayout.LayoutParams params =(RelativeLayout.LayoutParams) bixiu2.getLayoutParams();
        params.height = (int) bixiu_hight;
        bixiu2.setLayoutParams(params);
        System.out.println("bixiu :"+bixiu_hight);
        if(lists.size()<4)
        {
            double xuanxiu_height = (new Double(lists.get(2).get(2)))/(new Double(lists.get(2).get(1)))*hight;
            params = (RelativeLayout.LayoutParams) xuanxiu2.getLayoutParams();
            params.height = (int) xuanxiu_height;
            xuanxiu2.setLayoutParams(params);


            params =(RelativeLayout.LayoutParams) other2.getLayoutParams();
            params.height =  0;
            other2.setLayoutParams(params);
            params = (RelativeLayout.LayoutParams)other1.getLayoutParams();
            params.height = 0;
            other1.setLayoutParams(params);

        }
        else
        {
            int i = 0;
            while(!lists.get(i).get(0).equals("选修课"))
                i++;
            double xuanxiu_height = (new Double(lists.get(i).get(2)))/(new Double(lists.get(i).get(1)))*hight;
            params = (RelativeLayout.LayoutParams) xuanxiu2.getLayoutParams();
            params.height = (int) xuanxiu_height;
            xuanxiu2.setLayoutParams(params);

            double other_height = (new Double(lists.get(2).get(2)))/(new Double(lists.get(2).get(1)))*hight;
            params =(RelativeLayout.LayoutParams) other2.getLayoutParams();
            params.height = (int) other_height;
            other2.setLayoutParams(params);
            System.out.println(other_height);
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
