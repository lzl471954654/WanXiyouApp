package com.lzl.wanxiyouapp.Presenter.PresenterInterface;

import java.util.List;
import java.util.Map;

/**
 * Created by LZL on 2017/7/22.
 */

public interface IScorePresenter {

    public void requestScoresList();

    public void onRequestSuccess(Map<String,List<Map<String,String>>> scoreMap);

    public void onRequestError(String msg);

    public void checkDataSuccess(Map<String,List<Map<String,String>>> scoreMap);

    public void checkDataError();

    public void checkData();
}
