package com.example.appofzhejiang.xihu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.example.appofzhejiang.R;
import com.example.appofzhejiang.StatusBarUtil.StatusBarUtil;

import java.util.List;

public class Jingqu_around_park extends AppCompatActivity {

    private static int count = 0;
    private MapView mMapView;
    private BaiduMap mBaidumap;
    private Toolbar mBack;
    private PoiSearch poiSearch;
    private OnGetPoiSearchResultListener poiListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_jingqu_around_park);
        //设置沉浸式
        StatusBarUtil.setTransparent(this);
        StatusBarUtil.setDarkFont(this);
        //返回按钮
        mBack=findViewById(R.id.toolbar);
        mBack.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mMapView = findViewById(R.id.map_baidu);//初始化地图
        mBaidumap = mMapView.getMap();

        LatLng cenpt = new LatLng(30.22730, 120.12979); //设定中心点坐标

        MapStatus mMapStatus = new MapStatus.Builder()//定义地图状态
                .target(cenpt)
                .zoom(18)
                .build();  //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        nearbyPoiSearch(mBaidumap);

        mBaidumap.setMapStatus(mMapStatusUpdate);//改变地图状态
    }

    private void nearbyPoiSearch(final BaiduMap mBaidumap) {
        //创建poi检索实例
        poiSearch = PoiSearch.newInstance();
        //创建poi监听者
        poiListener = new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult result) {
                if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
                    Log.e(String.valueOf(getClass()), "onGetPoiResult: blank" );
                    return;
                }
                if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                    List<PoiInfo> point = result.getAllPoi();
//构建Marker图标
                    for (PoiInfo poiInfo : point) {
                        LatLng poiLocation = poiInfo.location;
//构建Marker图标
                        BitmapDescriptor bitmap = BitmapDescriptorFactory
                                .fromResource(R.drawable.park);
//构建MarkerOption，用于在地图上添加Marker
                        OverlayOptions option = new MarkerOptions()
                                .position(poiLocation)
                                .icon(bitmap);
//在地图上添加Marker，并显示
                        mBaidumap.addOverlay(option);

                        LatLng llText = poiInfo.location;
                        String addrStr = poiInfo.getName();
                        //构建文字Option对象，用于在地图上添加文字
                        OverlayOptions textOption = new TextOptions()
                                .bgColor(Color.WHITE)
                                .fontSize(30)
                                .fontColor(Color.BLACK)
                                .text(addrStr)
//                    .rotate(-30)
                                .position(llText);
//在地图上添加该文字对象并显示

                        mBaidumap.addOverlay(textOption);
                    }
                }
                //获取POI检索结果

            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }

            @Override
            public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        };


        //设置poi监听者该方法要先于检索方法searchNearby(PoiNearbySearchOption)前调用，否则会在某些场景出现拿不到回调结果的情况
        poiSearch.setOnGetPoiSearchResultListener(poiListener);
        //设置请求参数
        PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption()
                .keyword("停车场")//检索关键字
                .location(new LatLng(30.22730, 120.12979))//检索位置
                .radius(1000);//附近检索半径
        //发起请求
        poiSearch.searchNearby(nearbySearchOption);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    public void flush() {
        if(count == 0){
            finish();
            Log.e(String.valueOf(getClass()), "flush: flushed" );
            count++;
            Intent intent = new Intent(this, Jingqu_around_park.class);
            startActivity(intent);
        }
    }
}