package com.lzl.wanxiyouapp.Moudle;

import android.content.Context;
import android.os.Handler;

import com.lzl.wanxiyouapp.Bean.Student;
import com.lzl.wanxiyouapp.Moudle.MoudleInterface.IStudyMoudle;
import com.lzl.wanxiyouapp.MyApplication;
import com.lzl.wanxiyouapp.Presenter.PresenterInterface.IStudyPlanPresenter;
import com.lzl.wanxiyouapp.Utils.HttpRequestUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
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
 * Created by LZL on 2017/7/25.
 */

public class StudyPlanMoudle implements IStudyMoudle {
    IStudyPlanPresenter presenter;
    Handler handler;

    public StudyPlanMoudle(Context context,IStudyPlanPresenter planPresenter)
    {
        handler = new Handler(context.getMainLooper());
        this.presenter = planPresenter;
    }

    @Override
    public void checkData() {
        if(MyApplication.planList==null)
            presenter.checkDataError();
        else
            presenter.checkDataSuccess(MyApplication.planList);
    }

    @Override
    public void requestData() {
        HttpRequestUtils.httpRequestWithStudent(MyApplication.url + "WanXiyou/edu/getStudyPlan", presenter, new Callback() {
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
            public void onResponse(final Call call, Response response) throws IOException {
                final List<List<Map<String,String>>> lists;
                final int code = response.code();
                if(code != 200)
                {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            presenter.onRequestError("服务器错误"+code);
                        }
                    });
                }
                String json = response.header("result");
                if(json==null)
                {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            presenter.onRequestError("json NULL");
                        }
                    });
                    return;
                }
                if(json!=null&&json.equals("0"))
                {
                    String s = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        final String error = jsonObject.getString("error");
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                presenter.onRequestError(error);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return;
                }

                try {
                    ObjectInputStream inputStream = new ObjectInputStream(response.body().byteStream());
                    lists = (List<List<Map<String,String>>>)inputStream.readObject();
                    System.out.println("List:\t"+lists);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            presenter.onRequestSuccess(lists);
                        }
                    });
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            presenter.onRequestError("ClassNotFoundException"+code);
                        }
                    });
                }catch (Exception e)
                {
                    e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            presenter.onRequestError("Another Exception"+code);
                        }
                    });
                }
            }
        },20,20);
    }
}
