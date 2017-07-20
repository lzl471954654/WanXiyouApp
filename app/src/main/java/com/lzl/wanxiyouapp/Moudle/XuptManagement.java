package com.lzl.wanxiyouapp.Moudle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

import com.lzl.wanxiyouapp.CallBack;
import com.lzl.wanxiyouapp.Presenter.IMainMenuPresenter;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by LZL on 2017/7/20.
 */

public class XuptManagement implements IXuptManagement {
    IMainMenuPresenter presenter;
    Context context;
    Handler handler;

    public XuptManagement(IMainMenuPresenter presenter,Context context) {
        this.presenter = presenter;
        handler = new Handler(context.getMainLooper());
    }

    @Override
    public void userLogon(String username, String password, String cookie) {

    }

    @Override
    public void loadScretImage() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        builder.url("http://139.199.20.248:8080/WanXiyou/GetScretImage")
                .get();
        Call call = okHttpClient.newCall(builder.build());
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String cookie = response.header("Set-Cookie");
                System.out.println(cookie);
                byte[] bytes;
                bytes = response.body().bytes();
                final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        presenter.getScretSuccess(bitmap,cookie);
                    }
                });
            }
        });
    }
}
