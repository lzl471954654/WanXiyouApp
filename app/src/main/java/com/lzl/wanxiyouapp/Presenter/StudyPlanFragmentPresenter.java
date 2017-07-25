package com.lzl.wanxiyouapp.Presenter;

import android.content.Context;

import com.lzl.wanxiyouapp.Moudle.MoudleInterface.IStudyMoudle;
import com.lzl.wanxiyouapp.Moudle.StudyPlanMoudle;
import com.lzl.wanxiyouapp.Presenter.PresenterInterface.IStudyPlanPresenter;
import com.lzl.wanxiyouapp.View.ViewInterface.IStudyPlanFragment;

import java.util.List;
import java.util.Map;

/**
 * Created by LZL on 2017/7/25.
 */

public class StudyPlanFragmentPresenter implements IStudyPlanPresenter{
    IStudyPlanFragment fragment;
    IStudyMoudle moudle;
    Context context;

    public StudyPlanFragmentPresenter(IStudyPlanFragment fragment, Context context) {
        this.fragment = fragment;
        this.context = context;
        this.moudle = new StudyPlanMoudle(context,this);
    }

    @Override
    public void checkData() {
        fragment.showProgressDialog();
        moudle.checkData();
    }

    @Override
    public void checkDataError() {
        requestData();
    }

    @Override
    public void checkDataSuccess(List<List<Map<String, String>>> data) {
        onRequestSuccess(data);
    }

    @Override
    public void onRequestSuccess(List<List<Map<String, String>>> data) {
        fragment.dissmisProgressDialog();
        fragment.onRequestSuccess(data);
    }

    @Override
    public void onRequestError(String msg) {
        fragment.dissmisProgressDialog();
        fragment.onRequestError(msg);
    }

    @Override
    public void requestData() {
        moudle.requestData();
    }
}
