package com.lzl.wanxiyouapp.Moudle.MoudleInterface;

/**
 * Created by LZL on 2017/8/1.
 */

public interface ILogonMoudle {
    public void Logon(String id,String pass,String cookie,String code);

    public void LogonWithoutCode(String id,String pass);

    public void getVerificationImage();
}
