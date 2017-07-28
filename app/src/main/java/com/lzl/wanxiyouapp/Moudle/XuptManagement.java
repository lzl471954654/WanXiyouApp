package com.lzl.wanxiyouapp.Moudle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

import com.lzl.wanxiyouapp.Bean.Student;
import com.lzl.wanxiyouapp.Moudle.MoudleInterface.IXuptManagement;
import com.lzl.wanxiyouapp.MyApplication;
import com.lzl.wanxiyouapp.Presenter.PresenterInterface.IMainMenuPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by LZL on 2017/7/20.
 */

public class XuptManagement implements IXuptManagement {
    final String url = "http://139.199.20.248:8080/";
    IMainMenuPresenter presenter;
    Context context;
    Handler handler;

    public XuptManagement(IMainMenuPresenter presenter,Context context) {
        this.presenter = presenter;
        handler = new Handler(context.getMainLooper());
    }

    @Override
    public boolean checkPeEnable() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://yddx.boxkj.com/wx/login")
                .get()
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                MyApplication.peEnable = false;
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code()==200)
                    MyApplication.peEnable = true;
            }
        });
        return MyApplication.peEnable;
    }

    @Override
    public void userLogon(String username, String password, String cookie) {
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody formBody = new FormBody.Builder()
                .add("username",username)
                .add("password",password)
                .add("code","")
                .add("Set-Cookie","")
                .build();
        Request request = new Request.Builder()
                .url(url+"")
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        presenter.onLoginError("对不起网络异常，请求失败。");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int code = response.code();
                if(code==200)
                {
                    try
                    {
                        String data = response.body().string();
                        JSONObject object = new JSONObject(data);
                        int status = object.getInt("code");
                        if(code==1)
                        {
                            final String s = object.getString("data");
                            JSONObject jsonObject = new JSONObject(s);
                            Map<String,String> map = (Map<String, String>)jsonObject.getJSONObject("user_info");
                            final Student student = new Student(jsonObject.getString("id"),jsonObject.getString("name"),jsonObject.getString("cookie"),map);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    presenter.onLoginSuccess(student);
                                }
                            });
                        }
                        else
                        {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    presenter.onLoginError("对不起登录失败，请核对用户名密码");
                                }
                            });
                        }
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    final String error_code = String.valueOf(code);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            presenter.onLoginError("错误代码："+error_code);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void userLogon(String id, final String password, String code, String cookie) {
        OkHttpClient okHttpClient = new OkHttpClient();
        FormBody formBody = new FormBody.Builder()
                .add("xh",id)
                .add("password",password)
                .add("code",code)
                .add("cookie",cookie)
                .build();
        Request request = new Request.Builder()
                .url(url+"WanXiyou/edu/login")
                .post(formBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        presenter.onLoginError("对不起网络异常，请求失败。");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                int code = response.code();
                if(code==200)
                {
                    try
                    {
                        String data = response.body().string();
                        System.out.println("Body:"+data);
                        System.out.println("ContentType:"+response.header("Content-Type"));
                        JSONObject object = new JSONObject(data);
                        int status = object.getInt("code");
                        if(status==1)
                        {
                            final String s = object.getString("data");
                            JSONObject jsonObject = new JSONObject(s);
                            JSONObject data1 = jsonObject.getJSONObject("user_info");
                            Map<String,String> map = new LinkedHashMap<String, String>();
                            map.put("xh",data1.getString("xh"));
                            map.put("dqszj",data1.getString("dqszj"));
                            map.put("cc",data1.getString("cc"));
                            map.put("xzb",data1.getString("xzb"));
                            map.put("xz",data1.getString("xz"));
                            map.put("xm",data1.getString("xm"));
                            map.put("zy",data1.getString("zy"));
                            final Student student = new Student(jsonObject.getString("id"),jsonObject.getString("name"),jsonObject.getString("cookie"),map);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    presenter.onLoginSuccess(student);
                                }
                            });
                        }
                        else
                        {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    presenter.onLoginError("对不起登录失败，请核对用户名密码");
                                }
                            });
                        }
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    final String error_code = String.valueOf(code);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            presenter.onLoginError("错误代码："+error_code);
                        }
                    });
                }
                response.close();
            }
        });
    }


    @Override
    public void loadScretImage() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        builder.url("http://139.199.20.248:8080/WanXiyou/edu/GetScretImage")
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
                response.close();
            }
        });
    }
}
