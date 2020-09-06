package com.example.appofzhejiang.fragment3.TicketDetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appofzhejiang.Business.AddressActivity;
import com.example.appofzhejiang.Business.AddressBean;
import com.example.appofzhejiang.Business.GetDefaultAddressUtil;
import com.example.appofzhejiang.Login.LoginActivity;
import com.example.appofzhejiang.Login.LoginUtil;
import com.example.appofzhejiang.R;
import com.example.appofzhejiang.StatusBarUtil.StatusBarUtil;
import com.example.appofzhejiang.fragment3.GlideImageLoader;
import com.example.appofzhejiang.fragment3.SubmitOrderActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TicketDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView image1, image2, goodsImage;
    private TextView detailTitle, detailPrice, detailCompany, detailSales, detailGoods1, detailGoods2, detailPrice1, detailPrice2,
            detailBuy1, detailBuy2, detailContent1, detailContent2;
    private Banner banner;
    private List images;
    private Boolean isLoginStatus;//登录状态
    private DetailBean detailBean;
    private int userID;//用户ID
    private String loginUserName;//用户名

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.right_in, R.anim.right_silent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);
        toolbar = findViewById(R.id.toolbar_goods);
        //标题栏返回按钮点击事件，点击后返回上一页
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //mainImage = findViewById(R.id.image_ticketDetail);
        image1 = findViewById(R.id.detail_image1);
        image2 = findViewById(R.id.detail_image2);
        detailTitle = findViewById(R.id.detail_title);
        detailPrice = findViewById(R.id.detail_price);
        detailCompany = findViewById(R.id.detail_company);
        detailSales = findViewById(R.id.detail_sales);
        detailGoods1 = findViewById(R.id.detail_goods1);
        detailGoods2 = findViewById(R.id.detail_goods2);
        detailPrice1 = findViewById(R.id.detail_price1);
        detailPrice2 = findViewById(R.id.detail_price2);
        detailBuy1 = findViewById(R.id.detail_buy1);
        detailBuy2 = findViewById(R.id.detail_buy2);
        detailContent1 = findViewById(R.id.detail_content1);
        detailContent2 = findViewById(R.id.detail_content2);
        goodsImage = findViewById(R.id.goods_image);
        banner = findViewById(R.id.banner);

        //设置沉浸式
        StatusBarUtil.setTransparent(this);
        StatusBarUtil.setDarkFont(this);

        //取得从上一个Activity当中传递过来的Intent对象
        Intent intent = getIntent();
        //从Intent当中根据key取得value
        String value = intent.getStringExtra("index");
        int index = Integer.parseInt(value);
        final String product_id = intent.getStringExtra("product_id");//商品ID
        String company = intent.getStringExtra("company");//公司名称
        String image = intent.getStringExtra("image");//商品封面

        switch (index) {
            case 0:
                detailBean = new DetailBeanUtil(product_id).getDetailBeanList();
                //图片URL获取
                String url = detailBean.getPictures();
                String[] urlArray = url.split(",");
                //处理价格
                String price = detailBean.getPrices();
                String[] priceArray = price.split(",");
                //商品规格
                String goodsName = detailBean.getParams();
                String[] goodsNameArray = goodsName.split(",");
                //商品介绍
                String introduce = detailBean.getIntroduct();
                String[] introduceArray = introduce.split("<");
                //轮播图
                images = new ArrayList<>();
                images.add(urlArray[0]);
                images.add(urlArray[1]);
                images.add(urlArray[2]);
                banner.setImageLoader(new GlideImageLoader());   //设置图片加载器
                banner.setImages(images);//设置图片源
                banner.setDelayTime(3000);//设置轮播事件，单位毫秒
                banner.setBannerAnimation(Transformer.Default);//设置轮播动画，动画种类很多，有兴趣的去试试吧，我在这里用的是默认
                banner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器的位置
                banner.start();//开始轮播，一定要调用此方法。
                Glide.with(this).load(image).into(goodsImage);
                detailTitle.setText(detailBean.getName());
                if (Integer.parseInt(priceArray[0]) < Integer.parseInt(priceArray[1])) {
                    detailPrice.setText(priceArray[0]);
                } else {
                    detailPrice.setText(priceArray[1]);
                }
                detailCompany.setText(company);
                detailSales.setText(detailBean.getSales());
                detailGoods1.setText(goodsNameArray[0]);
                detailPrice1.setText(priceArray[0]);
                detailGoods2.setText(goodsNameArray[1]);
                detailPrice2.setText(priceArray[1]);
                Glide.with(this).load(urlArray[3]).into(image1);
                Glide.with(this).load(urlArray[2]).into(image2);
                detailContent1.setText(introduceArray[0]);
                detailContent2.setText(introduceArray[1]);
                final String finalImage0 = image;
                detailBuy1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //登录状态
                        SharedPreferences sp = TicketDetailActivity.this.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                        isLoginStatus = sp.getBoolean("isLogin", false);
                        if (isLoginStatus.equals(true)) {
                            //用户名
                            loginUserName = sp.getString("loginUserName", null);
                            userID = new LoginUtil(loginUserName).getLoginRegisterBean().getUser_id();
                            if (new GetDefaultAddressUtil(String.valueOf(userID)).getAddressBean() == null) {
                                Toast.makeText(TicketDetailActivity.this, "请先添加默认地址", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(TicketDetailActivity.this, AddressActivity.class));
                            } else {
                                Intent intentBuy = new Intent(TicketDetailActivity.this, SubmitOrderActivity.class);
                                intentBuy.putExtra("goodsName", detailTitle.getText());
                                intentBuy.putExtra("goodsPrice", detailPrice1.getText());
                                intentBuy.putExtra("goodsType", detailGoods1.getText());
                                intentBuy.putExtra("goodsImage", finalImage0);
                                intentBuy.putExtra("inventory", Integer.toString(detailBean.getInventory()));
                                intentBuy.putExtra("product_id", product_id);
                                intentBuy.putExtra("type", String.valueOf(detailBean.getType()));
                                startActivity(intentBuy);
                                TicketDetailActivity.this.finish();
                            }
                        } else {
                            Toast.makeText(TicketDetailActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(TicketDetailActivity.this, LoginActivity.class);
                            intent1.putExtra("n", 1);
                            startActivity(intent1);
                        }
                    }
                });
                detailBuy2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //登录状态
                        SharedPreferences sp = TicketDetailActivity.this.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                        isLoginStatus = sp.getBoolean("isLogin", false);
                        if (isLoginStatus.equals(true)) {
                            //用户名
                            loginUserName = sp.getString("loginUserName", null);
                            userID = new LoginUtil(loginUserName).getLoginRegisterBean().getUser_id();
                            if (new GetDefaultAddressUtil(String.valueOf(userID)).getAddressBean() == null) {
                                Toast.makeText(TicketDetailActivity.this, "请先添加默认地址", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(TicketDetailActivity.this, AddressActivity.class));
                            } else {
                                Intent intentBuy = new Intent(TicketDetailActivity.this, SubmitOrderActivity.class);
                                intentBuy.putExtra("goodsName", detailTitle.getText());
                                intentBuy.putExtra("goodsPrice", detailPrice2.getText());
                                intentBuy.putExtra("goodsType", detailGoods2.getText());
                                intentBuy.putExtra("goodsImage", finalImage0);
                                intentBuy.putExtra("inventory", Integer.toString(detailBean.getInventory()));
                                intentBuy.putExtra("product_id", product_id);
                                intentBuy.putExtra("type", String.valueOf(detailBean.getType()));
                                startActivity(intentBuy);
                                TicketDetailActivity.this.finish();
                            }
                        } else {
                            Toast.makeText(TicketDetailActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(TicketDetailActivity.this, LoginActivity.class);
                            intent1.putExtra("n", 1);
                            startActivity(intent1);
                        }
                    }
                });
                break;
            case 1:
                detailBean = new DetailBeanUtil(product_id).getDetailBeanList();
                //图片URL获取
                url = detailBean.getPictures();
                urlArray = url.split(",");
                //处理价格
                price = detailBean.getPrices();
                priceArray = price.split(",");
                //商品规格
                goodsName = detailBean.getParams();
                goodsNameArray = goodsName.split(",");
                //轮播图
                images = new ArrayList<>();
                images.add(urlArray[0]);
                images.add(urlArray[1]);
                banner.setImageLoader(new GlideImageLoader());   //设置图片加载器
                banner.setImages(images);//设置图片源
                banner.setDelayTime(3000);//设置轮播事件，单位毫秒
                banner.setBannerAnimation(Transformer.Default);//设置轮播动画，动画种类很多，有兴趣的去试试吧，我在这里用的是默认
                banner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器的位置
                banner.start();//开始轮播，一定要调用此方法。
                Glide.with(this).load(image).into(goodsImage);
                detailTitle.setText(detailBean.getName());
                if (Integer.parseInt(priceArray[0]) < Integer.parseInt(priceArray[1])) {
                    detailPrice.setText(priceArray[0]);
                } else {
                    detailPrice.setText(priceArray[1]);
                }
                detailCompany.setText(company);
                detailSales.setText(detailBean.getSales());
                detailGoods1.setText(goodsNameArray[0]);
                detailPrice1.setText(priceArray[0]);
                detailGoods2.setText(goodsNameArray[1]);
                detailPrice2.setText(priceArray[1]);
                Glide.with(this).load(urlArray[0]).into(image1);
                Glide.with(this).load(urlArray[1]).into(image2);
                detailContent1.setText(R.string.hotel1);
                detailContent2.setText(R.string.hotel2);
                final String finalImage1 = image;
                detailBuy1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //登录状态
                        SharedPreferences sp = TicketDetailActivity.this.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                        isLoginStatus = sp.getBoolean("isLogin", false);
                        if (isLoginStatus.equals(true)) {
                            //用户名
                            loginUserName = sp.getString("loginUserName", null);
                            userID = new LoginUtil(loginUserName).getLoginRegisterBean().getUser_id();
                            if (new GetDefaultAddressUtil(String.valueOf(userID)).getAddressBean() == null) {
                                Toast.makeText(TicketDetailActivity.this, "请先添加默认地址", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(TicketDetailActivity.this, AddressActivity.class));
                            } else {
                                Intent intentBuy = new Intent(TicketDetailActivity.this, SubmitOrderActivity.class);
                                intentBuy.putExtra("goodsName", detailTitle.getText());
                                intentBuy.putExtra("goodsPrice", detailPrice1.getText());
                                intentBuy.putExtra("goodsType", detailGoods1.getText());
                                intentBuy.putExtra("goodsImage", finalImage1);
                                intentBuy.putExtra("inventory", Integer.toString(detailBean.getInventory()));
                                intentBuy.putExtra("product_id", product_id);
                                intentBuy.putExtra("type", String.valueOf(detailBean.getType()));
                                startActivity(intentBuy);
                                TicketDetailActivity.this.finish();
                            }
                        } else {
                            Toast.makeText(TicketDetailActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(TicketDetailActivity.this, LoginActivity.class);
                            intent1.putExtra("n", 1);
                            startActivity(intent1);
                        }
                    }
                });
                detailBuy2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //登录状态
                        SharedPreferences sp = TicketDetailActivity.this.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                        isLoginStatus = sp.getBoolean("isLogin", false);
                        if (isLoginStatus.equals(true)) {
                            //用户名
                            loginUserName = sp.getString("loginUserName", null);
                            userID = new LoginUtil(loginUserName).getLoginRegisterBean().getUser_id();
                            if (new GetDefaultAddressUtil(String.valueOf(userID)).getAddressBean() == null) {
                                Toast.makeText(TicketDetailActivity.this, "请先添加默认地址", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(TicketDetailActivity.this, AddressActivity.class));
                            } else {
                                Intent intentBuy = new Intent(TicketDetailActivity.this, SubmitOrderActivity.class);
                                intentBuy.putExtra("goodsName", detailTitle.getText());
                                intentBuy.putExtra("goodsPrice", detailPrice2.getText());
                                intentBuy.putExtra("goodsType", detailGoods2.getText());
                                intentBuy.putExtra("goodsImage", finalImage1);
                                intentBuy.putExtra("inventory", Integer.toString(detailBean.getInventory()));
                                intentBuy.putExtra("product_id", product_id);
                                intentBuy.putExtra("type", String.valueOf(detailBean.getType()));
                                startActivity(intentBuy);
                                TicketDetailActivity.this.finish();
                            }
                        } else {
                            Toast.makeText(TicketDetailActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(TicketDetailActivity.this, LoginActivity.class);
                            intent1.putExtra("n", 1);
                            startActivity(intent1);
                        }
                    }
                });
                break;
            case 2:
                detailBean = new DetailBeanUtil(product_id).getDetailBeanList();
                //图片URL获取
                url = detailBean.getPictures();
                urlArray = url.split(",");
                //处理价格
                price = detailBean.getPrices();
                priceArray = price.split(",");
                //商品规格
                goodsName = detailBean.getParams();
                goodsNameArray = goodsName.split(",");
                //轮播图
                images = new ArrayList<>();
                images.add(urlArray[0]);
                images.add(urlArray[1]);
                banner.setImageLoader(new GlideImageLoader());   //设置图片加载器
                banner.setImages(images);//设置图片源
                banner.setDelayTime(3000);//设置轮播事件，单位毫秒
                banner.setBannerAnimation(Transformer.Default);//设置轮播动画，动画种类很多，有兴趣的去试试吧，我在这里用的是默认
                banner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器的位置
                banner.start();//开始轮播，一定要调用此方法。
                Glide.with(this).load(image).into(goodsImage);
                detailTitle.setText(detailBean.getName());
                if (Integer.parseInt(priceArray[0]) < Integer.parseInt(priceArray[1])) {
                    detailPrice.setText(priceArray[0]);
                } else {
                    detailPrice.setText(priceArray[1]);
                }
                detailCompany.setText(company);
                detailSales.setText(detailBean.getSales());
                detailGoods1.setText(goodsNameArray[0]);
                detailPrice1.setText(priceArray[0]);
                detailGoods2.setText(goodsNameArray[1]);
                detailPrice2.setText(priceArray[1]);
                Glide.with(this).load(urlArray[0]).into(image1);
                Glide.with(this).load(urlArray[1]).into(image2);
                detailContent1.setText(R.string.taxi1);
                detailContent2.setText(R.string.taxi2);
                final String finalImage2 = image;
                detailBuy1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //登录状态
                        SharedPreferences sp = TicketDetailActivity.this.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                        isLoginStatus = sp.getBoolean("isLogin", false);
                        if (isLoginStatus.equals(true)) {
                            //用户名
                            loginUserName = sp.getString("loginUserName", null);
                            userID = new LoginUtil(loginUserName).getLoginRegisterBean().getUser_id();
                            if (new GetDefaultAddressUtil(String.valueOf(userID)).getAddressBean() == null) {
                                Toast.makeText(TicketDetailActivity.this, "请先添加默认地址", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(TicketDetailActivity.this, AddressActivity.class));
                            } else {
                                Intent intentBuy = new Intent(TicketDetailActivity.this, SubmitOrderActivity.class);
                                intentBuy.putExtra("goodsName", detailTitle.getText());
                                intentBuy.putExtra("goodsPrice", detailPrice1.getText());
                                intentBuy.putExtra("goodsType", detailGoods1.getText());
                                intentBuy.putExtra("goodsImage", finalImage2);
                                intentBuy.putExtra("inventory", Integer.toString(detailBean.getInventory()));
                                intentBuy.putExtra("product_id", product_id);
                                intentBuy.putExtra("type", String.valueOf(detailBean.getType()));
                                startActivity(intentBuy);
                                TicketDetailActivity.this.finish();
                            }
                        } else {
                            Toast.makeText(TicketDetailActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(TicketDetailActivity.this, LoginActivity.class);
                            intent1.putExtra("n", 1);
                            startActivity(intent1);
                        }
                    }
                });
                detailBuy2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //登录状态
                        SharedPreferences sp = TicketDetailActivity.this.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                        isLoginStatus = sp.getBoolean("isLogin", false);
                        if (isLoginStatus.equals(true)) {
                            //用户名
                            loginUserName = sp.getString("loginUserName", null);
                            userID = new LoginUtil(loginUserName).getLoginRegisterBean().getUser_id();
                            if (new GetDefaultAddressUtil(String.valueOf(userID)).getAddressBean() == null) {
                                Toast.makeText(TicketDetailActivity.this, "请先添加默认地址", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(TicketDetailActivity.this, AddressActivity.class));
                            } else {
                                Intent intentBuy = new Intent(TicketDetailActivity.this, SubmitOrderActivity.class);
                                intentBuy.putExtra("goodsName", detailTitle.getText());
                                intentBuy.putExtra("goodsPrice", detailPrice2.getText());
                                intentBuy.putExtra("goodsType", detailGoods2.getText());
                                intentBuy.putExtra("goodsImage", finalImage2);
                                intentBuy.putExtra("inventory", Integer.toString(detailBean.getInventory()));
                                intentBuy.putExtra("product_id", product_id);
                                intentBuy.putExtra("type", String.valueOf(detailBean.getType()));
                                startActivity(intentBuy);
                                TicketDetailActivity.this.finish();
                            }
                        } else {
                            Toast.makeText(TicketDetailActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(TicketDetailActivity.this, LoginActivity.class);
                            intent1.putExtra("n", 1);
                            startActivity(intent1);
                        }
                    }
                });
                break;
            case 3:
                detailBean = new DetailBeanUtil(product_id).getDetailBeanList();
                //图片URL获取
                url = detailBean.getPictures();
                urlArray = url.split(",");
                //处理价格
                price = detailBean.getPrices();
                priceArray = price.split(",");
                //商品规格
                goodsName = detailBean.getParams();
                goodsNameArray = goodsName.split(",");
                //轮播图
                images = new ArrayList<>();
                images.add(urlArray[0]);
                banner.setImageLoader(new GlideImageLoader());   //设置图片加载器
                banner.setImages(images);//设置图片源
                banner.setDelayTime(3000);//设置轮播事件，单位毫秒
                banner.setBannerAnimation(Transformer.Default);//设置轮播动画，动画种类很多，有兴趣的去试试吧，我在这里用的是默认
                banner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器的位置
                banner.start();//开始轮播，一定要调用此方法。
                Glide.with(this).load(image).into(goodsImage);
                detailTitle.setText(detailBean.getName());
                if (Integer.parseInt(priceArray[0]) < Integer.parseInt(priceArray[1])) {
                    detailPrice.setText(priceArray[0]);
                } else {
                    detailPrice.setText(priceArray[1]);
                }
                detailCompany.setText(company);
                detailSales.setText(detailBean.getSales());
                detailGoods1.setText(goodsNameArray[0]);
                detailPrice1.setText(priceArray[0]);
                detailGoods2.setText(goodsNameArray[1]);
                detailPrice2.setText(priceArray[1]);
                Glide.with(this).load(urlArray[0]).into(image1);
                Glide.with(this).load(urlArray[0]).into(image2);
                detailContent1.setText(R.string.guider1);
                detailContent2.setText(R.string.guider2);
                final String finalImage3 = image;
                detailBuy1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //登录状态
                        SharedPreferences sp = TicketDetailActivity.this.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                        isLoginStatus = sp.getBoolean("isLogin", false);
                        if (isLoginStatus.equals(true)) {
                            //用户名
                            loginUserName = sp.getString("loginUserName", null);
                            userID = new LoginUtil(loginUserName).getLoginRegisterBean().getUser_id();
                            if (new GetDefaultAddressUtil(String.valueOf(userID)).getAddressBean() == null) {
                                Toast.makeText(TicketDetailActivity.this, "请先添加默认地址", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(TicketDetailActivity.this, AddressActivity.class));
                            } else {
                                Intent intentBuy = new Intent(TicketDetailActivity.this, SubmitOrderActivity.class);
                                intentBuy.putExtra("goodsName", detailTitle.getText());
                                intentBuy.putExtra("goodsPrice", detailPrice1.getText());
                                intentBuy.putExtra("goodsType", detailGoods1.getText());
                                intentBuy.putExtra("goodsImage", finalImage3);
                                intentBuy.putExtra("inventory", Integer.toString(detailBean.getInventory()));
                                intentBuy.putExtra("product_id", product_id);
                                intentBuy.putExtra("type", String.valueOf(detailBean.getType()));
                                startActivity(intentBuy);
                                TicketDetailActivity.this.finish();
                            }
                        } else {
                            Toast.makeText(TicketDetailActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(TicketDetailActivity.this, LoginActivity.class);
                            intent1.putExtra("n", 1);
                            startActivity(intent1);
                        }
                    }
                });
                detailBuy2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //登录状态
                        SharedPreferences sp = TicketDetailActivity.this.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                        isLoginStatus = sp.getBoolean("isLogin", false);
                        if (isLoginStatus.equals(true)) {
                            //用户名
                            loginUserName = sp.getString("loginUserName", null);
                            userID = new LoginUtil(loginUserName).getLoginRegisterBean().getUser_id();
                            if (new GetDefaultAddressUtil(String.valueOf(userID)).getAddressBean() == null) {
                                Toast.makeText(TicketDetailActivity.this, "请先添加默认地址", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(TicketDetailActivity.this, AddressActivity.class));
                            } else {
                                Intent intentBuy = new Intent(TicketDetailActivity.this, SubmitOrderActivity.class);
                                intentBuy.putExtra("goodsName", detailTitle.getText());
                                intentBuy.putExtra("goodsPrice", detailPrice2.getText());
                                intentBuy.putExtra("goodsType", detailGoods2.getText());
                                intentBuy.putExtra("goodsImage", finalImage3);
                                intentBuy.putExtra("inventory", Integer.toString(detailBean.getInventory()));
                                intentBuy.putExtra("product_id", product_id);
                                intentBuy.putExtra("type", String.valueOf(detailBean.getType()));
                                startActivity(intentBuy);
                                TicketDetailActivity.this.finish();
                            }
                        } else {
                            Toast.makeText(TicketDetailActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(TicketDetailActivity.this, LoginActivity.class);
                            intent1.putExtra("n", 1);
                            startActivity(intent1);
                        }
                    }
                });
                break;
            case 4:
                detailBean = new DetailBeanUtil(product_id).getDetailBeanList();
                //图片URL获取
                url = detailBean.getPictures();
                urlArray = url.split(",");
                //处理价格
                price = detailBean.getPrices();
                priceArray = price.split(",");
                //商品规格
                goodsName = detailBean.getParams();
                goodsNameArray = goodsName.split(",");
                //轮播图
                images = new ArrayList<>();
                images.add(urlArray[0]);
                images.add(urlArray[1]);
                banner.setImageLoader(new GlideImageLoader());   //设置图片加载器
                banner.setImages(images);//设置图片源
                banner.setDelayTime(3000);//设置轮播事件，单位毫秒
                banner.setBannerAnimation(Transformer.Default);//设置轮播动画，动画种类很多，有兴趣的去试试吧，我在这里用的是默认
                banner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器的位置
                banner.start();//开始轮播，一定要调用此方法。
                Glide.with(this).load(image).into(goodsImage);
                detailTitle.setText(detailBean.getName());
                if (Integer.parseInt(priceArray[0]) < Integer.parseInt(priceArray[1])) {
                    detailPrice.setText(priceArray[0]);
                } else {
                    detailPrice.setText(priceArray[1]);
                }
                detailCompany.setText(company);
                detailSales.setText(detailBean.getSales());
                detailGoods1.setText(goodsNameArray[0]);
                detailPrice1.setText(priceArray[0]);
                detailGoods2.setText(goodsNameArray[1]);
                detailPrice2.setText(priceArray[1]);
                Glide.with(this).load(urlArray[0]).into(image1);
                Glide.with(this).load(urlArray[1]).into(image2);
                detailContent1.setText(R.string.farmhouse1);
                detailContent2.setText(R.string.farmhouse2);
                final String finalImage4 = image;
                detailBuy1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //登录状态
                        SharedPreferences sp = TicketDetailActivity.this.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                        isLoginStatus = sp.getBoolean("isLogin", false);
                        if (isLoginStatus.equals(true)) {
                            //用户名
                            loginUserName = sp.getString("loginUserName", null);
                            userID = new LoginUtil(loginUserName).getLoginRegisterBean().getUser_id();
                            if (new GetDefaultAddressUtil(String.valueOf(userID)).getAddressBean() == null) {
                                Toast.makeText(TicketDetailActivity.this, "请先添加默认地址", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(TicketDetailActivity.this, AddressActivity.class));
                            } else {
                                Intent intentBuy = new Intent(TicketDetailActivity.this, SubmitOrderActivity.class);
                                intentBuy.putExtra("goodsName", detailTitle.getText());
                                intentBuy.putExtra("goodsPrice", detailPrice1.getText());
                                intentBuy.putExtra("goodsType", detailGoods1.getText());
                                intentBuy.putExtra("goodsImage", finalImage4);
                                intentBuy.putExtra("inventory", Integer.toString(detailBean.getInventory()));
                                intentBuy.putExtra("product_id", product_id);
                                intentBuy.putExtra("type", String.valueOf(detailBean.getType()));
                                startActivity(intentBuy);
                                TicketDetailActivity.this.finish();
                            }
                        } else {
                            Toast.makeText(TicketDetailActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(TicketDetailActivity.this, LoginActivity.class);
                            intent1.putExtra("n", 1);
                            startActivity(intent1);
                        }
                    }
                });
                detailBuy2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //登录状态
                        SharedPreferences sp = TicketDetailActivity.this.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                        isLoginStatus = sp.getBoolean("isLogin", false);
                        if (isLoginStatus.equals(true)) {
                            //用户名
                            loginUserName = sp.getString("loginUserName", null);
                            userID = new LoginUtil(loginUserName).getLoginRegisterBean().getUser_id();
                            if (new GetDefaultAddressUtil(String.valueOf(userID)).getAddressBean() == null) {
                                Toast.makeText(TicketDetailActivity.this, "请先添加默认地址", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(TicketDetailActivity.this, AddressActivity.class));
                            } else {
                                Intent intentBuy = new Intent(TicketDetailActivity.this, SubmitOrderActivity.class);
                                intentBuy.putExtra("goodsName", detailTitle.getText());
                                intentBuy.putExtra("goodsPrice", detailPrice2.getText());
                                intentBuy.putExtra("goodsType", detailGoods2.getText());
                                intentBuy.putExtra("goodsImage", finalImage4);
                                intentBuy.putExtra("inventory", Integer.toString(detailBean.getInventory()));
                                intentBuy.putExtra("product_id", product_id);
                                intentBuy.putExtra("type", String.valueOf(detailBean.getType()));
                                startActivity(intentBuy);
                                TicketDetailActivity.this.finish();
                            }
                        } else {
                            Toast.makeText(TicketDetailActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(TicketDetailActivity.this, LoginActivity.class);
                            intent1.putExtra("n", 1);
                            startActivity(intent1);
                        }
                    }
                });
                break;
            case 5:
                detailBean = new DetailBeanUtil(product_id).getDetailBeanList();
                //图片URL获取
                url = detailBean.getPictures();
                urlArray = url.split(",");
                //处理价格
                price = detailBean.getPrices();
                priceArray = price.split(",");
                //商品规格
                goodsName = detailBean.getParams();
                goodsNameArray = goodsName.split(",");
                //轮播图
                images = new ArrayList<>();
                images.add(urlArray[0]);
                images.add(urlArray[1]);
                banner.setImageLoader(new GlideImageLoader());   //设置图片加载器
                banner.setImages(images);//设置图片源
                banner.setDelayTime(3000);//设置轮播事件，单位毫秒
                banner.setBannerAnimation(Transformer.Default);//设置轮播动画，动画种类很多，有兴趣的去试试吧，我在这里用的是默认
                banner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器的位置
                banner.start();//开始轮播，一定要调用此方法。
                Glide.with(this).load(image).into(goodsImage);
                detailTitle.setText(detailBean.getName());
                if (Integer.parseInt(priceArray[0]) < Integer.parseInt(priceArray[1])) {
                    detailPrice.setText(priceArray[0]);
                } else {
                    detailPrice.setText(priceArray[1]);
                }
                detailCompany.setText(company);
                detailSales.setText(detailBean.getSales());
                detailGoods1.setText(goodsNameArray[0]);
                detailPrice1.setText(priceArray[0]);
                detailGoods2.setText(goodsNameArray[1]);
                detailPrice2.setText(priceArray[1]);
                Glide.with(this).load(urlArray[0]).into(image1);
                Glide.with(this).load(urlArray[1]).into(image2);
                detailContent1.setText(R.string.food1);
                detailContent2.setText(R.string.food2);
                final String finalImage5 = image;
                detailBuy1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //登录状态
                        SharedPreferences sp = TicketDetailActivity.this.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                        isLoginStatus = sp.getBoolean("isLogin", false);
                        if (isLoginStatus.equals(true)) {
                            //用户名
                            loginUserName = sp.getString("loginUserName", null);
                            userID = new LoginUtil(loginUserName).getLoginRegisterBean().getUser_id();
                            if (new GetDefaultAddressUtil(String.valueOf(userID)).getAddressBean() == null) {
                                Toast.makeText(TicketDetailActivity.this, "请先添加默认地址", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(TicketDetailActivity.this, AddressActivity.class));
                            } else {
                                Intent intentBuy = new Intent(TicketDetailActivity.this, SubmitOrderActivity.class);
                                intentBuy.putExtra("goodsName", detailTitle.getText());
                                intentBuy.putExtra("goodsPrice", detailPrice1.getText());
                                intentBuy.putExtra("goodsType", detailGoods1.getText());
                                intentBuy.putExtra("goodsImage", finalImage5);
                                intentBuy.putExtra("inventory", Integer.toString(detailBean.getInventory()));
                                intentBuy.putExtra("product_id", product_id);
                                intentBuy.putExtra("type", String.valueOf(detailBean.getType()));
                                startActivity(intentBuy);
                                TicketDetailActivity.this.finish();
                            }
                        } else {
                            Toast.makeText(TicketDetailActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(TicketDetailActivity.this, LoginActivity.class);
                            intent1.putExtra("n", 1);
                            startActivity(intent1);
                        }
                    }
                });
                detailBuy2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //登录状态
                        SharedPreferences sp = TicketDetailActivity.this.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                        isLoginStatus = sp.getBoolean("isLogin", false);
                        if (isLoginStatus.equals(true)) {
                            //用户名
                            loginUserName = sp.getString("loginUserName", null);
                            userID = new LoginUtil(loginUserName).getLoginRegisterBean().getUser_id();
                            if (new GetDefaultAddressUtil(String.valueOf(userID)).getAddressBean() == null) {
                                Toast.makeText(TicketDetailActivity.this, "请先添加默认地址", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(TicketDetailActivity.this, AddressActivity.class));
                            } else {
                                Intent intentBuy = new Intent(TicketDetailActivity.this, SubmitOrderActivity.class);
                                intentBuy.putExtra("goodsName", detailTitle.getText());
                                intentBuy.putExtra("goodsPrice", detailPrice2.getText());
                                intentBuy.putExtra("goodsType", detailGoods2.getText());
                                intentBuy.putExtra("goodsImage", finalImage5);
                                intentBuy.putExtra("inventory", Integer.toString(detailBean.getInventory()));
                                intentBuy.putExtra("product_id", product_id);
                                intentBuy.putExtra("type", String.valueOf(detailBean.getType()));
                                startActivity(intentBuy);
                                TicketDetailActivity.this.finish();
                            }
                        } else {
                            Toast.makeText(TicketDetailActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(TicketDetailActivity.this, LoginActivity.class);
                            intent1.putExtra("n", 1);
                            startActivity(intent1);
                        }
                    }
                });
                break;
            case 6:
                detailBean = new DetailBeanUtil(product_id).getDetailBeanList();
                //图片URL获取
                url = detailBean.getPictures();
                urlArray = url.split(",");
                //处理价格
                price = detailBean.getPrices();
                priceArray = price.split(",");
                //商品规格
                goodsName = detailBean.getParams();
                goodsNameArray = goodsName.split(",");
                //轮播图
                images = new ArrayList<>();
                images.add(urlArray[0]);
                images.add(urlArray[1]);
                banner.setImageLoader(new GlideImageLoader());   //设置图片加载器
                banner.setImages(images);//设置图片源
                banner.setDelayTime(3000);//设置轮播事件，单位毫秒
                banner.setBannerAnimation(Transformer.Default);//设置轮播动画，动画种类很多，有兴趣的去试试吧，我在这里用的是默认
                banner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器的位置
                banner.start();//开始轮播，一定要调用此方法。
                Glide.with(this).load(image).into(goodsImage);
                detailTitle.setText(detailBean.getName());
                if (Integer.parseInt(priceArray[0]) < Integer.parseInt(priceArray[1])) {
                    detailPrice.setText(priceArray[0]);
                } else {
                    detailPrice.setText(priceArray[1]);
                }
                detailCompany.setText(company);
                detailSales.setText(detailBean.getSales());
                detailGoods1.setText(goodsNameArray[0]);
                detailPrice1.setText(priceArray[0]);
                detailGoods2.setText(goodsNameArray[1]);
                detailPrice2.setText(priceArray[1]);
                Glide.with(this).load(urlArray[0]).into(image1);
                Glide.with(this).load(urlArray[1]).into(image2);
                detailContent1.setText(R.string.techan1);
                detailContent2.setText(R.string.techan2);
                final String finalImage6 = image;
                detailBuy1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //登录状态
                        SharedPreferences sp = TicketDetailActivity.this.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                        isLoginStatus = sp.getBoolean("isLogin", false);
                        if (isLoginStatus.equals(true)) {
                            //用户名
                            loginUserName = sp.getString("loginUserName", null);
                            userID = new LoginUtil(loginUserName).getLoginRegisterBean().getUser_id();
                            if (new GetDefaultAddressUtil(String.valueOf(userID)).getAddressBean() == null) {
                                Toast.makeText(TicketDetailActivity.this, "请先添加默认地址", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(TicketDetailActivity.this, AddressActivity.class));
                            } else {
                                Intent intentBuy = new Intent(TicketDetailActivity.this, SubmitOrderActivity.class);
                                intentBuy.putExtra("goodsName", detailTitle.getText());
                                intentBuy.putExtra("goodsPrice", detailPrice1.getText());
                                intentBuy.putExtra("goodsType", detailGoods1.getText());
                                intentBuy.putExtra("goodsImage", finalImage6);
                                intentBuy.putExtra("inventory", Integer.toString(detailBean.getInventory()));
                                intentBuy.putExtra("product_id", product_id);
                                intentBuy.putExtra("type", String.valueOf(detailBean.getType()));
                                startActivity(intentBuy);
                                TicketDetailActivity.this.finish();
                            }
                        } else {
                            Toast.makeText(TicketDetailActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(TicketDetailActivity.this, LoginActivity.class);
                            intent1.putExtra("n", 1);
                            startActivity(intent1);
                        }
                    }
                });
                detailBuy2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //登录状态
                        SharedPreferences sp = TicketDetailActivity.this.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                        isLoginStatus = sp.getBoolean("isLogin", false);
                        if (isLoginStatus.equals(true)) {
                            //用户名
                            loginUserName = sp.getString("loginUserName", null);
                            userID = new LoginUtil(loginUserName).getLoginRegisterBean().getUser_id();
                            if (new GetDefaultAddressUtil(String.valueOf(userID)).getAddressBean() == null) {
                                Toast.makeText(TicketDetailActivity.this, "请先添加默认地址", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(TicketDetailActivity.this, AddressActivity.class));
                            } else {
                                Intent intentBuy = new Intent(TicketDetailActivity.this, SubmitOrderActivity.class);
                                intentBuy.putExtra("goodsName", detailTitle.getText());
                                intentBuy.putExtra("goodsPrice", detailPrice2.getText());
                                intentBuy.putExtra("goodsType", detailGoods2.getText());
                                intentBuy.putExtra("goodsImage", finalImage6);
                                intentBuy.putExtra("inventory", Integer.toString(detailBean.getInventory()));
                                intentBuy.putExtra("product_id", product_id);
                                intentBuy.putExtra("type", String.valueOf(detailBean.getType()));
                                startActivity(intentBuy);
                                TicketDetailActivity.this.finish();
                            }
                        } else {
                            Toast.makeText(TicketDetailActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(TicketDetailActivity.this, LoginActivity.class);
                            intent1.putExtra("n", 1);
                            startActivity(intent1);
                        }
                    }
                });
                break;
            case 7:
                detailBean = new DetailBeanUtil(product_id).getDetailBeanList();
                //图片URL获取
                url = detailBean.getPictures();
                urlArray = url.split(",");
                //处理价格
                price = detailBean.getPrices();
                priceArray = price.split(",");
                //商品规格
                goodsName = detailBean.getParams();
                goodsNameArray = goodsName.split(",");
                //商品介绍
                introduce = detailBean.getIntroduct();
                introduceArray = introduce.split("<");
                //轮播图
                images = new ArrayList<>();
                images.add(urlArray[0]);
                images.add(urlArray[1]);
                images.add(urlArray[2]);
                banner.setImageLoader(new GlideImageLoader());   //设置图片加载器
                banner.setImages(images);//设置图片源
                banner.setDelayTime(3000);//设置轮播事件，单位毫秒
                banner.setBannerAnimation(Transformer.Default);//设置轮播动画，动画种类很多，有兴趣的去试试吧，我在这里用的是默认
                banner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器的位置
                banner.start();//开始轮播，一定要调用此方法。
                Glide.with(this).load(image).into(goodsImage);
                detailTitle.setText(detailBean.getName());
                if (Integer.parseInt(priceArray[0]) < Integer.parseInt(priceArray[1])) {
                    detailPrice.setText(priceArray[0]);
                } else {
                    detailPrice.setText(priceArray[1]);
                }
                detailCompany.setText(company);
                detailSales.setText(detailBean.getSales());
                detailGoods1.setText(goodsNameArray[0]);
                detailPrice1.setText(priceArray[0]);
                detailGoods2.setText(goodsNameArray[1]);
                detailPrice2.setText(priceArray[1]);
                Glide.with(this).load(urlArray[3]).into(image1);
                Glide.with(this).load(urlArray[1]).into(image2);
                detailContent1.setText(introduceArray[0]);
                detailContent2.setText(introduceArray[1]);
                final String finalImage7 = image;
                detailBuy1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //登录状态
                        SharedPreferences sp = TicketDetailActivity.this.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                        isLoginStatus = sp.getBoolean("isLogin", false);
                        if (isLoginStatus.equals(true)) {
                            //用户名
                            loginUserName = sp.getString("loginUserName", null);
                            userID = new LoginUtil(loginUserName).getLoginRegisterBean().getUser_id();
                            if (new GetDefaultAddressUtil(String.valueOf(userID)).getAddressBean() == null) {
                                Toast.makeText(TicketDetailActivity.this, "请先添加默认地址", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(TicketDetailActivity.this, AddressActivity.class));
                            } else {
                                Intent intentBuy = new Intent(TicketDetailActivity.this, SubmitOrderActivity.class);
                                intentBuy.putExtra("goodsName", detailTitle.getText());
                                intentBuy.putExtra("goodsPrice", detailPrice1.getText());
                                intentBuy.putExtra("goodsType", detailGoods1.getText());
                                intentBuy.putExtra("goodsImage", finalImage7);
                                intentBuy.putExtra("inventory", Integer.toString(detailBean.getInventory()));
                                intentBuy.putExtra("product_id", product_id);
                                intentBuy.putExtra("type", String.valueOf(detailBean.getType()));
                                startActivity(intentBuy);
                                TicketDetailActivity.this.finish();
                            }
                        } else {
                            Toast.makeText(TicketDetailActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(TicketDetailActivity.this, LoginActivity.class);
                            intent1.putExtra("n", 1);
                            startActivity(intent1);
                        }
                    }
                });
                detailBuy2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //登录状态
                        SharedPreferences sp = TicketDetailActivity.this.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                        isLoginStatus = sp.getBoolean("isLogin", false);
                        if (isLoginStatus.equals(true)) {
                            //用户名
                            loginUserName = sp.getString("loginUserName", null);
                            userID = new LoginUtil(loginUserName).getLoginRegisterBean().getUser_id();
                            if (new GetDefaultAddressUtil(String.valueOf(userID)).getAddressBean() == null) {
                                Toast.makeText(TicketDetailActivity.this, "请先添加默认地址", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(TicketDetailActivity.this, AddressActivity.class));
                            } else {
                                Intent intentBuy = new Intent(TicketDetailActivity.this, SubmitOrderActivity.class);
                                intentBuy.putExtra("goodsName", detailTitle.getText());
                                intentBuy.putExtra("goodsPrice", detailPrice2.getText());
                                intentBuy.putExtra("goodsType", detailGoods2.getText());
                                intentBuy.putExtra("goodsImage", finalImage7);
                                intentBuy.putExtra("inventory", Integer.toString(detailBean.getInventory()));
                                intentBuy.putExtra("product_id", product_id);
                                intentBuy.putExtra("type", String.valueOf(detailBean.getType()));
                                startActivity(intentBuy);
                                TicketDetailActivity.this.finish();
                            }
                        } else {
                            Toast.makeText(TicketDetailActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(TicketDetailActivity.this, LoginActivity.class);
                            intent1.putExtra("n", 1);
                            startActivity(intent1);
                        }
                    }
                });
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.right_silent, R.anim.right_out);
    }
}