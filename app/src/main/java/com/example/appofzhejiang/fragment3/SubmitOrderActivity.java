package com.example.appofzhejiang.fragment3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.appofzhejiang.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.security.auth.PrivateCredentialPermission;

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
    private RelativeLayout relativeLayout;//选择地址

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_order);
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
        endCount=findViewById(R.id.order_shuliang);
        time=findViewById(R.id.order_playTime);
        relativeLayout=findViewById(R.id.name_address_telephone);

        //标题栏返回按钮
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //跳转到收货地址页面
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SubmitOrderActivity.this, "你点击了", Toast.LENGTH_SHORT).show();
            }
        });

        //获得传递过来的参数
        Intent intent = getIntent();
        String name = intent.getStringExtra("goodsName");
        String type = intent.getStringExtra("goodsType");
        String unitPrice = intent.getStringExtra("goodsPrice");

        //将传递过来的参设设置进去
        goodsName.setText(name);
        goodsType.setText(type);
        goodsUnitPrice.setText(unitPrice);

        //合计、实付
        tot=Integer.toString(Integer.parseInt(buyCount.getText().toString())*Integer.parseInt(goodsUnitPrice.getText().toString()));
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
                    tot=Integer.toString(count*Integer.parseInt(goodsUnitPrice.getText().toString()));
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
                    tot=Integer.toString(count*Integer.parseInt(goodsUnitPrice.getText().toString()));
                    totalPrice.setText(tot);
                    payment.setText(tot);
                } else {
                    Toast.makeText(SubmitOrderActivity.this, "不能再加啦>_<", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //添加订单
        addOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SubmitOrderActivity.this, "订单添加成功", Toast.LENGTH_SHORT).show();
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