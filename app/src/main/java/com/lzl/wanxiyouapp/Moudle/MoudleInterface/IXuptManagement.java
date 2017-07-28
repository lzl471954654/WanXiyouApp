package com.lzl.wanxiyouapp.Moudle.MoudleInterface;

import com.lzl.wanxiyouapp.CallBack;

import okhttp3.Call;
import okhttp3.Callback;

/**
 * Created by LZL on 2017/7/20.
 */

public interface IXuptManagement {

    public void userLogon(String username,String password,String cookie);

    public void userLogon(String id,String password,String code,String cookie);

    public boolean checkPeEnable();

    public void loadScretImage();
}
