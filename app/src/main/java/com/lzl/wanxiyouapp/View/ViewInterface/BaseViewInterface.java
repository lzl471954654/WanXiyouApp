package com.lzl.wanxiyouapp.View.ViewInterface;

/**
 * Created by LZL on 2017/7/25.
 */

public interface BaseViewInterface<T> {

    public void checkData();

    public void showProgressDialog();

    public void dissmisProgressDialog();

    public void onRequestSuccess(T data);

    public void onRequestError(String msg);

}
