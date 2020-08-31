package com.example.appofzhejiang.fragment1.culturevideo;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.appofzhejiang.R;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.concurrent.TimeUnit;

public class CultureVideoActivity extends AppCompatActivity {

    StandardGSYVideoPlayer videoPlayer;

    OrientationUtils orientationUtils;
    private String type;
    private String path;
    private String title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_culture_video);
        // 设置视频路径和标题
        setSource();
        // 隐藏系统标题
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        init();
    }
    private void setSource() {
        Intent intent = getIntent();
        this.type = intent.getStringExtra("type");
        if(type == null || "".equals(type.trim())) {
            path = "http://120.26.172.104:8080/video/video1.flv";
            title = "人文";
        } else if("1".equals(type)) {
            path = "http://120.26.172.104:8080/video/video1.flv";
            title = "人文";
        } else if("2".equals(type)) {
            path = "http://120.26.172.104:8080/video/video2.flv";
            title = "山水";
        }else if("3".equals(type)) {
            path = "http://120.26.172.104:8080/video/video3.flv";
            title = "古镇";
        }else if("4".equals(type)) {
            path = "http://120.26.172.104:8080/video/video4.flv";
            title = "味道";
        }
    }

    private void init() {

        videoPlayer =  (StandardGSYVideoPlayer)findViewById(R.id.video_player);

        String source1 = this.path;
        videoPlayer.setUp(source1, true,this.title);

        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.mipmap.xxx1);
        videoPlayer.setNeedAutoAdaptation(true); //  适配刘海屏
        videoPlayer.setNeedLockFull(true); // 设置全屏锁
        videoPlayer.setThumbImageView(imageView);
        //增加title
        videoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        //设置返回键
        videoPlayer.getBackButton().setVisibility(View.VISIBLE);
        //设置旋转
        orientationUtils = new OrientationUtils(this, videoPlayer);
        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orientationUtils.resolveByClick();
            }
        });
        //是否可以滑动调整
        videoPlayer.setIsTouchWiget(true);
        //设置返回按键功能
        videoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        videoPlayer.startPlayLogic();
    }


    @Override
    protected void onPause() {
        super.onPause();
        videoPlayer.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoPlayer.onVideoResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    @Override
    public void onBackPressed() {
        //先返回正常状态
        if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            videoPlayer.getFullscreenButton().performClick();
            return;
        }
        //释放所有
        videoPlayer.setVideoAllCallBack(null);
        super.onBackPressed();
    }
}