package com.example.appofzhejiang.xihu;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appofzhejiang.R;
<<<<<<< HEAD
import com.example.appofzhejiang.fragment1.util.LocalImageLoader;
=======
import com.example.appofzhejiang.StatusBarUtil.StatusBarUtil;
>>>>>>> 3c9bbdde65df78e0ff718c43d76954956b5fb80e
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;


import java.util.ArrayList;

import javax.xml.transform.Transformer;

public class Jingqu extends AppCompatActivity {


    private Banner mBanner;//初始化Banner控件的使用
    private LocalImageLoader mLocalImageLoader;
    private ArrayList<String> imagePath;
    private ArrayList<String> imageTitle;

    private Dialog mShareDialog;
    private Dialog mShareDialog2;
    private TextView txt_open,txt_menpiao,txt_around,daohang;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jingqu);
        //设置沉浸式
        StatusBarUtil.setTransparent(this);
        StatusBarUtil.setDarkFont(this);

        initDate();
        intView();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //跳转导航
        daohang = findViewById(R.id.list2_image);
        daohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Jingqu.this,Jingqu_daohang.class);
                startActivity(intent);
            }
        });

        //底部弹窗
        txt_open = findViewById(R.id.txt_openTime);
        txt_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();// 单击按钮后 调用显示视图的 showDialog 方法
            }
        });

        txt_menpiao = findViewById(R.id.txt_menpiao);
        txt_menpiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();// 单击按钮后 调用显示视图的 showDialog 方法
            }
        });

        txt_around = findViewById(R.id.txt_around);
        txt_around.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog2();// 单击按钮后 调用显示视图的 showDialog 方法
            }
        });

        //景区攻略的recelyeView
        RecycleViewAdepter recycleViewAdepter = new RecycleViewAdepter(this);
        RecyclerView recyclerView = findViewById(R.id.jq_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this );
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        //创建并设置Adapter
        recyclerView.setAdapter(recycleViewAdepter);

    }

    private void intView() {
        mLocalImageLoader =new LocalImageLoader();
        mBanner = findViewById(R.id.banner);

        //样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //加载器
        mBanner.setImageLoader(mLocalImageLoader);

        //动画效果
//        mBanner.setBannerAnimation(Transformer.ZoomOutSlide);
        //图片标题
        mBanner.setBannerTitles(imageTitle);
        //间隔时间
        mBanner.setDelayTime(4500);
        //是否为自动播放
        mBanner.isAutoPlay(true);
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
        imageTitle = new ArrayList<String>();
        imagePath.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1597253495982&di=f60564ef9ba2ed5db3831dbc7d175370&imgtype=0&src=http%3A%2F%2Fi1.sinaimg.cn%2Ftravel%2Ful%2F2009%2F0812%2FU4013P704DT20090812095257.jpg");
        imagePath.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1597253495981&di=64647d732df041cb391d1c1901f761b2&imgtype=0&src=http%3A%2F%2Fdingyue.nosdn.127.net%2FfYexHc7uOi%3DKzjIJBavj096salFnas9oTNMqVmybuXSja1499388911175.jpg");
        imagePath.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1597253495981&di=1f76308c3555f9519c153973501b83e9&imgtype=0&src=http%3A%2F%2Fpic.rmb.bdstatic.com%2Ff5676f23020b561cbf5210413aaeead5.jpeg");
        imageTitle.add("西湖1");
        imageTitle.add("西湖2");
        imageTitle.add("西湖3");
    }

    private void showDialog2(){
        if (mShareDialog2 == null) {
            initShareDialog2();
        }
        mShareDialog2.show();
    }

    private void initShareDialog2() {
        mShareDialog2 = new Dialog(this, R.style.dialog_bottom_full);
        mShareDialog2.setCanceledOnTouchOutside(true); //手指触碰到外界取消
        mShareDialog2.setCancelable(true);             //可取消 为true
        Window window = mShareDialog2.getWindow();      // 得到dialog的窗体
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.share_animation);

        View view = View.inflate(this, R.layout.lay_share2, null); //获取布局视图
        view.findViewById(R.id.know).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mShareDialog2 != null && mShareDialog2.isShowing()) {
                    mShareDialog2.dismiss();
                }
            }
        });
        view.findViewById(R.id.txt_park).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Jingqu.this,Jingqu_around_park.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.txt_wc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        view.findViewById(R.id.txt_restrant).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
    }

    private void showDialog() {
        if (mShareDialog == null) {
            initShareDialog();
        }
        mShareDialog.show();
    }
    /**
     * 初始化分享弹出框
     */
    private void initShareDialog() {
        mShareDialog = new Dialog(this, R.style.dialog_bottom_full);
        mShareDialog.setCanceledOnTouchOutside(true); //手指触碰到外界取消
        mShareDialog.setCancelable(true);             //可取消 为true
        Window window = mShareDialog.getWindow();      // 得到dialog的窗体
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.share_animation);

        View view = View.inflate(this, R.layout.lay_share, null); //获取布局视图
        view.findViewById(R.id.know).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mShareDialog != null && mShareDialog.isShowing()) {
                    mShareDialog.dismiss();
                }
            }
        });
        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
    }
}