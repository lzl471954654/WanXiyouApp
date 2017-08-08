package com.lzl.wanxiyouapp.Presenter.PresenterInterface;

import android.graphics.Bitmap;

/**
 * Created by LZL on 2017/8/1.
 */

public interface ILogonPresenter {

    public void Logon(String id,String pass,String cookie,String code);

    public void LogonWithoutCode(String id,String pass);

    public void onLogonSuccess();

    public void onLogonError(String error);

    public void onGetVerificationSuccess(Bitmap bitmap,String msg);

    public void getVerificationImage();
}
