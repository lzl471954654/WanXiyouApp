package com.lzl.wanxiyouapp.Presenter.PresenterInterface;

/**
 * Created by LZL on 2017/7/25.
 */

public interface BasePersenterInterface<T> {
    public void checkData();

    public void checkDataError();

    public void checkDataSuccess(T data);

    public void onRequestSuccess(T data);

    public void onRequestError(String msg);

    public void requestData();
}
