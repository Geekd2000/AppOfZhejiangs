package com.example.appofzhejiang;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import com.example.appofzhejiang.coolweather.WeatherActivity;
import com.example.appofzhejiang.recyclerpage.RecyclerBeanListUtil;
import com.example.appofzhejiang.recyclerpage.RecyclerType;
import com.example.appofzhejiang.recyclerpage.RecyclerPageActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.appofzhejiang.CustomDialog.CustomDialog;
import com.example.appofzhejiang.coolweather.CoolWeatherActivity;
import com.example.appofzhejiang.coolweather.gson.Weather;
import com.example.appofzhejiang.coolweather.json.JsonCity;
import com.example.appofzhejiang.coolweather.json.JsonProvince;
import com.example.appofzhejiang.coolweather.json.WeatherCity;
import com.example.appofzhejiang.coolweather.util.HttpUtil;
import com.example.appofzhejiang.coolweather.util.Utility;
import com.example.appofzhejiang.recyclerpage.RecyclerPageAdapter;
import com.example.appofzhejiang.tourismculture.TourismCultureAreaActivity;
import com.example.appofzhejiang.tourismculture.TourismCultureFestivalActivity;
import com.example.appofzhejiang.tourismculture.TourismCultureFolkActivity;
import com.example.appofzhejiang.tourismculture.TourismCultureHistoryActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.base.Code;
import interfaces.heweather.com.interfacesmodule.bean.weather.WeatherNowBean;
import interfaces.heweather.com.interfacesmodule.view.HeConfig;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyFragment1 extends Fragment {
    public LocationClient mLocationClient;
    private View view; // 定义view用来设置fragment的layout
    public RecyclerView recyclerView; // 定义RecyclerView
    private FragmentAdapter1 fragmentAdapter1;  // 自定义RecyclerView的适配器
    private TextView txtCity; // 当前城市
    private TextView txtWeather; // 当前天气
    private TextView txtPoem;// 诗画浙江
    private TextView txtSearch;//搜索按钮
    private EditText editSearch;//搜索框
    private ImageView indexNewsImage; // 专题新闻
    private ImageView indexFolkImage; // 民族风情
    private ImageView indexAreaImage; // 地域概况
    private ImageView indexHistoryImage; // 历史故事
    private ImageView indexFestivalImage; // 历史故事
    private String currentCity; // 当前城市
    private String currentProvince; // 当前省份
    private String weatherId; // 当前weatherId;

<<<<<<< HEAD
    public MyFragment1(){}
=======
>>>>>>> 437de8f... 优化首页

    // 传入默认城市名称
    public MyFragment1(String province, String city) {
        this.currentProvince = province;
        this.currentCity = city;
        // 如果获取的城市名字最后面带有市或省份后面带有省，要去除
        removeRedundantWord();
    }

    /**
     * 此方法会在onCreateView方法前执行，因为fragemnt有缓存机制导致页面切换时城市信息不同步
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // 如果缓存中有城市信息，则从缓存中获取城市
            String cityInfo = load("data_cityInfo");
            if (cityInfo != null && !"".equals(cityInfo.trim())) {
                currentProvince = cityInfo.split("=")[0];
                currentCity = cityInfo.split("=")[1];
                txtCity = view.findViewById(R.id.txt_city);
                txtCity.setText(this.currentCity);
                // 设置天气
                setWeather(currentProvince, currentCity);
                // 设置城市给攻略
                txtPoem.setText("攻略 · " + currentCity);
                // 初始化首页的RecyclerView
                initRecyclerView();

            }
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my1, container, false);


        // 设置城市定位 start

        // 如果缓存中有城市信息，则从缓存中获取城市
        String cityInfo = load("data_cityInfo");
        if (cityInfo != null && !"".equals(cityInfo.trim())) {
            currentProvince = cityInfo.split("=")[0];
            currentCity = cityInfo.split("=")[1];
        }
        txtCity = view.findViewById(R.id.txt_city);
        txtCity.setText(this.currentCity);

        //定位弹窗
        txtCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog customDialog = new CustomDialog(getActivity());
                customDialog.setCancel("取消", new CustomDialog.IOnCancelListener() {
                    @Override
                    public void onCancel(CustomDialog dialog) {
                        // 若取消，就打开选择城市页面进行手动选择
                        setCity();

                    }
                }).setConfirm("确定", new CustomDialog.IOnConfirmListener() {
                    @Override
                    public void onConfirm(CustomDialog dialog) {
                        // 定位城市，开启权限
                        setLocated();

                    }
                }).show();
            }
        });
        // 设置城市定位 end

        //搜索框
        txtSearch = view.findViewById(R.id.txt_search);
        editSearch = view.findViewById(R.id.main_search);
        txtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = editSearch.getText().toString();
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
            }
        });

        // 初始化首页的RecyclerView，没有缓存信息时使用
        initRecyclerView();


        // 设置天气
        txtWeather = view.findViewById(R.id.txt_weather);
        setWeather(currentProvince, currentCity);
        txtWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CoolWeatherActivity.class);
                intent.putExtra("weatherId", weatherId);
                startActivity(intent);
            }
        });

        // 点击专题新闻
        indexNewsImage = (ImageView) view.findViewById(R.id.index_news);
        indexNewsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RecyclerPageActivity.class);
                intent.putExtra("currentCity", currentCity);
                intent.putExtra("type", RecyclerType.NEWS);
                startActivity(intent);
            }
        });

        // 点击攻略·地点TextView事件
        txtPoem = (TextView) view.findViewById(R.id.txt_poem);
        txtPoem.setText("攻略 · " + currentCity);
        txtPoem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RecyclerPageActivity.class);
                intent.putExtra("currentCity", currentCity);
                intent.putExtra("type", RecyclerType.STRATEGY);
                startActivity(intent);
            }
        });

        // 设置民族风情点击事件
        indexFolkImage = (ImageView) view.findViewById(R.id.index_folk);
        indexFolkImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TourismCultureFolkActivity.class);
                intent.putExtra("currentCity", currentCity);
                startActivity(intent);
            }
        });

        // 设置地域概况点击事件
        indexAreaImage = (ImageView) view.findViewById(R.id.index_area);
        indexAreaImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TourismCultureAreaActivity.class);
                intent.putExtra("currentCity", currentCity);
                startActivity(intent);
            }
        });

        // 设置历史故事点击事件
        indexHistoryImage = (ImageView) view.findViewById(R.id.index_history);
        indexHistoryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TourismCultureHistoryActivity.class);
                intent.putExtra("currentCity", currentCity);
                startActivity(intent);
            }
        });

        // 设置传统节日点击事件
        indexFestivalImage = (ImageView) view.findViewById(R.id.index_festival);
        indexFestivalImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TourismCultureFestivalActivity.class);
                intent.putExtra("currentCity", currentCity);
                startActivity(intent);
            }
        });
        return view;
    }

    /**
     * 对RecycleView进行配置
     */
    private void initRecyclerView() {

        recyclerView = view.findViewById(R.id.fg1_recyclerView);
        RecyclerPageAdapter adapter = new RecyclerPageAdapter(new RecyclerBeanListUtil(currentCity,null).getRecyclerBeanList());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    /**
     * 设置Myfragment1页面上跟天气有关的信息
     */

    private void setWeather(final String province, String city) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 通过省份和城市来获取weatherId start
                    // 第一次请求，目的：得到省份id
                    OkHttpClient client1 = new OkHttpClient();
                    String url = "http://guolin.tech/api/china";
                    Request request1 = new Request.Builder().url(url).build();
                    Response response1 = client1.newCall(request1).execute();
                    String responseData1 = response1.body().string();
                    int provinceId = parseJSON1(responseData1); // 得到省份id

                    /// 第二次请求，目的：得到城市id
                    OkHttpClient client2 = new OkHttpClient();
                    url = url + "/" + provinceId;
                    Request request2 = new Request.Builder().url(url).build();
                    Response response2 = client2.newCall(request2).execute();
                    String responseData2 = response2.body().string();
                    int cityId = parseJSON2(responseData2); // 得到城市id

                    // 第三次请求，目的：得到城市weatherId
                    OkHttpClient client3 = new OkHttpClient();
                    url = url + "/" + cityId;
                    Request request3 = new Request.Builder().url(url).build();
                    Response response3 = client3.newCall(request3).execute();
                    String responseData3 = response3.body().string();
                    String wId = parseJSON3(responseData3); // 得到城市id
                    weatherId = wId;
                    // 通过省份和城市来获取weatherId end

                    //设置Myfragment1页面上跟天气有关的信息
                    if (weatherId == null) {
                        txtWeather.setText("无法获取当地天气");
                    } else {
                        String degree = requestDegree(weatherId);
                        if (degree != null) {
                            txtWeather.setText(currentCity + "天气：" + degree);
                        } else {
                            txtWeather.setText("当地天气：");
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 获取degree
     */
    public String requestDegree(final String weatherId) {
        final boolean[] flag1 = {false};
        final String[] tempe = {null};
        HeWeather.getWeatherNow(getContext(), weatherId, new HeWeather.OnResultWeatherNowListener() {
            @Override
            public void onError(Throwable throwable) {
                Log.i("min", "getWeather onError: " + throwable);
            }

            @Override
            public void onSuccess(WeatherNowBean weatherNowBean) {
                if (Code.OK.getCode().equalsIgnoreCase(weatherNowBean.getCode())) {
                    String str = new Gson().toJson(weatherNowBean);
                    // 温度
                    int feelsLike = str.indexOf("feelsLike");
                    String temp = str.substring(feelsLike + 12);
                    int indexTempLast = temp.indexOf("\"");
                    tempe[0] = temp.substring(0, indexTempLast);
                    flag1[0] = true;
                } else {
                    //在此查看返回数据失败的原因
                    String status = weatherNowBean.getCode();
                    Code code = Code.toEnum(status);
                    Log.i("Min", "failed code: " + code);
                }
            }
        });
        if (flag1[0]) {
            return tempe[0];
        } else {
            String weatherUrl = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=8fb20072276e48aa83d1200cce3653e0";
            final Weather[] weather = new Weather[1];
            save("false", "data_weather");
            HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    final String responseText = response.body().string();
                    weather[0] = Utility.handleWeatherResponse(responseText);
                    save("true", "data_weather");
                }

                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "获取天气信息失败", Toast.LENGTH_SHORT).show();
                            save("true", "data_weather");
                        }
                    });
                }
            });

            // 等待okHttp返回数据
            while (true) {
                String flag = load("data_weather");
                if ("true".equalsIgnoreCase(flag)) {
                    break;
                }
            }

            String degree = null;
            if (weather[0] != null) {
                degree = weather[0].now.temperature + "℃";
            }
            return degree;
        }

    }


    /**
     * 功能：声明LocationClient，并添加监听器，声明相关权限
     */
    private void setLocated() {
        mLocationClient = new LocationClient(getActivity().getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            requestLocation();
        }
    }

    /**
     * 功能：初始化位置信息，并开启LocationClient
     */
    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }

    /**
     * 功能：初始化位置信息
     */
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
//        option.setScanSpan(5000); // 实时更新
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }


    /**
     * 功能：停止LocationClient，防止其在后台一直运行
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLocationClient != null) {
            mLocationClient.stop();
        }
    }


    /**
     * 功能：判断用户是否同意开启权限
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requestLocation();
                } else {
                    // 如果不同意开启位置服务的话，可以手动选择城市
                    setCity();

                }
                break;
            default:
        }
    }

    /**
     * 功能：打开选择城市页面，可以设置热点城市
     */
    private void setCity() {
        List<HotCity> hotCities = new ArrayList<>();
        hotCities.add(new HotCity("北京", "北京", "101010100"));
        hotCities.add(new HotCity("上海", "上海", "101020100"));
        hotCities.add(new HotCity("广州", "广东", "101280101"));
        hotCities.add(new HotCity("深圳", "广东", "101280601"));
        hotCities.add(new HotCity("杭州", "浙江", "101210101"));

        CityPicker.getInstance()
                .setFragmentManager(getActivity().getSupportFragmentManager())  // 此方法必须调用
                .enableAnimation(true)
//                .setLocatedCity(new LocatedCity("杭州", "浙江", "101210101"))  // APP自身已定位的城市，默认为null（定位失败），有问题
                .setHotCities(hotCities)  //指定热门城市
                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {

                        if (data == null) {
                            return;
                        }

                        // 获取选择的省份和城市
                        currentCity = data.getName();
                        currentProvince = data.getProvince();

                        // 如果获取的城市名字最后面带有市或省份后面带有省，要去除
                        removeRedundantWord();

                        // 设置城市
                        txtCity.setText(currentCity);

                        // 设置天气
                        setWeather(currentProvince, currentCity);

                        // 设置城市给攻略
                        txtPoem.setText("攻略 · " + currentCity);

                        // 初始化首页的RecyclerView
                        initRecyclerView();

                        // 把城市信息缓存到本地
                        save(currentProvince + "=" + currentCity, "data_cityInfo");


                    }

                    @Override
                    public void onLocate() {
                        //开始定位，这里模拟一下定位
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                //定位完成之后更新数据
//                                setLocated();
//                                CityPicker.getInstance()
//                                        .locateComplete(new LocatedCity("深圳", "广东", "101280601"), LocateState.SUCCESS);
//                            }
//                        }, 2000);
                    }
                })
                .show();
    }

    // LocationClient监听器，要先LocationClient.start才起作用
    public class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // 通过定位获取当前位置的省份和城市
            currentCity = location.getCity();
            currentProvince = location.getProvince();

            // 如果获取的城市名字最后面带有市或省份后面带有省，要去除
            removeRedundantWord();

            // 设置城市
            txtCity.setText(currentCity);

            // 设置天气
            setWeather(currentProvince, currentCity);

            // 设置城市给攻略
            txtPoem.setText("攻略 · " + currentCity);

            // 初始化首页的RecyclerView
            initRecyclerView();

            // 把城市信息缓存到本地
            save(currentProvince + "=" + currentCity, "data_cityInfo");


        }
    }

    /**
     * 功能：如果城市名字最后面带有市或省份后面带有省，要去除
     */
    private void removeRedundantWord() {
        if (currentCity.lastIndexOf("市") == (currentCity.length() - 1)) {
            currentCity = currentCity.substring(0, currentCity.length() - 1);
        }
        if (currentProvince.lastIndexOf("省") == (currentProvince.length() - 1)) {
            currentProvince = currentProvince.substring(0, currentProvince.length() - 1);
        }
    }

    /**
     * 解析JSON第一次，得到provinceId
     */
    private int parseJSON1(String responseData) {
        int provinceId = 1;
        Gson gson = new Gson();
        List<JsonProvince> provinceList = gson.fromJson(responseData, new TypeToken<List<JsonProvince>>() {
        }.getType());
        for (JsonProvince province : provinceList) {
            if (province.getName().equalsIgnoreCase(currentProvince)) {
                provinceId = province.getId();
                break;
            }
        }
        return provinceId;

    }

    /**
     * 解析JSON第二次
     */
    private int parseJSON2(String responseData) {
        int cityId = 1;
        Gson gson = new Gson();
        List<JsonCity> cityList = gson.fromJson(responseData, new TypeToken<List<JsonCity>>() {
        }.getType());
        for (JsonCity city : cityList) {
            if (city.getName().equalsIgnoreCase(currentCity)) {
                cityId = city.getId();
                break;
            }
        }
        return cityId;

    }

    /**
     * 解析JSON第三次
     */
    private String parseJSON3(String responseData) {
        String weatherId = null;
        Gson gson = new Gson();
        List<WeatherCity> cityList = gson.fromJson(responseData, new TypeToken<List<WeatherCity>>() {
        }.getType());
        for (WeatherCity weatherCity : cityList) {
            if (weatherCity.getName().equalsIgnoreCase(currentCity)) {
                weatherId = weatherCity.getWeather_id();
                break;
            }
        }
        return weatherId;

    }

    /**
     * 把数据存储在本地
     */
    private void save(String textStr, String location) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = getActivity().openFileOutput(location, Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(textStr);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从本地读取数据
     */
    private String load(String location) {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuffer sBuffer = new StringBuffer();
        try {
            in = getActivity().openFileInput(location);
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            if ((line = reader.readLine()) != null) {
                sBuffer.append(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sBuffer.toString();
    }


}