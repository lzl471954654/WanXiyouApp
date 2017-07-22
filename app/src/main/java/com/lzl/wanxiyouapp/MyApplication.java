package com.lzl.wanxiyouapp;

import android.app.Application;

import com.lzl.wanxiyouapp.Bean.Student;

import java.util.List;
import java.util.Map;

/**
 * Created by LZL on 2017/7/21.
 */

public class MyApplication extends Application {
    public static Student student;
    public static final String url = "http://139.199.20.248:8080/";
    public static Map<String,List<Map<String,String>>> scoresMap;
}
