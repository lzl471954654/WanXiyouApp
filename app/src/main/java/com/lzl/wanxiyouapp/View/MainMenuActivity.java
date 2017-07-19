package com.lzl.wanxiyouapp.View;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

import com.lzl.wanxiyouapp.R;

/**
 * Created by LZL on 2017/7/19.
 */

public class MainMenuActivity extends AppCompatActivity implements IMainMenu,NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu_layout);
        init();
        initToolBar();
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.dawer_open,R.string.dawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
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
        }

        return true;
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
}
