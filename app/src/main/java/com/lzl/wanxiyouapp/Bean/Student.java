package com.lzl.wanxiyouapp.Bean;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by LZL on 2017/7/21.
 */

public class Student {
    String id;
    String name;
    String cookie;
    Map<String,String> user_info;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getUser_info() {
            return user_info;
        }

    public void setUser_info(Map<String, String> user_info) {
        this.user_info = user_info;
    }

    public Student(String id, String name, String cookie, Map<String, String> user_info) {
        this.id = id;
        this.name = name;
        this.cookie = cookie;
        this.user_info = user_info;
    }

    public String getCookie() {

        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
}
