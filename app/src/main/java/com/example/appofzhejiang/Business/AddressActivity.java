package com.example.appofzhejiang.Business;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appofzhejiang.CustomDialog.CustomDialog;
import com.example.appofzhejiang.Login.LoginUtil;
import com.example.appofzhejiang.R;
import com.example.appofzhejiang.StatusBarUtil.StatusBarUtil;
import com.example.appofzhejiang.ToastUtils;
import com.example.appofzhejiang.fragment3.SubmitOrderActivity;
import com.google.gson.Gson;
import com.lljjcoder.citypickerview.widget.CityPicker;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class AddressActivity extends AppCompatActivity {

    private Toolbar mBtnBack;//返回按钮
    private TextView newAddress;//区域地址
    private EditText receiveName;//收货人
    private EditText telephoneNumber;//手机号
    private EditText detailAddress;//详细地址
    private RadioButton mRadioButton;//默认地址单选按钮
    private TextView save;//保存
    private int userID;//用户ID
    private String id;//地址编号
    private AddressBean addressBean;

    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.right_in, R.anim.right_silent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        //初始化，找到控件
        initView();

        //设置沉浸式
        StatusBarUtil.setTransparent(this);
        StatusBarUtil.setDarkFont(this);

        //获取传过来的参数
        Intent intent = getIntent();
        final int num =intent.getIntExtra("num",0);
        if (num == 1) {
            id = intent.getStringExtra("id");
            addressBean = new GetAddressUtil(id).getAddressBean();
            receiveName.setText(addressBean.getName());
            telephoneNumber.setText(addressBean.getMobile());
            detailAddress.setText(addressBean.getAddress());
            if (addressBean.getDefault()) {
                mRadioButton.setChecked(true);
            } else {
                mRadioButton.setChecked(false);
            }
        }


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

        //用户ID
        SharedPreferences sp = AddressActivity.this.getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        String loginUserName = sp.getString("loginUserName", null);
        userID = new LoginUtil(loginUserName).getLoginRegisterBean().getUser_id();

        //toolbar返回按钮
        if (TextUtils.isEmpty(receiveName.getText().toString().trim()) && TextUtils.isEmpty(telephoneNumber.getText().toString().trim())
                && TextUtils.isEmpty(detailAddress.getText().toString().trim())) {
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
                                if (num == 1) {
                                    String name = receiveName.getText().toString();
                                    String phone = telephoneNumber.getText().toString();
                                    String address = newAddress.getText().toString() + detailAddress.getText().toString();
                                    try {
                                        runUpdate(address, Integer.parseInt(id), phone, name);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    //默认地址修改
                                    runDefaultSet(id,String.valueOf(addressBean.getUser_id()));
                                    ToastUtils.show(AddressActivity.this,"修改成功");
                                } else {
                                    boolean isCheck = globalValue.isCheck();
                                    String name = receiveName.getText().toString();
                                    String phone = telephoneNumber.getText().toString();
                                    String address = newAddress.getText().toString() + detailAddress.getText().toString();
                                    try {
                                        runAddress(isCheck, address, phone, name, userID);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    ToastUtils.show(AddressActivity.this,"添加成功");
                                }
                                finish();
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
                    if (num == 1) {
                        String name = receiveName.getText().toString();
                        String phone = telephoneNumber.getText().toString();
                        String address = newAddress.getText().toString() + detailAddress.getText().toString();
                        try {
                            runUpdate(address, Integer.parseInt(id), phone, name);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //默认地址修改
                        runDefaultSet(id,String.valueOf(addressBean.getUser_id()));
                        ToastUtils.show(AddressActivity.this,"修改成功");
                    } else {
                        boolean isCheck = globalValue.isCheck();
                        String name = receiveName.getText().toString();
                        String phone = telephoneNumber.getText().toString();
                        String address = newAddress.getText().toString() + detailAddress.getText().toString();
                        try {
                            runAddress(isCheck, address, phone, name, userID);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        ToastUtils.show(AddressActivity.this,"添加成功");
                    }
                    finish();
                }
            }
        });


    }

    //向服务器发送post请求
    private void runAddress(boolean _default, String address, String mobile, String name,
                            int user_id) throws InterruptedException {
        final OkHttpClient client = new OkHttpClient();
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("_default", _default);
        map.put("address", address);
        map.put("mobile", mobile);
        map.put("name", name);
        map.put("user_id", user_id);
        String params = gson.toJson(map);

        RequestBody requestBody = RequestBody.create(JSON, params);
        final Request request = new Request.Builder()
                .url("http://120.26.172.104:9002//wx/insertAddress")
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

    //向服务器发送put请求,处理修改信息
    private void runUpdate(String address, int id, String mobile, String name) throws InterruptedException {
        final OkHttpClient client = new OkHttpClient();
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("address", address);
        map.put("id", id);
        map.put("mobile", mobile);
        map.put("name", name);
        String params = gson.toJson(map);

        RequestBody requestBody = RequestBody.create(JSON, params);
        final Request request = new Request.Builder()
                .url("http://120.26.172.104:9002//wx/updateAddress")
                .put(requestBody)
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

    //向服务器发送put请求,修改默认地址
    private void runDefaultSet(String id, String user_id) {
        String url = "http://120.26.172.104:9002//wx/updateDefaultAddress";
        RequestBody formBody = new FormBody.Builder()
                .add("id", id)
                .add("user_id", user_id)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .put(formBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("TAG", "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("TAG", response.protocol() + " " +response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.d("TAG", headers.name(i) + ":" + headers.value(i));
                }
                Log.d("TAG", "onResponse: " + response.body().string());
            }
        });
    }

    //获取单选按钮checked值的类
    class GlobalValue {

        private boolean isCheck;

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

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
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.right_silent, R.anim.right_out);
    }
}