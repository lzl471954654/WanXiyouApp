package com.lzl.wanxiyouapp.Presenter;

import android.content.Context;

import com.lzl.wanxiyouapp.Moudle.MoudleInterface.ILessonMoudle;
import com.lzl.wanxiyouapp.Moudle.LessonMoudle;
import com.lzl.wanxiyouapp.Presenter.PresenterInterface.ILessonPresenter;
import com.lzl.wanxiyouapp.View.ViewInterface.ILessonFragment;

import java.util.List;
import java.util.Map;

/**
 * Created by LZL on 2017/7/24.
 */

public class LessonPresenter implements ILessonPresenter {
    ILessonFragment fragment;
    ILessonMoudle moudle;
    Context context;

    public LessonPresenter(ILessonFragment fragment, Context context) {
        this.fragment = fragment;
        this.context = context;
        LessonMoudle moudle = new LessonMoudle(context);
        moudle.setPresenter(this);
        this.moudle = moudle;
    }

    @Override
    public void checkHasData() {
        moudle.checkHasData();
    }

    @Override
    public void hasData(List<List<Map<String, String>>> lists) {
        fragment.showLessonTable(lists);
    }

    @Override
    public void hasNoData() {
        requestLessonData();
    }

    @Override
    public void onRequestSuccess(List<List<Map<String,String>>> lists) {
        fragment.dissmisProgressDialog();
        fragment.showLessonTable(lists);
    }

    public void onRequestError(String msg)
    {
        fragment.dissmisProgressDialog();
        fragment.showErrorDialog(msg);
    }

    @Override
    public void requestLessonData() {
        fragment.showProgressDialog();
        moudle.requestLessonData();
    }
}
