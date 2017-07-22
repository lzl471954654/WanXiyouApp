package com.lzl.wanxiyouapp.View.ViewInterface;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;

import com.lzl.wanxiyouapp.Bean.Student;

/**
 * Created by LZL on 2017/7/19.
 */

public interface IMainMenuView {
    public void initFragment();

    public void replaceFragment(Fragment fragment);

    public void onLoginResult(boolean result,String error);

    public void onSearchLocalDataResult(boolean result,Object object);

    public void showLoginDialog();

    public void showLoginDialogWithoutVerificationCode();

    public void changeMainMenuUserInfo();

    public void dissmissLoginDialog();

    public void onLoginSuccess(Student student);

    public void onLoginError(String error);

    public void getScretImageSuccess(Bitmap bitmap,String cookie);
}
