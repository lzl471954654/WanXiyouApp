package com.lzl.wanxiyouapp.Presenter;

import android.graphics.Bitmap;

import com.lzl.wanxiyouapp.Moudle.IXuptManagement;

/**
 * Created by LZL on 2017/7/20.
 */

public interface IMainMenuPresenter {
    public boolean hasAlreadyLogin();

    public boolean wasFirstLogin();

    public void login(String username,String password,String cookie);

    public void showLogonDialog();

    public void searchHasLocalData();

    public void getScretImage();
    public void getScretError();

    public void getScretSuccess(Bitmap bitmap,String cookie);

    public void setManagement(IXuptManagement management);

}
