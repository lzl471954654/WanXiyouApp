package com.lzl.wanxiyouapp.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;

import com.loopeer.cardstack.CardStackView;
import com.lzl.wanxiyouapp.Adapter.ScoresCardStackAdapter;
import com.lzl.wanxiyouapp.Presenter.PresenterInterface.IStudyPlanPresenter;
import com.lzl.wanxiyouapp.Presenter.StudyPlanFragmentPresenter;
import com.lzl.wanxiyouapp.R;
import com.lzl.wanxiyouapp.View.ViewInterface.IStudyPlanFragment;
import com.lzl.wanxiyouapp.View.ViewInterface.ViewUpdateInterface;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Created by LZL on 2017/7/25.
 */

public class StudyPlanFragment extends Fragment implements IStudyPlanFragment, CardStackView.ItemExpendListener, ViewUpdateInterface {
    ProgressDialog progressDialog;
    AlertDialog.Builder errorDialog;
    CardStackView cardStackView;
    View root;
    Context mContext;
    IStudyPlanPresenter presenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EventBus.getDefault().register(this);
        presenter = new StudyPlanFragmentPresenter(this, getContext());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //EventBus.getDefault().unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.plan_fragment_layout, container, false);
        initView();
        return root;
    }

    public void initView() {
        cardStackView = (CardStackView) root.findViewById(R.id.plan_cardStackView);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        presenter.checkData();
    }

    @Override
    public void updateView() {
        cardStackView.removeAllViews();
        presenter.checkData();
    }

    @Override
    public void checkData() {
        presenter.checkData();
    }

    @Override
    public void showProgressDialog() {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("请稍后正在加载");
        progressDialog.setCancelable(true);
        progressDialog.create();
        progressDialog.show();
    }

    @Override
    public void dissmisProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    public void onRequestSuccess(List<List<Map<String, String>>> data) {
        ScoresCardStackAdapter adapter = new ScoresCardStackAdapter(mContext);
        adapter.updateData(Arrays.asList(color), data);
        cardStackView.setAdapter(adapter);
        cardStackView.setItemExpendListener(this);
    }

    @Override
    public void onItemExpend(boolean expend) {

    }

    @Override
    public void onRequestError(String msg) {
        errorDialog = new AlertDialog.Builder(mContext);
        errorDialog.setCancelable(false);
        errorDialog.setMessage(msg);
        errorDialog.setPositiveButton("确定", null);
        errorDialog.setNegativeButton("重试", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.requestData();
            }
        });
        errorDialog.create().show();
    }

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
}
