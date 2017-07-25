package com.lzl.wanxiyouapp.Moudle;

import android.content.Context;
import android.os.Handler;

import com.lzl.wanxiyouapp.Bean.Student;
import com.lzl.wanxiyouapp.Moudle.MoudleInterface.ILessonMoudle;
import com.lzl.wanxiyouapp.MyApplication;
import com.lzl.wanxiyouapp.Presenter.PresenterInterface.ILessonPresenter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Map;
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

public class LessonMoudle implements ILessonMoudle {
    ILessonPresenter presenter;
    Context context;
    Handler handler;

    public LessonMoudle(Context context)
    {
        this.context = context;
        handler = new Handler(context.getMainLooper());
    }

    public void setPresenter(ILessonPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void checkHasData() {
        if(MyApplication.leesonList!=null)
            presenter.hasData(MyApplication.leesonList);
        else
            presenter.hasNoData();
    }

    @Override
    public void requestLessonData() {
        Student student = MyApplication.student;
        if(student==null)
        {
            presenter.onRequestError("对不起您还没有登录，或验证信息已过期。");
            return;
        }
        String url = MyApplication.url+"WanXiyou/edu/getLessonList";
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
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
                final List<List<Map<String,String>>> lessonList;
                if(response.code()!=200)
                {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            presenter.onRequestError("服务器错误"+response.code());
                        }
                    });
                }
                ObjectInputStream inputStream = new ObjectInputStream(response.body().byteStream());
                try {
                    Object object = inputStream.readObject();
                    lessonList  = (List<List<Map<String,String>>>)object;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            presenter.onRequestSuccess(lessonList);
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
                }
                catch (final Exception e)
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
