package com.lzl.wanxiyouapp.Presenter.PresenterInterface;

import java.util.List;
import java.util.Map;

/**
 * Created by LZL on 2017/7/24.
 */

public interface ILessonPresenter {

    public void onRequestSuccess(List<List<Map<String,String>>> lists);

    public void onRequestError(String msg);

    public void requestLessonData();

    public void checkHasData();

    public void hasData(List<List<Map<String,String>>> lists);

    public void hasNoData();
}
