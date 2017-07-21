package com.lzl.wanxiyouapp.View;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.lzl.wanxiyouapp.Bean.Student;
import com.lzl.wanxiyouapp.Moudle.User;
import com.lzl.wanxiyouapp.Moudle.XuptManagement;
import com.lzl.wanxiyouapp.MyApplication;
import com.lzl.wanxiyouapp.Presenter.IMainMenuPresenter;
import com.lzl.wanxiyouapp.Presenter.MainMenuPresenter;
import com.lzl.wanxiyouapp.R;

/**
 * Created by LZL on 2017/7/19.
 */

public class MainMenuActivity extends AppCompatActivity implements IMainMenuView,NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    IMainMenuPresenter presenter;
    Dialog logonDialog;
    String cookie;
    Bitmap bitmap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_layout);
        presenter = new MainMenuPresenter(this,this);
        XuptManagement management = new XuptManagement(presenter,this);
        presenter.setManagement(management);
        init();
        initToolBar();
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.dawer_open,R.string.dawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        if(presenter.wasFirstLogin())
            presenter.showLogonDialog();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
            {
                if(drawerLayout.isDrawerOpen(drawerLayout))
                    drawerLayout.closeDrawer(Gravity.START);
                else
                    drawerLayout.openDrawer(Gravity.START);
                break;
            }
            case R.id.navigation_lesson_sheet:
            {
                replaceFragment(new EmptyFragment());
                break;
            }
            case R.id.navigation_run:
            {
                replaceFragment(new EmptyFragment());
                break;
            }
        }

        return true;
    }

    @Override
    public void onSearchLocalDataResult(boolean result, Object object) {

    }

    @Override
    public void showLoginDialog() {
        logonDialog = new Dialog(this);
        logonDialog.setContentView(R.layout.login_dialog);
        final TextInputEditText username = (TextInputEditText)logonDialog.findViewById(R.id.login_username);
        final TextInputEditText password = (TextInputEditText)logonDialog.findViewById(R.id.login_password);
        final CheckBox checkBox = (CheckBox)logonDialog.findViewById(R.id.login_remember_pass);
        final Button button = (Button)logonDialog.findViewById(R.id.login_button);
        ImageView imageView = (ImageView) logonDialog.findViewById(R.id.scret_code_image);
        final TextInputEditText scretCode = (TextInputEditText)logonDialog.findViewById(R.id.scret_code_input);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getScretImage();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = username.getText().toString();
                String pass = password.getText().toString();
                String code = scretCode.getText().toString();
               if(id!=null&&pass!=null&&code!=null&&cookie!=null)
                   presenter.login(username.getText().toString(),password.getText().toString(),scretCode.getText().toString(),cookie);
                else
                    Snackbar.make(button,"用户名，密码，验证码不能为空",Snackbar.LENGTH_SHORT).show();
            }
        });
        logonDialog.setCancelable(false);
        logonDialog.create();
        logonDialog.show();
        presenter.getScretImage();
        //presenter.getScretImage();
    }


    @Override
    public void getScretImageSuccess(Bitmap bitmap, String cookie) {
        if(logonDialog!=null)
        {
            ((ImageView)logonDialog.findViewById(R.id.scret_code_image)).setImageBitmap(bitmap);
            this.cookie = cookie;
        }
    }

    public void initFragment()
    {
        replaceFragment(new EmptyFragment());
    }

    @Override
    public void onLoginResult(boolean result, String error) {

    }

    public void replaceFragment(Fragment fragment)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_menu_frameLayout,fragment);
        transaction.commit();
    }

    public void initToolBar()
    {
        setSupportActionBar(toolbar);
    }

    public void init()
    {
        navigationView = (NavigationView)findViewById(R.id.navigationView);
        toolbar = (Toolbar)findViewById(R.id.main_menu_toolbar);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
    }

    @Override
    public void onLoginError(String error) {
        Snackbar.make(logonDialog.getCurrentFocus(),error,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginSuccess(Student student) {
        MyApplication.student = student;
        if(student!=null)
        {
            Snackbar.make(getCurrentFocus(),student.getName()+"，登录成功",Snackbar.LENGTH_SHORT).show();
            dissmissLoginDialog();
        }
    }

    @Override
    public void dissmissLoginDialog() {
        if(logonDialog!=null)
        {
            logonDialog.dismiss();
            logonDialog = null;
        }
    }
}
