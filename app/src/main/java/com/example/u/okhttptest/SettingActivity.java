package com.example.u.okhttptest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by u on 2017/1/31.
 */

public class SettingActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Toolbar toolbar;

    private SwitchCompat threeg_switch, wifi_switch, night_switch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("设置");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        initView();

    }

    private void initView() {

        threeg_switch= (SwitchCompat) findViewById(R.id.threeg_switch);

        wifi_switch= (SwitchCompat) findViewById(R.id.wifi_switch);

        night_switch= (SwitchCompat) findViewById(R.id.night_switch);

        preferences=getSharedPreferences("setting",MODE_PRIVATE);

        threeg_switch.setChecked(false);
        wifi_switch.setChecked(true);
        night_switch.setChecked(false);

//        if (preferences.getBoolean("3g",true)){
//            threeg_switch.setChecked(true);
//        }
//        else {
//            threeg_switch.setChecked(false);
//        }
//
//        if (preferences.getBoolean("wifi",true)){
//            wifi_switch.setChecked(true);
//        }else {
//            wifi_switch.setChecked(false);
//        }
//
//        if (preferences.getBoolean("night_or_day",true)){
//
//            night_switch.setChecked(true);
//        }
//        else {
//
//            night_switch.setChecked(false);
//        }


    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}
