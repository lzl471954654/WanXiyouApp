package com.lzl.wanxiyouapp.View;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;

/**
 * Created by LZL on 2017/7/19.
 */

public interface IMainMenuView {
    public void initFragment();

    public void replaceFragment(Fragment fragment);

    public void onLoginResult(boolean result,String error);

    public void onSearchLocalDataResult(boolean result,Object object);

    public void showLoginDialog();

    public void dissmissLoginDialog();

    public void onLoginSuccess(String cookie);

    public void onLoginError();

    public void getScretImageSuccess(Bitmap bitmap,String cookie);
}
