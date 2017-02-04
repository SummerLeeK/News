package com.example.u.okhttptest;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private List<Fragment> fragmentList;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    private DrawerLayout drawerLayout;

    private NavigationView navigation;

    private Toolbar toolbar;
    private int flag;

    private ImageView weather_img;

    private TextView weather_desc, weather_temp, weather_city;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Snackbar.make(drawerLayout, "您已经在当前界面！！", Snackbar.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initShare();

        initView();


    }

    private void initShare() {

        preferences = getSharedPreferences("setting", MODE_PRIVATE);
        editor = preferences.edit();
        if (preferences.getAll().size() == 0) {
            editor.putBoolean("3g", true);
            editor.putBoolean("wifi", true);
            editor.putBoolean("night_or_day", false);

        }
        editor.commit();

    }

    private void initView() {

        flag = 0;
        fragmentList = new ArrayList<>();
        manager = getFragmentManager();
        transaction = manager.beginTransaction();
        ZhihuContentFragment zhihuContentFragment = new ZhihuContentFragment(this);
        WeixinContentFragment weixinContentFragment = new WeixinContentFragment(this);
        fragmentList.add(zhihuContentFragment);
        fragmentList.add(weixinContentFragment);

        transaction.replace(R.id.content, fragmentList.get(flag));
        transaction.commit();


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        navigation = (NavigationView) findViewById(R.id.navigation_view);
        navigation.setNavigationItemSelectedListener(this);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.weixin:
                if (flag == 1) {
                    handler.sendEmptyMessage(0);
                } else {
                    flag = 1;
                    transaction = manager.beginTransaction();
                    transaction.replace(R.id.content, fragmentList.get(flag));
                    transaction.commit();
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                break;
            case R.id.zhihu:
                if (flag == 0) {
                    handler.sendEmptyMessage(0);
                } else {
                    flag = 0;
                    transaction = manager.beginTransaction();
                    transaction.replace(R.id.content, fragmentList.get(flag));
                    transaction.commit();
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                break;
        }
        return false;
    }


    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:

                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);

                break;
        }
        return false;
    }
}
