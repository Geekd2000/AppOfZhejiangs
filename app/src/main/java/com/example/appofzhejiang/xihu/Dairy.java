package com.example.appofzhejiang.xihu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.appofzhejiang.R;
import com.example.appofzhejiang.StatusBarUtil.StatusBarUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;

public class Dairy extends AppCompatActivity {

    private Banner mBanner;//初始化Banner控件的使用
    private LocalImageLoader mLocalImageLoader;
    private ArrayList<String> imagePath;
    private ArrayList<String> imageTitle;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dairy);
        //设置沉浸式
        StatusBarUtil.setTransparent(this);
        StatusBarUtil.setDarkFont(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        initDate();
        initView();

        textView = findViewById(R.id.txt_xihu);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dairy.this, Jingqu.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        mLocalImageLoader =new LocalImageLoader();
        mBanner = findViewById(R.id.banner);

        //样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //加载器
        mBanner.setImageLoader(mLocalImageLoader);
        //动画效果
        mBanner.setBannerAnimation(Transformer.ZoomOutSlide);
        //图片标题
//        mBanner.setBannerTitles(imageTitle);
        //间隔时间
//        mBanner.setDelayTime(4500);
        //是否为自动播放
//        mBanner.isAutoPlay(true);
        //图片加载地址
        mBanner.setImages(imagePath);
        //启动
        mBanner.start();
//        mBanner.setOnBannerListener(new OnBannerListener() {
//            @Override
//            public void OnBannerClick(int position) {
//                具体事件
//            }
//        });
    }

    private void initDate() {
        imagePath = new ArrayList<String>();
        imagePath.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1597253495982&di=f60564ef9ba2ed5db3831dbc7d175370&imgtype=0&src=http%3A%2F%2Fi1.sinaimg.cn%2Ftravel%2Ful%2F2009%2F0812%2FU4013P704DT20090812095257.jpg");
    }
}