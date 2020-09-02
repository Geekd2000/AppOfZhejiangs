package com.example.appofzhejiang.xihu;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.appofzhejiang.R;

import com.example.appofzhejiang.fragment1.util.LocalImageLoader;

import com.example.appofzhejiang.StatusBarUtil.StatusBarUtil;

import com.example.appofzhejiang.fragment3.TicketDetail.TicketDetailActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;


import java.util.ArrayList;

public class Jingqu extends AppCompatActivity {



    private Banner mBanner;//初始化Banner控件的使用
    private LocalImageLoader mLocalImageLoader;
    private ArrayList<String> imagePath;
    private JinquBean2 jinquBean2;
    private JinquBean jinquBean;
    private Dialog mShareDialog;
    private TextView txt_open,txt_menpiao,txt_around,daohang,detail_content1,detail_content2,txt_title;
    private TextView time,price,price2,txt_label;
    private ImageView imageView1,imageView2;
    private Toolbar toolbar;
    private Button buy ,buy2;
    private String id;
    private String[] urlArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.right_in, R.anim.right_silent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jingqu);
        //设置沉浸式
        StatusBarUtil.setTransparent(this);
        StatusBarUtil.setDarkFont(this);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent=getIntent();
        id = intent.getStringExtra("product_id");
        String id2 = intent.getStringExtra("place_id");

        jinquBean2 = new JinquBeanUtil2(id2).getDetailBeanList();
        final String address = jinquBean2.getAddress();

        jinquBean=new JinquBeanUtil(id).getDetailBeanList();
        //图片URL获取
        String url = jinquBean.getPictures();
        urlArray = url.split(",");
        //商品介绍
        String introduce = jinquBean.getIntroduct();
        String[] introduceArray = introduce.split("<");
        imageView1 = findViewById(R.id.detail_image1);
        imageView2 = findViewById(R.id.detail_image2);
        Glide.with(this).load(urlArray[2]).into(imageView1);
        Glide.with(this).load(urlArray[3]).into(imageView2);
        imagePath = new ArrayList<String>();
        imagePath.add(urlArray[0]);
        imagePath.add(urlArray[1]);
        imagePath.add(urlArray[2]);
        intView();

        detail_content1 = findViewById(R.id.detail_content1);
        detail_content2 = findViewById(R.id.detail_content2);
        detail_content1.setText(introduceArray[0]);
        detail_content2.setText(introduceArray[1]);

        txt_label = findViewById(R.id.txt_label);
        txt_label.setText(jinquBean2.getLevel()+"景区");

        txt_title = findViewById(R.id.txt_title);
        txt_title.setText(jinquBean.getName());

        //跳转导航
        daohang = findViewById(R.id.list2_image);
        daohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Jingqu.this,Jingqu_daohang.class);
                intent.putExtra("address",address);
                intent.putExtra("name",jinquBean.getName());
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

        //周边
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
                Intent intent = new Intent(Jingqu.this,Jingqu_around_park.class);
                intent.putExtra("address",address);
                startActivity(intent);
            }
        });


    }

    private void intView() {
        mLocalImageLoader =new LocalImageLoader();
        mBanner = findViewById(R.id.banner);

        //样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //加载器
        mBanner.setImageLoader(mLocalImageLoader);

        //动画效果
//        mBanner.setBannerAnimation(Transformer.ZoomOutSlide);
        //图片标题
//        mBanner.setBannerTitles(imageTitle);
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
        time = view.findViewById(R.id.time);
        time.setText(jinquBean2.getTime());
        String[] params = jinquBean.getParams().split(",");
        String[] prices = jinquBean.getPrices().split(",");
        price = view.findViewById(R.id.price);
        price.setText(params[0].trim()+":"+prices[0].trim());
        price2 = view.findViewById(R.id.price2);
        price2.setText(params[1].trim()+":"+prices[1].trim());
        buy = view.findViewById(R.id.buy);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TicketDetailActivity.class);
                intent.putExtra("product_id",id);
                intent.putExtra("index", "7");
                intent.putExtra("company", "全城旅游");
                intent.putExtra("image", urlArray[0]);
                startActivity(intent);
            }
        });
        buy2 = view.findViewById(R.id.buy2);
        buy2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TicketDetailActivity.class);
                intent.putExtra("product_id",id);
                intent.putExtra("index", "7");
                intent.putExtra("company", "全城旅游");
                intent.putExtra("image", urlArray[0]);
                startActivity(intent);
            }
        });
        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.right_silent, R.anim.right_out);
    }
}