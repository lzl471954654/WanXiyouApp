package com.lzl.wanxiyouapp.Presenter.PresenterInterface;

import com.lzl.wanxiyouapp.Bean.ResponseData;
import com.lzl.wanxiyouapp.Bean.ResponseSingleData;

import java.util.List;

/**
 * Created by LZL on 2017/7/24.
 */

public interface IScoreSheetPresenter {

    public void requestSheetData();

    public void onRequestError(String msg);

    public void onRequestSuccess(List<List<String>> data);

    public void checkData();

    public void checkDataError();

    public void checkDataSuccess(List<List<String>> data);

}
