package com.example.appofzhejiang.Business;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appofzhejiang.R;
import com.lljjcoder.citypickerview.widget.CityPicker;


public class AddressActivity extends AppCompatActivity {

    private Toolbar mBtnBack;//返回按钮
    private TextView newAddress;//区域地址
    private EditText receiveName;//收货人
    private EditText telephoneNumber;//手机号
    private EditText detailAddress;//详细地址
    private RadioButton mRadioButton;//默认地址单选按钮
    private Button cancel;
    private Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        //初始化，找到控件
        initView();

        //区域选择
        newAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseArea(view);
            }
        });
        //返回按钮
        mBtnBack.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //默认地址按钮设置选中和取消
        final GlobalValue globalValue = new GlobalValue();
        mRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isCheck = globalValue.isCheck();
                if (isCheck) {
                    if (view == mRadioButton)
                        mRadioButton.setChecked(false);
                } else {
                    if (view == mRadioButton)
                        mRadioButton.setChecked(true);
                }
                globalValue.setCheck(!isCheck);
            }
        });
        //取消按钮,返回上一页
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //保存按钮,保存添加一条地址信息
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (receiveName.getText().toString() == null) {
                    Toast.makeText(AddressActivity.this, "请输入收货人姓名", Toast.LENGTH_SHORT).show();
                } else if (telephoneNumber.getText() == null) {
                    Toast.makeText(AddressActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                } else if (detailAddress.getText() == null) {
                    Toast.makeText(AddressActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                } else if (newAddress.getText() == null) {
                    Toast.makeText(AddressActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                }else {

                }
            }
        });
    }

    //获取单选按钮checked值的类
    class GlobalValue {
        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        private boolean isCheck;
    }

    //Texview的点击事件
    public void chooseArea(View view) {
        //判断输入法的隐藏状态
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            selectAddress();//调用CityPicker选取区域

        }
    }

    private void selectAddress() {
        CityPicker cityPicker = new CityPicker.Builder(AddressActivity.this)
                .textSize(14)
                .title("地址选择")
                .titleBackgroundColor("#FFFFFF")
                .confirTextColor("#696969")
                .cancelTextColor("#696969")
                .province("浙江省")
                .city("杭州市")
                .district("江干区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        cityPicker.show();
        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                String province = citySelected[0];
                //城市
                String city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                String district = citySelected[2];
                //邮编
                String code = citySelected[3];
                //为TextView赋值
                newAddress.setText(province.trim() + "-" + city.trim() + "-" + district.trim());
            }
        });
    }

    protected void initView() {
        newAddress = findViewById(R.id.new_address);
        mBtnBack = findViewById(R.id.address_toolbar);
        mRadioButton = findViewById(R.id.rb_address);
        receiveName = findViewById(R.id.new_username);
        telephoneNumber = findViewById(R.id.new_number);
        detailAddress = findViewById(R.id.detail_address);
        cancel = findViewById(R.id.btn_address_cancel);
        save = findViewById(R.id.btn_address_save);
    }

}