package com.example.appofzhejiang.coolweather;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appofzhejiang.R;

public class CoolWeatherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coolweather);

        /*****隐藏系统顶部栏*****************/
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        Intent intent = getIntent();
        String weatherId = intent.getStringExtra("weatherId");
        if(weatherId != null) {
            Intent newIntent = new Intent(this, WeatherActivity.class);
            newIntent.putExtra("weather_id", weatherId);
            startActivity(newIntent);
            finish();
        }
        // 若weatherId等于null：有可能地点无法获取天气，则手动选择



         // 当城市信息已经缓存时，调转到WeatherActivity，否则开始选择城市
//        if (prefs.getString("weather", null) != null) {
//            Intent newIntent = new Intent(this, WeatherActivity.class);
//            startActivity(newIntent);
//            finish();
//        }
    }

}