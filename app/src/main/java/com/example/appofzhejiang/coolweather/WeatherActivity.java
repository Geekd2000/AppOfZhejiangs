package com.example.appofzhejiang.coolweather;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;

import com.example.appofzhejiang.R;
import com.example.appofzhejiang.coolweather.gson.Forecast;
import com.example.appofzhejiang.coolweather.gson.Weather;
import com.example.appofzhejiang.coolweather.service.AutoUpdateService;
import com.example.appofzhejiang.coolweather.util.HttpUtil;

import com.example.appofzhejiang.coolweather.util.ResponseToReal;
import com.example.appofzhejiang.coolweather.util.Utility;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.base.Code;
import interfaces.heweather.com.interfacesmodule.bean.weather.WeatherDailyBean;
import interfaces.heweather.com.interfacesmodule.bean.weather.WeatherNowBean;
import interfaces.heweather.com.interfacesmodule.view.HeConfig;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {

    public DrawerLayout drawerLayout;

    public SwipeRefreshLayout swipeRefresh;

    private ScrollView weatherLayout;

    private Button navButton;

    private TextView titleCity;

    private TextView titleUpdateTime;

    private TextView degreeText;

    private TextView weatherInfoText;

    private LinearLayout forecastLayout;

    private TextView aqiText;

    private TextView pm25Text;

    private TextView comfortText;

    private TextView carWashText;

    private TextView sportText;

    private ImageView bingPicImg;

    private String mWeatherId;
    private HashMap<String, String[]> forecastMap = new HashMap<>();
    private HashMap<String, String> nowMap = new HashMap<>();
    private static int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*****隐藏系统顶部栏*****************/
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_weather);
        // 初始化各控件
        bingPicImg = (ImageView) findViewById(R.id.bing_pic_img);
        weatherLayout = (ScrollView) findViewById(R.id.weather_layout);
        titleCity = (TextView) findViewById(R.id.title_city);
        titleUpdateTime = (TextView) findViewById(R.id.title_update_time);
        degreeText = (TextView) findViewById(R.id.degree_text);
        weatherInfoText = (TextView) findViewById(R.id.weather_info_text);
        forecastLayout = (LinearLayout) findViewById(R.id.forecast_layout);
        aqiText = (TextView) findViewById(R.id.aqi_text);
        pm25Text = (TextView) findViewById(R.id.pm25_text);
        comfortText = (TextView) findViewById(R.id.comfort_text);
        carWashText = (TextView) findViewById(R.id.car_wash_text);
        sportText = (TextView) findViewById(R.id.sport_text);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navButton = (Button) findViewById(R.id.nav_button);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = prefs.getString("weather", null);
//        if (weatherString != null) {
//            // 有缓存时直接解析天气数据
//            Weather weather = Utility.handleWeatherResponse(weatherString);
//            mWeatherId = weather.basic.weatherId;
//            showWeatherInfo(weather);
//        } else {
//            // 无缓存时去服务器查询天气
//            mWeatherId = getIntent().getStringExtra("weather_id");
//            weatherLayout.setVisibility(View.INVISIBLE);
//            requestWeather(mWeatherId);
//        }

        // 不缓存，每次都去查
        mWeatherId = getIntent().getStringExtra("weather_id");
        weatherLayout.setVisibility(View.INVISIBLE);
        requestWeather(mWeatherId);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(mWeatherId);
            }
        });
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        String bingPic = prefs.getString("bing_pic", null);
        if (bingPic != null) {
            Glide.with(this).load(bingPic).into(bingPicImg);
        } else {
            loadBingPic();
        }
    }


    /**
     * 根据天气id请求城市天气信息。CN101010100
     */
    public void requestWeather(final String weatherId) {
        String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=8fb20072276e48aa83d1200cce3653e0";
        getWeatherMap(weatherId);
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText0 = response.body().string();
                // 第一次请求时就把相应数据改成假数据 start
                int aqiFirstIndex1 = responseText0.indexOf("aqi") + 6;
                int aqiFirstIndex2 = aqiFirstIndex1 + responseText0.substring(aqiFirstIndex1).indexOf("aqi") + 6;
                int aqiLastIndex2 = aqiFirstIndex2 + responseText0.substring(aqiFirstIndex2).indexOf("\"");
                responseText0 = responseText0.substring(0, aqiFirstIndex2) + "40" + responseText0.substring(aqiLastIndex2);

                int pm25FirstIndex = responseText0.indexOf("pm25") + 7;
                int pm25LastIndex = pm25FirstIndex + responseText0.substring(pm25FirstIndex).indexOf("\"");
                responseText0 = responseText0.substring(0, pm25FirstIndex) + "西南风2级" + responseText0.substring(pm25LastIndex);
                // 第一次请求时就把相应数据改成假数据 end

                // 把对应数据换成和风天气的数据，如果获取失败则还用原来的数据
                final String responseText = new ResponseToReal().toReal(responseText0, nowMap, forecastMap);
                final Weather weather = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)) {
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather", responseText);
                            editor.apply();
                            mWeatherId = weather.basic.weatherId;
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        }
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });
        loadBingPic();
    }

    /**
     * 加载必应每日一图
     */
    private void loadBingPic() {
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                editor.putString("bing_pic", bingPic);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(bingPic).into(bingPicImg);
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }


    /**
     * 处理并展示Weather实体类中的数据。
     */
    private void showWeatherInfo(Weather weather) {
        String cityName = weather.basic.cityName;
        String updateTime = weather.basic.update.updateTime.split(" ")[1];
        String degree = weather.now.temperature + "℃";
        String weatherInfo = weather.now.more.info;
        titleCity.setText(cityName);
        titleUpdateTime.setText(updateTime);
        degreeText.setText(degree);
        weatherInfoText.setText(weatherInfo);
        forecastLayout.removeAllViews();
        int i = 0;
        for (Forecast forecast : weather.forecastList) {
            View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
            TextView dateText = (TextView) view.findViewById(R.id.date_text);
            TextView infoText = (TextView) view.findViewById(R.id.info_text);
            TextView maxText = (TextView) view.findViewById(R.id.max_text);
            TextView minText = (TextView) view.findViewById(R.id.min_text);
            dateText.setText(forecast.date);
            infoText.setText(forecast.more.info);
            maxText.setText(forecast.temperature.max);
            minText.setText(forecast.temperature.min);
            forecastLayout.addView(view);
            i++;
            if(i == 3) {
                break;
            }
        }
        if (weather.aqi != null) {
            aqiText.setText(weather.aqi.city.aqi);
            pm25Text.setText(weather.aqi.city.pm25);
        }
        String comfort = "舒适度：" + weather.suggestion.comfort.info;
        String carWash = "洗车指数：" + weather.suggestion.carWash.info;
        String sport = "运行建议：" + weather.suggestion.sport.info;
        comfortText.setText(comfort);
        carWashText.setText(carWash);
        sportText.setText(sport);
        weatherLayout.setVisibility(View.VISIBLE);
        Intent intent = new Intent(this, AutoUpdateService.class);
        startService(intent);
    }
    private void getWeatherMap(String weatherId) {
        getNowMap(weatherId);
        getForecastMap(weatherId);
        // 测试此次刷新和风天气的数据有没有请求到
        System.out.println("--------------------------------" + nowMap.size() + forecastMap.size());
    }
    private void getNowMap(String weatherId) {
        HeWeather.getWeatherNow(WeatherActivity.this, weatherId, new HeWeather.OnResultWeatherNowListener() {
            @Override
            public void onError(Throwable throwable) {
                Log.i("min", "getWeather onError: " + throwable);
            }

            @Override
            public void onSuccess(WeatherNowBean weatherNowBean) {
                if (Code.OK.getCode().equalsIgnoreCase(weatherNowBean.getCode())) {
                    String str = new Gson().toJson(weatherNowBean);
                    // 更新日期
                    int updateTime = str.indexOf("updateTime");
                    String updateTimeStr = str.substring(updateTime + 13, updateTime + 29).replaceAll("T", " ");
                    nowMap.put("updateTimeStr", updateTimeStr);
                    // 温度
                    int feelsLike = str.indexOf("feelsLike");
                    String temp = str.substring(feelsLike + 12);
                    int indexTempLast = temp.indexOf("\"");
                    temp = temp.substring(0, indexTempLast);
                    nowMap.put("temp", temp);
                    // 天气状况
                    int text = str.indexOf("text");
                    String textStr = str.substring(text + 7);
                    int indexTextLast = textStr.indexOf("\"");
                    textStr = textStr.substring(0, indexTextLast);
                    nowMap.put("textStr", textStr);
                    // 相对湿度
                    int humidity = str.indexOf("humidity");
                    String humidityStr = str.substring(humidity + 11);
                    int indexHumidityLast = humidityStr.indexOf("\"");
                    humidityStr = humidityStr.substring(0,indexHumidityLast);
                    nowMap.put("humidityStr",humidityStr);
                    // 风向风力
                    int winDir = str.indexOf("windDir");
                    String winDirStr = str.substring(winDir + 10);
                    int indexwinDirLast = winDirStr.indexOf("\"");
                    winDirStr = winDirStr.substring(0,indexwinDirLast);
                    int winScale = str.indexOf("windScale");
                    String winScaleStr = str.substring(winScale + 12);
                    int indexwinScaleLast = winScaleStr.indexOf("\"");
                    winScaleStr = winScaleStr.substring(0,indexwinScaleLast);
                    String winDirScale = winDirStr + winScaleStr + "级";
                    nowMap.put("winDirScale",winDirScale);
                } else {
                    //在此查看返回数据失败的原因
                    String status = weatherNowBean.getCode();
                    Code code = Code.toEnum(status);
                    Log.i("Min", "failed code: " + code);
                }
            }
        });
    }
    private void getForecastMap(String weatherId) {

        HeWeather.getWeather3D(WeatherActivity.this, weatherId, new HeWeather.OnResultWeatherDailyListener() {
            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onSuccess(WeatherDailyBean weatherDailyBean) {
                //先判断返回的status是否正确，当status正确时获取数据，若status不正确，可查看status对应的Code值找到原因
                if (Code.OK.getCode().equalsIgnoreCase(weatherDailyBean.getCode())) {
                    List<WeatherDailyBean.DailyBean> dailyBeanList = weatherDailyBean.getDaily();
                    String str = new Gson().toJson(dailyBeanList);
                    // 天气状况1
                    int text1 = str.indexOf("textDay");
                    String textStr1 = str.substring(text1 + 10);
                    int indexTextLast1 = textStr1.indexOf("\"");
                    String textString1 = textStr1.substring(0, indexTextLast1);
                    // 天气状况2
                    int text2 = textStr1.indexOf("textDay");
                    String textStr2 = textStr1.substring(text2 + 10);
                    int indexTextLast2 = textStr2.indexOf("\"");
                    String textString2 = textStr2.substring(0, indexTextLast2);
                    // 天气状况3
                    int text3 = textStr2.indexOf("textDay");
                    String textStr3 = textStr2.substring(text3 + 10);
                    int indexTextLast3 = textStr3.indexOf("\"");
                    String textString3 = textStr3.substring(0, indexTextLast3);
                    forecastMap.put("txt", new String[]{textString1, textString2, textString3});
                    // 温度最大值1
                    int max1 = str.indexOf("tempMax");
                    String maxStr1 = str.substring(max1 + 10);
                    int indexMaxLast1 = maxStr1.indexOf("\"");
                    String maxString1 = maxStr1.substring(0, indexMaxLast1);
                    // 温度最大值2
                    int max2 = maxStr1.indexOf("tempMax");
                    String maxStr2 = maxStr1.substring(max2 + 10);
                    int indexMaxLast2 = maxStr2.indexOf("\"");
                    String maxString2 = maxStr2.substring(0, indexMaxLast2);
                    // 温度最大值2
                    int max3 = maxStr2.indexOf("tempMax");
                    String maxStr3 = maxStr2.substring(max3 + 10);
                    int indexMaxLast3 = maxStr3.indexOf("\"");
                    String maxString3 = maxStr3.substring(0, indexMaxLast3);
                    forecastMap.put("max", new String[]{maxString1, maxString2, maxString3});
                    // 温度最小值1
                    int min1 = str.indexOf("tempMin");
                    String minStr1 = str.substring(min1 + 10);
                    int indexMinLast1 = minStr1.indexOf("\"");
                    String minString1 = minStr1.substring(0, indexMinLast1);
                    // 温度最小值2
                    int min2 = minStr1.indexOf("tempMin");
                    String minStr2 = minStr1.substring(min2 + 10);
                    int indexMinLast2 = minStr2.indexOf("\"");
                    String minString2 = minStr2.substring(0, indexMinLast2);
                    // 温度最小值2
                    int min3 = minStr2.indexOf("tempMin");
                    String minStr3 = minStr2.substring(min3 + 10);
                    int indexMinLast3 = minStr3.indexOf("\"");
                    String minString3 = minStr3.substring(0, indexMinLast3);
                    forecastMap.put("min", new String[]{minString1, minString2, minString3});
                } else {
                    //在此查看返回数据失败的原因
                    String status = weatherDailyBean.getCode();
                    Code code = Code.toEnum(status);
                    Log.i("Min", "failed code: " + code);
                }
            }
        });
    }

}