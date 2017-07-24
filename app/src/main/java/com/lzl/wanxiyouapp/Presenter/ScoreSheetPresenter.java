package com.lzl.wanxiyouapp.Presenter;

import android.content.Context;

import com.lzl.wanxiyouapp.Bean.ResponseData;
import com.lzl.wanxiyouapp.Bean.ResponseSingleData;
import com.lzl.wanxiyouapp.Moudle.MoudleInterface.IScoreSheetMoudle;
import com.lzl.wanxiyouapp.Moudle.ScoreSheetMoudle;
import com.lzl.wanxiyouapp.Presenter.PresenterInterface.IScoreSheetPresenter;
import com.lzl.wanxiyouapp.View.ViewInterface.IScoreSheet;

import java.util.List;

/**
 * Created by LZL on 2017/7/24.
 */

public class ScoreSheetPresenter implements IScoreSheetPresenter {
    IScoreSheetMoudle moudle;
    IScoreSheet sheet;
    Context context;
    public ScoreSheetPresenter(Context context,IScoreSheet scoreSheet)
    {
        this.context = context;
        sheet = scoreSheet;
        moudle = new ScoreSheetMoudle(context);
        ((ScoreSheetMoudle)moudle).setPresenter(this);
    }

    @Override
    public void checkData() {
        moudle.checkData();
    }

    @Override
    public void checkDataSuccess(List<List<String>> data) {
        sheet.onRequestSuccess(data);
        sheet.dissmisDialog();
    }

    @Override
    public void checkDataError() {
        sheet.showProgressDialog();
        moudle.requestSheetData();
    }

    public void requestSheetData()
    {
        sheet.showProgressDialog();
        moudle.requestSheetData();
    }

    @Override
    public void onRequestError(String msg) {
        sheet.onRrequestError(msg);
    }

    @Override
    public void onRequestSuccess(List<List<String>> data) {
        sheet.dissmisDialog();
        sheet.onRequestSuccess(data);
    }
}
