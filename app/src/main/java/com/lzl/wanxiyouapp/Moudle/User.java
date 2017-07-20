package com.lzl.wanxiyouapp.Moudle;

/**
 * Created by LZL on 2017/7/20.
 */

public class User implements IUser {

    String name;
    String pass;

    @Override
    public boolean checkUserAviliable(String username, String password) {
        return (username!=null&&pass!=null)&(name.equals(username)&&pass.equals(password));
    }

    public User(String username, String password) {
        this.name = username;
        this.pass = password;
    }
}
