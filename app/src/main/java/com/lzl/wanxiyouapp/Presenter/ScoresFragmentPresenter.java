package com.lzl.wanxiyouapp.Presenter;

import android.content.Context;
import android.os.Handler;

import com.lzl.wanxiyouapp.Moudle.MoudleInterface.IScoresMoudle;
import com.lzl.wanxiyouapp.Presenter.PresenterInterface.IScorePresenter;
import com.lzl.wanxiyouapp.View.ViewInterface.IScoresFragmen;

import java.util.List;
import java.util.Map;

/**
 * Created by LZL on 2017/7/22.
 */

public class ScoresFragmentPresenter implements IScorePresenter {
    IScoresFragmen fragmen;
    IScoresMoudle moudle;
    Context context;
    Handler handler;
    public ScoresFragmentPresenter(Context context){
        this.context = context;
        handler = new Handler(context.getMainLooper());
    };

    @Override
    public void checkDataError() {
        moudle.requestScoresList();
    }

    @Override
    public void checkDataSuccess(Map<String, List<Map<String, String>>> scoreMap) {
        fragmen.dissmisProgressDialog();
        fragmen.refreshLayout(scoreMap);
    }

    @Override
    public void checkData() {
        fragmen.showProgressDialog();
        moudle.checkData();
    }

    public IScoresFragmen getFragmen() {
        return fragmen;
    }

    public void setFragmen(IScoresFragmen fragmen) {
        this.fragmen = fragmen;
    }

    public IScoresMoudle getMoudle() {
        return moudle;
    }

    public void setMoudle(IScoresMoudle moudle) {
        this.moudle = moudle;
    }

    @Override
    public void onRequestError(final String msg) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                fragmen.dissmisProgressDialog();
                fragmen.showRequestErrorMsg(msg);
            }
        });
    }

    @Override
    public void onRequestSuccess(final Map<String, List<Map<String, String>>> scoreMap) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                fragmen.dissmisProgressDialog();
                fragmen.refreshLayout(scoreMap);

            }
        });
    }


    @Override
    public void requestScoresList() {
        fragmen.showProgressDialog();
        moudle.requestScoresList();
    }
}
