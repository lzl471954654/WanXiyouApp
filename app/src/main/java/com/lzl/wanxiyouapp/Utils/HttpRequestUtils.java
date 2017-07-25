package com.lzl.wanxiyouapp.Utils;

import com.lzl.wanxiyouapp.Bean.Student;
import com.lzl.wanxiyouapp.MyApplication;
import com.lzl.wanxiyouapp.Presenter.PresenterInterface.BasePersenterInterface;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by LZL on 2017/7/25.
 */

public class HttpRequestUtils {

    public static void httpRequestWithStudent(String url, BasePersenterInterface persenter, Callback callback,int connectionTimeOut,int readTimeOut)
    {
        Student student = MyApplication.student;
        if(student==null)
        {
            persenter.onRequestError("对不起您还没有登录，请先登录");
            return;
        }
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(connectionTimeOut, TimeUnit.SECONDS)
                .readTimeout(readTimeOut,TimeUnit.SECONDS)
                .build();
        Request.Builder builder = new Request.Builder();
        FormBody formBody = new FormBody.Builder()
                .add("xh",student.getId())
                .add("name",student.getName())
                .add("cookie",student.getCookie())
                .add("device","Android")
                .build();
        builder.post(formBody)
                .url(url);
        Call call = okHttpClient.newCall(builder.build());
        call.enqueue(callback);
    }
}
