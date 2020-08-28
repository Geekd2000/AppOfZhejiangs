package com.example.appofzhejiang.Business;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appofzhejiang.CustomDialog.CustomDialog;
import com.example.appofzhejiang.R;
import com.example.appofzhejiang.StatusBarUtil.StatusBarUtil;
import com.lljjcoder.citypickerview.widget.CityPicker;


public class AddressActivity extends AppCompatActivity {

    private Toolbar mBtnBack;//返回按钮
    private TextView newAddress;//区域地址
    private EditText receiveName;//收货人
    private EditText telephoneNumber;//手机号
    private EditText detailAddress;//详细地址
    private RadioButton mRadioButton;//默认地址单选按钮
    private TextView save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        //初始化，找到控件
        initView();

        //设置沉浸式
        StatusBarUtil.setTransparent(this);
        StatusBarUtil.setDarkFont(this);

        //获取传过来的参数
        Intent intent = getIntent();
        final int size = Integer.parseInt(intent.getStringExtra("size"));
        //区域选择
        newAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseArea(view);
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
        //toolbar返回按钮
        if (TextUtils.isEmpty(receiveName.getText().toString()) && TextUtils.isEmpty(telephoneNumber.getText().toString())
                && TextUtils.isEmpty(newAddress.getText().toString()) && TextUtils.isEmpty(detailAddress.getText().toString())) {
            mBtnBack.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        } else {
            mBtnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CustomDialog customDialog = new CustomDialog(AddressActivity.this);
                    customDialog.setTitle("提示").setMessage("是否保存本次编辑结果")
                            .setCancel("取消", new CustomDialog.IOnCancelListener() {
                                @Override
                                public void onCancel(CustomDialog dialog) {
                                    // 若取消，就返回上一页
                                    finish();
                                }
                            }).setConfirm("保存", new CustomDialog.IOnConfirmListener() {
                        @Override
                        public void onConfirm(CustomDialog dialog) {
                            // 保存本条记录
                            if (TextUtils.isEmpty(receiveName.getText().toString().trim())) {
                                Toast.makeText(AddressActivity.this, "请输入收货人姓名", Toast.LENGTH_SHORT).show();
                            } else if (TextUtils.isEmpty(telephoneNumber.getText().toString().trim())) {
                                Toast.makeText(AddressActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                            } else if (TextUtils.isEmpty(newAddress.getText().toString().trim())) {
                                Toast.makeText(AddressActivity.this, "请选择区域", Toast.LENGTH_SHORT).show();
                            } else if (TextUtils.isEmpty(detailAddress.getText().toString().trim())) {
                                Toast.makeText(AddressActivity.this, "请输入详细地址", Toast.LENGTH_SHORT).show();
                            } else {
                                boolean isCheck = globalValue.isCheck();
                                if (isCheck == false) {
                                    Intent intent = new Intent();
                                    intent.putExtra("name", receiveName.getText().toString());
                                    intent.putExtra("phone", telephoneNumber.getText().toString());
                                    intent.putExtra("address", newAddress.getText().toString() + detailAddress.getText().toString());
                                    intent.putExtra("select", "false");
                                    setResult(RESULT_OK, intent);
                                    finish();
                                } else {
                                    Intent intent = new Intent();
                                    intent.putExtra("name", receiveName.getText().toString());
                                    intent.putExtra("phone", telephoneNumber.getText().toString());
                                    intent.putExtra("address", newAddress.getText().toString() + detailAddress.getText().toString());
                                    intent.putExtra("select", "true");
                                    setResult(RESULT_OK, intent);
                                    finish();
                                }
                            }
                        }
                    }).show();
                }
            });
        }
        //保存按钮,保存添加一条地址信息
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(receiveName.getText().toString().trim())) {
                    Toast.makeText(AddressActivity.this, "请输入收货人姓名", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(telephoneNumber.getText().toString().trim())) {
                    Toast.makeText(AddressActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(newAddress.getText().toString().trim())) {
                    Toast.makeText(AddressActivity.this, "请选择区域", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(detailAddress.getText().toString().trim())) {
                    Toast.makeText(AddressActivity.this, "请输入详细地址", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isCheck = globalValue.isCheck();
                    if (isCheck == false) {
                        Intent intent = new Intent();
                        intent.putExtra("name", receiveName.getText().toString());
                        intent.putExtra("phone", telephoneNumber.getText().toString());
                        intent.putExtra("address", newAddress.getText().toString() + detailAddress.getText().toString());
                        intent.putExtra("select", "false");
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        Intent intent = new Intent();
                        intent.putExtra("name", receiveName.getText().toString());
                        intent.putExtra("phone", telephoneNumber.getText().toString());
                        intent.putExtra("address", newAddress.getText().toString() + detailAddress.getText().toString());
                        intent.putExtra("select", "true");
                        setResult(RESULT_OK, intent);
                        finish();
                    }
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

    //TexView的点击事件
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
        save = findViewById(R.id.txt_address_save);
    }

}