package com.example.appofzhejiang.xihu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

    private MapView mMapView;
    private BaiduMap mBaidumap;
    private Toolbar mBack;
    private PoiSearch poiSearch;
    private Button button;
    private EditText editText;
    private OnGetPoiSearchResultListener poiListener;
    String[] addresses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.bottom_in, R.anim.bottom_silent);
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

        Intent intent = getIntent();
        String address = intent.getStringExtra("address");
        addresses = address.split(",");

        editText = findViewById(R.id.edit1);
        button = findViewById(R.id.search);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().equals("")){
                    mBaidumap.clear();
                }else{
                    mBaidumap.clear();
                    nearbyPoiSearch(mBaidumap);
                }
            }
        });



        LatLng cenpt = new LatLng(Double.parseDouble(addresses[1]), Double.parseDouble(addresses[0])); //设定中心点坐标
        MapStatus mMapStatus = new MapStatus.Builder()//定义地图状态
                .target(cenpt)
                .zoom(15)
                .build();  //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
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
                    Toast.makeText(getApplicationContext(),"该搜索无结果", Toast.LENGTH_LONG).show();
                    return;
                }
                if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                    List<PoiInfo> point = result.getAllPoi();
//构建Marker图标
                    for (PoiInfo poiInfo : point) {
                        LatLng poiLocation = poiInfo.location;
//构建Marker图标
                        BitmapDescriptor bitmap = BitmapDescriptorFactory
                                .fromResource(R.drawable.dingdian);
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
                .keyword(String.valueOf(editText.getText()))//检索关键字
                .location(new LatLng(Double.parseDouble(addresses[1]), Double.parseDouble(addresses[0])))//检索位置
                .radius(1000);//附近检索半径
        //发起请求
        poiSearch.searchNearby(nearbySearchOption);
    }

    //点击空白处收起键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }


    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
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

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.bottom_silent, R.anim.bottom_out);
    }

}