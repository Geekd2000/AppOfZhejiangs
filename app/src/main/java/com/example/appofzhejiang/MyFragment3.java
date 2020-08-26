package com.example.appofzhejiang;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.example.appofzhejiang.fragment1.CustomDialog.CustomDialog;
import com.example.appofzhejiang.fragment3.Ticket;
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
import java.util.List;

public class MyFragment3 extends Fragment {

    private View view;
    private RecyclerView recyclerView,horView;
    private FragmentAdapter3_2 fragmentAdapter3_2;
    private FragmentAdapter3_1 fragmentAdapter3_1;
    private String content;
    private TextView txtCity, txtTicket, txtHotel, txtTaxi, txtGuider, txtNongjiale, txtFood, txtCommodity, txtSearch;
    private EditText editSearch;//搜索框
    private List<Ticket> ticketList;
    private String currentCity; // 当前城市
    private String currentProvince; // 当前省份
    public LocationClient mLocationClient;//定位

    public MyFragment3(){}

    // 传入默认城市名称
    public MyFragment3(String province, String city) {
        this.currentProvince = province;
        this.currentCity = city;
        // 如果获取的城市名字最后面带有市或省份后面带有省，要去除
        removeRedundantWord();
    }

    /**
     * 此方法会在onCreateView方法前执行，因为fragemnt有缓存机制导致页面切换时城市信息不同步
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            // 如果缓存中有城市信息，则从缓存中获取城市
            String cityInfo = load("data_cityInfo");
            if (cityInfo != null && !"".equals(cityInfo.trim())) {
                currentProvince = cityInfo.split("=")[0];
                currentCity = cityInfo.split("=")[1];
                txtCity = view.findViewById(R.id.txt_city);
                txtCity.setText(this.currentCity);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my3, container, false);
        init();
        //设初始化RecyclerView
        initRecyclerView();
        initHorRecyclerView();

        // 如果缓存中有城市信息，则从缓存中获取城市
        String cityInfo = load("data_cityInfo");
        if (cityInfo != null && !"".equals(cityInfo.trim())) {
            currentProvince = cityInfo.split("=")[0];
            currentCity = cityInfo.split("=")[1];
        }
        txtCity = view.findViewById(R.id.txt_city);
        txtCity.setText(this.currentCity);

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

        txtSearch = view.findViewById(R.id.txt_search);
        editSearch = view.findViewById(R.id.main_search);
        txtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = editSearch.getText().toString();
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
            }
        });
        initRecyclerView();
        return view;
    }

    /**
     * 对垂直RecycleView进行配置
     */
    private void initRecyclerView() {
        recyclerView = view.findViewById(R.id.fg3_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        fragmentAdapter3_2 = new FragmentAdapter3_2(ticketList, getActivity());
        recyclerView.setAdapter(fragmentAdapter3_2);
    }

    /**
     * 对水平RecycleView进行配置
     */
    private void initHorRecyclerView() {
        horView=view.findViewById(R.id.fg3_recyclerView_zuoyou);
        LinearLayoutManager horLayoutManager = new LinearLayoutManager(getActivity());
        horLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        horView.setLayoutManager(horLayoutManager);
        fragmentAdapter3_1=new FragmentAdapter3_1(getActivity());
        horView.setAdapter(fragmentAdapter3_1);
    }

    public void init() {
        ticketList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Ticket ticket = new Ticket(R.drawable.westlake, "杭州西湖" + i, "35", "超级无敌大公司", "666");
            ticketList.add(ticket);
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

            // 把城市信息缓存到本地
            save(currentProvince + "=" + currentCity, "data_cityInfo");
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