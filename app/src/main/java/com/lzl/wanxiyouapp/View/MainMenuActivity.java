package com.lzl.wanxiyouapp.View;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzl.wanxiyouapp.Bean.Student;
import com.lzl.wanxiyouapp.Moudle.XuptManagement;
import com.lzl.wanxiyouapp.MyApplication;
import com.lzl.wanxiyouapp.Presenter.PresenterInterface.IMainMenuPresenter;
import com.lzl.wanxiyouapp.Presenter.MainMenuPresenter;
import com.lzl.wanxiyouapp.R;
import com.lzl.wanxiyouapp.View.ViewInterface.IMainMenuView;

/**
 * Created by LZL on 2017/7/19.
 */

public class MainMenuActivity extends AppCompatActivity implements IMainMenuView,NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    TextView headerUsername;
    Toolbar toolbar;
    IMainMenuPresenter presenter;
    Dialog logonDialog;
    String cookie;


    Fragment tempFragment;
    LibFragment libFragment;
    EducationFragment educationFragment;
    Pefragment peFragment;
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
        presenter.checkPeEnable();
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.icons));
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
            case R.id.navigation_lib:
            {
                drawerLayout.closeDrawer(Gravity.START);
                if(libFragment==null)
                {
                    libFragment = new LibFragment();
                    addFragment(libFragment,tempFragment);
                    tempFragment = libFragment;
                }
                else
                {
                    showFragment(libFragment,tempFragment);
                    tempFragment = libFragment;
                }
                break;
            }
            case R.id.navigation_pe:
            {
                if(!presenter.checkPeEnable())
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("对不起选课系统暂时关闭无法进入。");
                    builder.setCancelable(true);
                    builder.setNegativeButton("确定",null);
                    builder.create().show();
                    break;
                }
                drawerLayout.closeDrawer(Gravity.START);
                if(peFragment==null)
                {
                    peFragment = new Pefragment();
                    addFragment(peFragment,tempFragment);
                    tempFragment = peFragment;
                }
                else
                {
                    showFragment(peFragment,tempFragment);
                    tempFragment = peFragment;
                }
                break;
            }
            case R.id.navigation_edu:
            {
                drawerLayout.closeDrawer(Gravity.START);
                if(educationFragment==null)
                {
                    educationFragment = new EducationFragment();
                    addFragment(educationFragment,tempFragment);
                    tempFragment = educationFragment;
                }
                else
                {
                    showFragment(educationFragment,tempFragment);
                    tempFragment = educationFragment;
                }
                break;
            }
            case R.id.navigation_run:
            {
                //replaceFragment(new EmptyFragment());
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
        logonDialog.setCancelable(true);
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
        //lessonFragment = new EducationFragment();
        tempFragment = new EducationFragment();
        //addFragment(lessonFragment,null);
        replaceFragment(tempFragment);
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

    public void showFragment(Fragment fragment,Fragment hideFragment)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(hideFragment);
        transaction.show(fragment);
        transaction.commitNow();
    }
    public void addFragment(Fragment fragment,Fragment hideFragment)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(hideFragment!=null)
            transaction.hide(hideFragment);
        transaction.add(R.id.main_menu_frameLayout,fragment);
        transaction.show(fragment);
        transaction.commitNow();
    }

    public void initToolBar()
    {
        setSupportActionBar(toolbar);
    }

    @Override
    public void changeMainMenuUserInfo() {
        headerUsername.setText(MyApplication.student.getName());
    }

    public void init()
    {
        navigationView = (NavigationView)findViewById(R.id.navigationView);
        toolbar = (Toolbar)findViewById(R.id.main_menu_toolbar);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        headerUsername = (TextView)navigationView.getHeaderView(0).findViewById(R.id.user_name);
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
        initFragment();
    }

    @Override
    public void dissmissLoginDialog() {
        if(logonDialog!=null)
        {
            logonDialog.dismiss();
            logonDialog = null;
        }
    }

    @Override
    public void showLoginDialogWithoutVerificationCode() {
        /*
        * 快速登陆接口 无验证码登陆
        * */
    }

}
