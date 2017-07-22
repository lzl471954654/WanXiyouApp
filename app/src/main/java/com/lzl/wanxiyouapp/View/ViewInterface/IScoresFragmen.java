package com.lzl.wanxiyouapp.View.ViewInterface;

import java.util.List;
import java.util.Map;

/**
 * Created by LZL on 2017/7/22.
 */

public interface IScoresFragmen {

    public void refreshLayout(Map<String,List<Map<String,String>>> scoreMap);

    public void showProgressDialog();

    public void dissmisProgressDialog();

    public void showRequestErrorMsg(String msg);
}
