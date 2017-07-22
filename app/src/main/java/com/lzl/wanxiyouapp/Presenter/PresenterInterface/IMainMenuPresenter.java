package com.lzl.wanxiyouapp.Presenter.PresenterInterface;

import android.graphics.Bitmap;

import com.lzl.wanxiyouapp.Bean.Student;
import com.lzl.wanxiyouapp.Moudle.MoudleInterface.IXuptManagement;

/**
 * Created by LZL on 2017/7/20.
 */

public interface IMainMenuPresenter {
    public boolean hasAlreadyLogin();

    public boolean wasFirstLogin(); //检查第一次登陆

    public void login(String username,String password,String cookie);   // 登陆

    public void login(String id,String password,String code,String cookie);

    public void onLoginError(String error);

    public void onLoginSuccess(Student student);

    public void showLogonDialog();

    public void searchHasLocalData();

    public void getScretImage();
    public void getScretError();

    public void getScretSuccess(Bitmap bitmap,String cookie);

    public void setManagement(IXuptManagement management);

}
