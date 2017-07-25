package com.lzl.wanxiyouapp.Moudle;

import android.content.Context;
import android.os.Handler;

import com.lzl.wanxiyouapp.Bean.ResponseData;
import com.lzl.wanxiyouapp.Bean.ResponseSingleData;
import com.lzl.wanxiyouapp.Bean.Student;
import com.lzl.wanxiyouapp.Moudle.MoudleInterface.IScoreSheetMoudle;
import com.lzl.wanxiyouapp.Moudle.MoudleInterface.IScoresMoudle;
import com.lzl.wanxiyouapp.MyApplication;
import com.lzl.wanxiyouapp.Presenter.PresenterInterface.IScoreSheetPresenter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by LZL on 2017/7/24.
 */

public class ScoreSheetMoudle implements IScoreSheetMoudle {
    IScoreSheetPresenter presenter;
    Handler handler;

    @Override
    public void checkData() {
        if(MyApplication.responseData==null)
            presenter.checkDataError();
        else
            presenter.checkDataSuccess(MyApplication.responseData);
    }

    public ScoreSheetMoudle(Context context)
    {
        handler = new Handler(context.getMainLooper());
    }

    public void setPresenter(IScoreSheetPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void requestSheetData() {
        Student student = MyApplication.student;
        if(student==null)
        {
            presenter.onRequestError("对不起您还没有登录，或验证信息已过期。");
            return;
        }
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .build();
        String url = MyApplication.url+"WanXiyou/edu/getScoresStatistics";
        FormBody formBody = new FormBody.Builder()
                .add("xh",student.getId())
                .add("name",student.getName())
                .add("cookie",student.getCookie())
                .add("device","Android")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        presenter.onRequestError("服务器异常");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if(response.code()!=200)
                {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            presenter.onRequestError("服务器错误"+response.code());
                        }
                    });
                }
                try {
                    ObjectInputStream inputStream = new ObjectInputStream(response.body().byteStream());
                    final List<List<String>> dataResponseData = (List<List<String>>)inputStream.readObject();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            MyApplication.responseData = dataResponseData;
                            presenter.onRequestSuccess(dataResponseData);
                        }
                    });
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            presenter.onRequestError("ClassNotFoundException");
                        }
                    });
                }catch (ClassCastException e)
                {
                    e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            presenter.onRequestError("ClassCastException");
                        }
                    });
                }catch (final Exception e)
                {
                    e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String msg = "Another Exception";
                            if(e instanceof SocketTimeoutException)
                                msg = "连接服务器超时";
                            presenter.onRequestError(msg);
                        }
                    });
                }


            }
        });
    }
}
