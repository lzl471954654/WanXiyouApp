package com.lzl.wanxiyouapp.Moudle;

import com.lzl.wanxiyouapp.Bean.Student;
import com.lzl.wanxiyouapp.Moudle.MoudleInterface.IScoresMoudle;
import com.lzl.wanxiyouapp.MyApplication;
import com.lzl.wanxiyouapp.Presenter.PresenterInterface.IScorePresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by LZL on 2017/7/22.
 */

public class ScoresMoudle implements IScoresMoudle {
    IScorePresenter presenter;

    public ScoresMoudle(IScorePresenter presenter) {
        this.presenter = presenter;
    }
    public ScoresMoudle(){};

    public IScorePresenter getPresenter() {
        return presenter;
    }

    public void setPresenter(IScorePresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void requestScoresList() {
        Student student = MyApplication.student;
        if(student==null)
        {
            presenter.onRequestError("用户信息异常！");
            return;
        }
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody formBody = new FormBody.Builder()
                .add("xh",student.getId())
                .add("name",student.getName())
                .add("cookie",student.getCookie())
                .add("device","Android")
                .build();
        Request request = new Request.Builder()
                .url(MyApplication.url+"WanXiyou/edu/getScoresList")
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.header("result");
                int code = response.code();
                if(code!=200)
                {
                    String msg = "获取数据时出现异常"+code;
                    presenter.onRequestError(msg);
                }
                else if(result!=null&&result.equals("1"))
                {
                    try
                    {
                        ObjectInputStream inputStream = new ObjectInputStream(response.body().byteStream());
                        Object object = inputStream.readObject();
                        if(object instanceof Map)
                        {
                            Map<String,List<Map<String,String>>> map = (Map<String,List<Map<String,String>>>) object;
                            if(map==null) {
                                System.out.println("map is null");
                                presenter.onRequestSuccess(new LinkedHashMap<String, List<Map<String, String>>>());
                            }
                            else
                            {
                                System.out.println(map);
                                presenter.onRequestSuccess(map);
                            }
                        }
                        else
                        {
                            presenter.onRequestError("请求数据的格式不正确");
                        }

                    }
                    catch (ClassNotFoundException e)
                    {
                        e.printStackTrace();
                        presenter.onRequestError("数据出错！");
                    }
                }
                else
                {
                    if(result!=null)
                    {
                        try
                        {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            String msg = jsonObject.getString("error");
                            presenter.onRequestError(msg);
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    else
                        presenter.onRequestError("请求出错");
                }
            }
        });
    }
}
