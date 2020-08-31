package com.example.appofzhejiang;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.appofzhejiang.fragment1.bannerpic.BannerPorcelainActivity;
import com.example.appofzhejiang.fragment1.bannerpic.BannerSilkActivity;
import com.example.appofzhejiang.fragment1.bannerpic.BannerTeaActivity;
import com.example.appofzhejiang.fragment1.beautifulzj.BeautifulZJActivity;
import com.example.appofzhejiang.fragment1.culturevideo.CultureVideoActivity;
import com.example.appofzhejiang.fragment1.hotstrategy.HotStrategy1Activity;
import com.example.appofzhejiang.fragment1.hotstrategy.HotStrategy2Activity;
import com.example.appofzhejiang.fragment1.hotstrategy.HotStrategy3Activity;
import com.example.appofzhejiang.fragment1.util.LocalImageLoader;
import com.example.appofzhejiang.fragment1.recyclerpage.RecyclerBean;
import com.example.appofzhejiang.fragment1.recyclerpage.RecyclerBeanListUtil;
import com.example.appofzhejiang.fragment1.recyclerpage.RecyclerType;
import com.example.appofzhejiang.fragment1.recyclerpage.RecyclerPageActivity;


import android.graphics.Typeface;


import android.os.Bundle;
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

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.appofzhejiang.CustomDialog.CustomDialog;

import com.example.appofzhejiang.fragment1.tourismculture.TourismCultureAreaActivity;
import com.example.appofzhejiang.fragment1.tourismculture.TourismCultureDramaActivity;
import com.example.appofzhejiang.fragment1.tourismculture.TourismCultureFestivalActivity;
import com.example.appofzhejiang.fragment1.tourismculture.TourismCultureFolkActivity;
import com.example.appofzhejiang.fragment1.tourismculture.TourismCultureHeritageActivity;
import com.example.appofzhejiang.fragment1.tourismculture.TourismCultureHistoryActivity;
import com.example.appofzhejiang.fragment1.tourismculture.TourismCultureIndustrialActivity;
import com.example.appofzhejiang.fragment1.tourismculture.TourismCulturePoemZJActivity;
import com.youth.banner.Banner;

import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
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
import java.util.LinkedList;
import java.util.List;

public class MyFragment1 extends Fragment {
    public LocationClient mLocationClient;
    private View view; // 定义view用来设置fragment的layout
    private TextView txtCity; // 当前城市
    private TextView txtSearch;//搜索按钮
    private EditText editSearch;//搜索框
    private String currentCity; // 当前城市
    private String currentProvince; // 当前省份

    private List<Integer> imagesPath; // 轮播图地址
    private List<String> imagesTile; // 轮播图标题
    private LocalImageLoader localImageLoader; // 轮播图Loder
    private Banner banner; // 轮播图板块

    private View culturePoemZJView; // 诗画浙江
    private View cultureHeritageView; // 文化遗产
    private View cultureDramaView; // 戏剧文化
    private View cultureIndustrialView; // 工艺美术
    private View areaView; // 地域概况

    private View cultureFolkView; // 民俗文化
    private View festivalView; // 传统节日
    private View historyView; // 历史沿革
    private View beautifulZJView; // 最美浙江
    private View video1View; // 视频1
    private View video2View; // 视频2
    private View video3View; // 视频3
    private View video4View; // 视频4

    private ImageView hotImageView1; // 热门攻略 图1
    private ImageView hotImageView2; // 热门攻略 图2
    private ImageView hotImageView3; // 热门攻略 图3
    private TextView hotImage1Title; // 热门攻略 标题1
    private TextView hotImage2Title; // 热门攻略 标题2
    private TextView hotImage3Title; // 热门攻略 标题3
    private View hotStrategy1; // 热门攻略1
    private View hotStrategy2; // 热门攻略2
    private View hotStrategy3; // 热门攻略3
    private View strategyMore; // 热门攻略->更多

    public MyFragment1() {
    }

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
                // 设置轮播图数据
                setBanner();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my1, container, false);


        // 处理小浙特别提醒
        setLittleRemind();

        // 设置轮播图数据
        setBanner();

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

        // 设置宣传栏1的点击事件
        getClick1View();
        setClick1();

        // 设置宣传栏2的点击事件
        getClick2View();
        setClick2();

        // 设置热点攻略
        getHotStrategyView();
        setHotStrategy();

        return view;
    }

    /**
     * 如果没存数据就正常显示，存数据了就隐藏不显示
     */
    private void setLittleRemind() {
        final String isVisibility = load("data_littleRemind");
        final View littleRemindView = view.findViewById(R.id.little_zj_remind);
        // 等待view加载完成后再执行
        littleRemindView.post(new Runnable() {
            @Override
            public void run() {
                if (isVisibility == null || "".equals(isVisibility.trim())) {
                    // 如果没有数据，说明没有被操作过，则显示；否则不显示。默认不显示
                    View littleCloseView = view.findViewById(R.id.little_zj_close);
                    View littleFindView = view.findViewById(R.id.little_zj_find);

                    littleRemindView.setVisibility(View.VISIBLE);

                    littleCloseView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            littleRemindView.setVisibility(View.GONE);
                            save("false", "data_littleRemind");
                        }
                    });
                    littleFindView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // 进入图片展示区 或 关于浙江的优雅美文
                            Intent intent = new Intent(getActivity(), BeautifulZJActivity.class);
                            startActivity(intent);
                        }
                    });

                }
            }
        });

    }

    /**
     * 获取宣传栏1的LineanerLayout
     */
    private void getClick1View() {
        cultureFolkView = view.findViewById(R.id.index_culture_folk);
        culturePoemZJView = view.findViewById(R.id.index_poem_zj);
        cultureHeritageView = view.findViewById(R.id.index_culture_heritage);
        cultureDramaView = view.findViewById(R.id.index_culture_drama);
        cultureIndustrialView = view.findViewById(R.id.index_culture_industrial);
    }

    private void setClick1() {
        culturePoemZJView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TourismCulturePoemZJActivity.class);
                intent.putExtra("currentCity", currentCity);
                startActivity(intent);
            }
        });
        cultureHeritageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TourismCultureHeritageActivity.class);
                intent.putExtra("currentCity", currentCity);
                startActivity(intent);
            }
        });
        cultureDramaView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TourismCultureDramaActivity.class);
                intent.putExtra("currentCity", currentCity);
                startActivity(intent);
            }
        });
        cultureIndustrialView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TourismCultureIndustrialActivity.class);
                intent.putExtra("currentCity", currentCity);
                startActivity(intent);
            }
        });

        cultureFolkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TourismCultureFolkActivity.class);
                intent.putExtra("currentCity", currentCity);
                startActivity(intent);
            }
        });
    }

    private void getClick2View() {
        areaView = view.findViewById(R.id.index_area);
        festivalView = view.findViewById(R.id.index_festival);
        historyView = view.findViewById(R.id.index_history);
        beautifulZJView = view.findViewById(R.id.index_beautiful_zj);
        video1View = view.findViewById(R.id.index_video1);
        video2View = view.findViewById(R.id.index_video2);
        video3View = view.findViewById(R.id.index_video3);
        video4View = view.findViewById(R.id.index_video4);
    }

    private void setClick2() {
        areaView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TourismCultureAreaActivity.class);
                intent.putExtra("currentCity", currentCity);
                startActivity(intent);
            }
        });
        festivalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TourismCultureFestivalActivity.class);
                intent.putExtra("currentCity", currentCity);
                startActivity(intent);
            }
        });
        historyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TourismCultureHistoryActivity.class);
                intent.putExtra("currentCity", currentCity);
                startActivity(intent);
            }
        });
        beautifulZJView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), BeautifulZJActivity.class);
                startActivity(intent);
            }
        });
        video1View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CultureVideoActivity.class);
                intent.putExtra("type", "1");
                startActivity(intent);
            }
        });
        video2View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CultureVideoActivity.class);
                intent.putExtra("type", "2");
                startActivity(intent);
            }
        });
        video3View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CultureVideoActivity.class);
                intent.putExtra("type", "3");
                startActivity(intent);
            }
        });
        video4View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CultureVideoActivity.class);
                intent.putExtra("type", "4");
                startActivity(intent);
            }
        });
    }

    /**
     * 获取热点攻略的图片
     */
    private void getHotStrategyView() {
        hotImageView1 = view.findViewById(R.id.strategy_hot_img1);
        hotImageView2 = view.findViewById(R.id.strategy_hot_img2);
        hotImageView3 = view.findViewById(R.id.strategy_hot_img3);
        strategyMore = view.findViewById(R.id.stratrgy_more);
        hotImage1Title = view.findViewById(R.id.hot_img1_title);
        hotImage2Title = view.findViewById(R.id.hot_img2_title);
        hotImage3Title = view.findViewById(R.id.hot_img3_title);
        hotStrategy1 = view.findViewById(R.id.strategy_hot1);
        hotStrategy2 = view.findViewById(R.id.strategy_hot2);
        hotStrategy3 = view.findViewById(R.id.strategy_hot3);
    }

    private void setHotStrategy() {
        strategyMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RecyclerPageActivity.class);
                intent.putExtra("currentCity", currentCity);
                intent.putExtra("type", RecyclerType.STRATEGY);
                startActivity(intent);
            }
        });
        hotStrategy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HotStrategy1Activity.class);
                intent.putExtra("currentCity", currentCity);
                startActivity(intent);
            }
        });
        hotStrategy2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HotStrategy2Activity.class);
                intent.putExtra("currentCity", currentCity);
                startActivity(intent);
            }
        });
        hotStrategy3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HotStrategy3Activity.class);
                intent.putExtra("currentCity", currentCity);
                startActivity(intent);
            }
        });

        List<RecyclerBean> recyclerBeanList = new RecyclerBeanListUtil(currentCity, RecyclerType.STRATEGY).getRecyclerBeanList();
        List<String> urls = new LinkedList<>();
        List<String> titles = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            RecyclerBean bean = recyclerBeanList.get(i);
            urls.add(bean.getPictures());
            titles.add(bean.getTitle());
        }
        Glide.with(getContext())
                .load(urls.get(0))
                .dontAnimate()
                .into(hotImageView1);
        Glide.with(getContext())
                .load(urls.get(1))
                .dontAnimate()
                .into(hotImageView2);
        Glide.with(getContext())
                .load(urls.get(2))
                .dontAnimate()
                .into(hotImageView3);
        hotImage1Title.setText(titles.get(0));
        hotImage2Title.setText(titles.get(1));
        hotImage3Title.setText(titles.get(2));
    }


    private void useGlide(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView);
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

    private void setBanner() {
        initData();
        localImageLoader = new LocalImageLoader();
        banner = view.findViewById(R.id.fg1_banner);
        if (banner != null) {

            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
            banner.setImageLoader(localImageLoader);
            banner.setBannerTitles(imagesTile);
            banner.setImages(imagesPath);
            banner.setDelayTime(4000);
            banner.isAutoPlay(true);
            view.findViewById(com.youth.banner.R.id.titleView).getBackground().setAlpha(0); // 设置标题背景透明
            ((TextView) view.findViewById(com.youth.banner.R.id.bannerTitle)).setTypeface(Typeface.create("sans", Typeface.BOLD));
            banner.setIndicatorGravity(BannerConfig.RIGHT);


            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    switch (position) {
                        case 0:
                            Intent intent0 = new Intent(getActivity(), RecyclerPageActivity.class);
                            intent0.putExtra("currentCity", currentCity);
                            intent0.putExtra("type", RecyclerType.NEWS);
                            startActivity(intent0);
                            break;
                        case 1:
                            Intent intent1 = new Intent(getActivity(), BannerSilkActivity.class);
                            intent1.putExtra("currentCity", currentCity);
                            startActivity(intent1);
                            break;
                        case 2:
                            Intent intent2 = new Intent(getActivity(), BannerTeaActivity.class);
                            intent2.putExtra("currentCity", currentCity);
                            startActivity(intent2);
                            break;
                        case 3:
                            Intent intent3 = new Intent(getActivity(), BannerPorcelainActivity.class);
                            intent3.putExtra("currentCity", currentCity);
                            startActivity(intent3);
                            break;
                    }
                }
            });
            banner.start();
        }

    }

    private void initData() {
        imagesTile = new LinkedList<>();
        imagesPath = new LinkedList<>();

        imagesPath.add(R.drawable.index_news);
        imagesPath.add(R.drawable.index_silk);
        imagesPath.add(R.drawable.index_tea);
        imagesPath.add(R.drawable.index_chinaware);
        imagesTile.add("专题新闻");
        imagesTile.add("最具\"中国元素\"的商品－丝绸");
        imagesTile.add("最具\"中国元素\"的商品－茶叶");
        imagesTile.add("最具\"中国元素\"的商品－瓷器");
    }


}