package com.example.appofzhejiang.fragment3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.example.appofzhejiang.Business.AddressBean;
import com.example.appofzhejiang.Business.GetDefaultAddressUtil;
import com.example.appofzhejiang.Business.ReceiptActivity;
import com.example.appofzhejiang.Login.LoginUtil;
import com.example.appofzhejiang.MainActivity;
import com.example.appofzhejiang.R;
import com.example.appofzhejiang.StatusBarUtil.StatusBarUtil;
import com.example.appofzhejiang.pay.OrderDialog;
import com.example.appofzhejiang.pay.PaySuccessActivity;
import com.github.clans.fab.FloatingActionButton;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class SubmitOrderActivity extends AppCompatActivity {

    private TimePickerView pvTime;//时间选择器
    private String tot;
    private Toolbar toolbar;//顶部标题栏
    private TextView goodsName;//商品名称
    private TextView goodsType;//商品规格
    private TextView goodsUnitPrice;//商品单价
    private ImageView add, reduce;//添加、减少
    private TextView buyCount;//购买数量
    private TextView totalPrice, payment;//合计、实付
    private TextView addOrder;//添加订单
    private TextView stock;//库存
    private TextView endCount;//最终数量
    private TextView time;//出行时间
    private TextView username;//收货人姓名
    private TextView telephone;//手机号
    private TextView address;//地址
    private ImageView goodsImage;//商品图片
    private RelativeLayout relativeLayout;//选择地址
    private int userID;//用户ID

    //悬浮按钮
    private FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;
    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.right_in, R.anim.right_silent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_order);

        //设置沉浸式
        StatusBarUtil.setTransparent(this);
        StatusBarUtil.setDarkFont(this);

        //初始化控件
        init();

        //初始化悬浮按钮
        initFloatActionButton();

        //标题栏返回按钮
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //获得传递过来的参数
        Intent intent = getIntent();
        String name = intent.getStringExtra("goodsName");
        String type = intent.getStringExtra("goodsType");
        String unitPrice = intent.getStringExtra("goodsPrice");
        final String image = intent.getStringExtra("goodsImage");
        String inventory = intent.getStringExtra("inventory");
        final String product_id = intent.getStringExtra("product_id");
        final String totalType = intent.getStringExtra("type");

        //将传递过来的参设设置进去
        goodsName.setText(name);//商品名称
        goodsType.setText(type);//商品种类
        goodsUnitPrice.setText(unitPrice);//商品单价
        Glide.with(this).load(image).into(goodsImage);//商品图片
        stock.setText(inventory);//商品库存

        //合计、实付
        tot = Integer.toString(Integer.parseInt(buyCount.getText().toString()) * Integer.parseInt(goodsUnitPrice.getText().toString()));
        totalPrice.setText(tot);
        payment.setText(tot);

        //添加减少数量
        reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = buyCount.getText().toString();
                int count = Integer.parseInt(s);
                if (count > 1) {
                    count = count - 1;
                    String num = Integer.toString(count);
                    buyCount.setText(num);
                    endCount.setText(num);
                    tot = Integer.toString(count * Integer.parseInt(goodsUnitPrice.getText().toString()));
                    totalPrice.setText(tot);
                    payment.setText(tot);
                } else {
                    Toast.makeText(SubmitOrderActivity.this, "不能再减啦>_<", Toast.LENGTH_SHORT).show();
                }
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = buyCount.getText().toString();
                int count = Integer.parseInt(s);
                if (count < Integer.parseInt(stock.getText().toString())) {
                    count = count + 1;
                    String num = Integer.toString(count);
                    buyCount.setText(num);
                    endCount.setText(num);
                    tot = Integer.toString(count * Integer.parseInt(goodsUnitPrice.getText().toString()));
                    totalPrice.setText(tot);
                    payment.setText(tot);
                } else {
                    Toast.makeText(SubmitOrderActivity.this, "不能再加啦>_<", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //出行时间设置
        initTimePicker();
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pvTime != null) {
                    pvTime.show(view);//弹出时间选择器，传递参数过去，回调的时候则可以绑定此view
                }
            }
        });

        //用户ID
        SharedPreferences sp = SubmitOrderActivity.this.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String loginUserName = sp.getString("loginUserName", null);
        userID = new LoginUtil(loginUserName).getLoginRegisterBean().getUser_id();
        //设置默认地址
        AddressBean addressBean=new GetDefaultAddressUtil(String.valueOf(userID)).getAddressBean();
        username.setText(addressBean.getName());
        telephone.setText(addressBean.getMobile());
        address.setText(addressBean.getAddress());

        //跳转到收货地址页面,选择地址
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubmitOrderActivity.this, ReceiptActivity.class);
                intent.putExtra("code", "1");
                startActivityForResult(intent, 1);
            }
        });

        //添加订单，跳转至订单详情页面
        addOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(time.getText().toString())) {
                    Toast.makeText(SubmitOrderActivity.this, "请选择使用时间", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SubmitOrderActivity.this, "订单添加成功", Toast.LENGTH_SHORT).show();
                    //订单
                    long currentTime = System.currentTimeMillis();
                    String timeNow = new SimpleDateFormat("yyyy-MM-dd").format(currentTime);

                    //订单号生成
                    //订单编号随机生成
                    int r1 = (int) (Math.random() * (9) + 1);//产生2个0-9的随机数
                    int r2 = (int) (Math.random() * (10));
                    long now = System.currentTimeMillis();//一个13位的时间戳
                    String paymentID = String.valueOf(r1) + String.valueOf(r2) + String.valueOf(now);// 订单ID

                    //post数据到服务器上
                    try {
                        runRegister(false,address.getText().toString(),Double.parseDouble(payment.getText().toString()),
                                username.getText().toString(),Integer.parseInt(endCount.getText().toString()),paymentID,false,
                                Integer.parseInt(product_id),image,goodsName.getText().toString(),telephone.getText().toString(),
                                userID,totalType,goodsType.getText().toString());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    OrderDialog orderDialog = new OrderDialog(SubmitOrderActivity.this);
                    orderDialog.setGoodsName1(goodsName.getText().toString());
                    orderDialog.setGoodsName2(goodsName.getText().toString());
                    orderDialog.setGoodsType(goodsType.getText().toString());
                    orderDialog.setGoodsUnitPrice(goodsUnitPrice.getText().toString());
                    orderDialog.setAmount(endCount.getText().toString());
                    orderDialog.setPay1(payment.getText().toString());
                    orderDialog.setPay2(payment.getText().toString());
                    orderDialog.setUsername(username.getText().toString());
                    orderDialog.setTelephone(telephone.getText().toString());
                    orderDialog.setAddress(address.getText().toString());
                    orderDialog.setImage(image, SubmitOrderActivity.this);
                    orderDialog.setTimeNow(timeNow);
                    orderDialog.setNumber(paymentID);
                    orderDialog.show();
                    orderDialog.setCancel("cancel", new OrderDialog.IOnCancelListener() {
                        @Override
                        public void onCancel(OrderDialog dialog) {
                            Toast.makeText(SubmitOrderActivity.this, "取消成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SubmitOrderActivity.this, TicketActivity.class);
                            intent.putExtra("num", "0");
                            startActivity(intent);
                            SubmitOrderActivity.this.finish();
                        }
                    });
                    orderDialog.setGoto("back", new OrderDialog.IOnGotoListener() {
                        @Override
                        public void onGoto(OrderDialog dialog) {
                            Intent intent = new Intent(SubmitOrderActivity.this, TicketActivity.class);
                            intent.putExtra("num", "0");
                            startActivity(intent);
                            SubmitOrderActivity.this.finish();
                        }
                    });
                    orderDialog.setConfirm("pay", new OrderDialog.IOnConfirmListener() {
                        @Override
                        public void onConfirm(OrderDialog dialog) {

                            Intent intent = new Intent(SubmitOrderActivity.this, PaySuccessActivity.class);
                            startActivity(intent);
                            SubmitOrderActivity.this.finish();
                        }
                    });
                }
            }
        });
    }

    //向服务器发送post请求
    private void runRegister(boolean _pay, String delivery_way, double money, String name,
                             int num, String order_no, boolean pin, int product_id, String remarks,
                             String shop_name, String tel, int user_id, String type, String param) throws InterruptedException {
        final OkHttpClient client = new OkHttpClient();
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("_pay", _pay);
        map.put("delivery_way", delivery_way);
        map.put("money", money);
        map.put("name", name);
        map.put("num", num);
        map.put("order_no", order_no);
        map.put("pin", pin);
        map.put("product_id", product_id);
        map.put("remarks", remarks);
        map.put("shop_name", shop_name);
        map.put("tel", tel);
        map.put("user_id", user_id);
        map.put("type", type);
        map.put("param", param);
        String params = gson.toJson(map);

        RequestBody requestBody = RequestBody.create(JSON, params);
        final Request request = new Request.Builder()
                .url("http://120.26.172.104:9002/wx/insertOrder")
                .post(requestBody)
                .build();
        //处理注册逻辑
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = client.newCall(request).execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        t1.join();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (intent != null && requestCode == 1 && resultCode == 2) {
            String nameInfo = intent.getStringExtra("name");
            String phoneInfo = intent.getStringExtra("phone");
            String addressInfo = intent.getStringExtra("address");
            username.setText(nameInfo);
            telephone.setText(phoneInfo);
            address.setText(addressInfo);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.right_silent, R.anim.right_out);
    }

    //初始化控件
    public void init() {
        toolbar = findViewById(R.id.toolbar_order);
        goodsName = findViewById(R.id.order_goods_name);
        goodsType = findViewById(R.id.order_goods_type);
        goodsUnitPrice = findViewById(R.id.order_danjia);
        add = findViewById(R.id.order_add);
        reduce = findViewById(R.id.order_reduce);
        buyCount = findViewById(R.id.order_buyCount);
        totalPrice = findViewById(R.id.order_heji);
        payment = findViewById(R.id.order_shifu);
        addOrder = findViewById(R.id.add_order);
        stock = findViewById(R.id.order_kucun);
        endCount = findViewById(R.id.order_shuliang);
        time = findViewById(R.id.order_playTime);
        relativeLayout = findViewById(R.id.name_address_telephone);
        username = findViewById(R.id.order_name);
        telephone = findViewById(R.id.order_telephone);
        address = findViewById(R.id.order_address);
        goodsImage = findViewById(R.id.goods_image);
    }

    //悬浮按钮配置
    public void initFloatActionButton() {
        floatingActionButton1 = findViewById(R.id.floatingActionButton1);
        floatingActionButton2 = findViewById(R.id.floatingActionButton2);
        floatingActionButton3 = findViewById(R.id.floatingActionButton3);

        floatingActionButton1.setLabelText("首页");
        floatingActionButton2.setLabelText("景点");
        floatingActionButton3.setLabelText("商城");
        floatingActionButton1.setImageResource(R.drawable.shouye);
        floatingActionButton2.setImageResource(R.drawable.jingdian);
        floatingActionButton3.setImageResource(R.drawable.shangcheng);

        //跳转至首页页面
        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubmitOrderActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //跳转至更多热门景点列表页面（暂无跳转 更多景点页面未写）
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, RecyclerPageActivity.class);
//                startActivity(intent);
            }
        });

        //跳转至商品列表页面(默认进入商品列表的门票页面)
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SubmitOrderActivity.this, TicketActivity.class);
                intent.putExtra("num", "0");
                startActivity(intent);
            }
        });
    }

    private void initTimePicker() {//Dialog 模式下，在底部弹出

        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                time.setText(getTime(date));
                Log.i("pvTime", "onTimeSelect");

            }
        })
                .setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
                    @Override
                    public void onTimeSelectChanged(Date date) {
                        Log.i("pvTime", "onTimeSelectChanged");
                    }
                }).setCancelText("取消")
                .setSubmitText("确认")
                .setTitleText("选择时间")
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
            }
        }
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

}