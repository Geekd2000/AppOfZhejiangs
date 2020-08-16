package com.example.appofzhejiang;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.example.appofzhejiang.CustomDialog.CustomDialog;
import com.example.appofzhejiang.R;
import com.example.appofzhejiang.fragment3.IndicatorTabBar;
import com.example.appofzhejiang.fragment3.Ticket;
import com.example.appofzhejiang.fragment3.TicketActivity;
import com.example.appofzhejiang.fragment3.TicketDetailActivity;
import com.example.appofzhejiang.fragment3.hotel.Hotel;
import com.example.appofzhejiang.fragment3.hotel.HotelAdapter;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyFragment3 extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private FragmentAdapter3 fragmentAdapter3;
    private String content;
    private TextView txtCity, txtTicket, txtHotel, txtTaxi, txtGuider, txtNongjiale, txtFood, txtCommodity, txtSearch;
    private EditText editSearch;//搜索框
    private List<Ticket> ticketList;
    private String currentCity; // 当前城市
    private String currentProvince; // 当前省份
    public LocationClient mLocationClient;//定位

    // 传入默认城市名称
    public MyFragment3(String province, String city) {
        this.currentProvince = province;
        this.currentCity = city;
        // 如果获取的城市名字最后面带有市或省份后面带有省，要去除
        removeRedundantWord();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my3, container, false);
        init();
        RecyclerView recyclerView = view.findViewById(R.id.fg3_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(new MainActivity());
        recyclerView.setLayoutManager(layoutManager);
        fragmentAdapter3 = new FragmentAdapter3(ticketList, getActivity());
        recyclerView.setAdapter(fragmentAdapter3);
        txtCity = view.findViewById(R.id.txt_city);
        txtCity.setText(currentCity);
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
        txtTicket = view.findViewById(R.id.txt_ticket);
        txtTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TicketActivity.class);
                intent.putExtra("num", "0");
                startActivity(intent);
            }
        });
        txtHotel = view.findViewById(R.id.txt_hotel);
        txtHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TicketActivity.class);
                intent.putExtra("num", "1");
                startActivity(intent);
            }
        });
        txtTaxi = view.findViewById(R.id.txt_taxi);
        txtTaxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TicketActivity.class);
                intent.putExtra("num", "2");
                startActivity(intent);
            }
        });
        txtGuider = view.findViewById(R.id.txt_guider);
        txtGuider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TicketActivity.class);
                intent.putExtra("num", "3");
                startActivity(intent);
            }
        });
        txtNongjiale = view.findViewById(R.id.txt_nongjiale);
        txtNongjiale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TicketActivity.class);
                intent.putExtra("num", "4");
                startActivity(intent);
            }
        });
        txtFood = view.findViewById(R.id.txt_food);
        txtFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TicketActivity.class);
                intent.putExtra("num", "5");
                startActivity(intent);
            }
        });
        txtCommodity = view.findViewById(R.id.txt_commodity);
        txtCommodity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TicketActivity.class);
                intent.putExtra("num", "6");
                startActivity(intent);
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
     * 对RecycleView进行配置
     */
    private void initRecyclerView() {
        //获取RecyclerView
        recyclerView = view.findViewById(R.id.fg3_recyclerView);
        //创建Adapter
        fragmentAdapter3 = new FragmentAdapter3(ticketList, getActivity());
        //给RecyclerView设置Adapter
        recyclerView.setAdapter(fragmentAdapter3);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置item的分割线
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
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
//                        Toast.makeText(getActivity().getApplicationContext(), data.getName(), Toast.LENGTH_SHORT).show();

                        if(data == null){
                            return;
                        }

                        // 获取选择的省份和城市
                        currentCity = data.getName();
                        currentProvince = data.getProvince();

                        // 如果获取的城市名字最后面带有市或省份后面带有省，要去除
                        removeRedundantWord();
                        // 设置城市
                        txtCity.setText(currentCity);

                        // 把省份和城市信息传给CoolWeatherActivity
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
}