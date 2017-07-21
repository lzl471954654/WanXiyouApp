package com.lzl.wanxiyouapp.Presenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;

import com.lzl.wanxiyouapp.Bean.Student;
import com.lzl.wanxiyouapp.CallBack;
import com.lzl.wanxiyouapp.Moudle.IUser;
import com.lzl.wanxiyouapp.Moudle.IXuptManagement;
import com.lzl.wanxiyouapp.View.IMainMenuView;

/**
 * Created by LZL on 2017/7/20.
 */

public class MainMenuPresenter implements IMainMenuPresenter {
    IMainMenuView mainMenuView;
    IXuptManagement management;
    Context context;
    Handler handler;
    public MainMenuPresenter(IMainMenuView mainMenuView, Context context) {
        this.mainMenuView = mainMenuView;
        this.context = context;
        handler = new Handler(context.getMainLooper());
    }

    @Override
    public void getScretImage() {
        management.loadScretImage();
    }

    public void setManagement(IXuptManagement management) {
        this.management = management;
    }

    @Override
    public void getScretError() {
        getScretImage();
    }

    @Override
    public void getScretSuccess(Bitmap bitmap, String cookie) {
        mainMenuView.getScretImageSuccess(bitmap,cookie);
    }

    @Override
    public boolean hasAlreadyLogin() {
        return false;
    }

    @Override
    public boolean wasFirstLogin() {
        return true;
    }

    @Override
    public void searchHasLocalData() {

    }

    @Override
    public void showLogonDialog() {
        mainMenuView.showLoginDialog();
        getScretImage();
    }

    @Override
    public void login(String username, String password,String cookie) {
        management.userLogon(username,password,cookie);
    }

    @Override
    public void login(String id, String password, String code, String cookie) {
        management.userLogon(id,password,code,cookie);
    }

    public void onLoginSuccess(Student student)
    {
        mainMenuView.onLoginSuccess(student);
    }

    @Override
    public void onLoginError(String error) {
        mainMenuView.onLoginError(error);
    }
}
