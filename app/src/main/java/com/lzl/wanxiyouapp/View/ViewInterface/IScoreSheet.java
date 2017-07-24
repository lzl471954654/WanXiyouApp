package com.lzl.wanxiyouapp.View.ViewInterface;

import com.lzl.wanxiyouapp.Bean.ResponseData;
import com.lzl.wanxiyouapp.Bean.ResponseSingleData;

import java.util.List;

/**
 * Created by LZL on 2017/7/24.
 */

public interface IScoreSheet {
    public void onRequestSuccess(List<List<String>> data);

    public void onRrequestError(String msg);

    public void showProgressDialog();

    public void dissmisDialog();

    public void showErrorDialog(String msg);
}
