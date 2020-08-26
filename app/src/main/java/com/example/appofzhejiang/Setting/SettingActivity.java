package com.example.appofzhejiang.Setting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.appofzhejiang.Login.LoginActivity;
import com.example.appofzhejiang.MainActivity;
import com.example.appofzhejiang.R;
import com.example.appofzhejiang.StatusBarUtil.StatusBarUtil;

public class SettingActivity extends AppCompatActivity {

    private Button mBtnRollOut;
    private RelativeLayout user;
    private Toolbar back;
    private TextView username;
    private Switch mPush;
    private Boolean isLoginStatus;

    public static SettingActivity instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mBtnRollOut = findViewById(R.id.roll_out);
        user = findViewById(R.id.user);
        back = findViewById(R.id.setting_toolbar);
        username = findViewById(R.id.user_name_setting);
        mPush = findViewById(R.id.push);

        LoginStatus();

        //设置沉浸式
        StatusBarUtil.setTransparent(this);
        StatusBarUtil.setDarkFont(this);

        mPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean isChecked = mPush.isChecked();
                check(isChecked);
                mPush.setChecked(checked());
            }
        });

        back.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mBtnRollOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                isLoginStatus = sp.getBoolean("isLogin", false);
                if (isLoginStatus == true) {
                    Out(false);
                    Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(SettingActivity.this, "退出成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SettingActivity.this, "未登录，无法退出请先登录", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                }
            }
        });

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                isLoginStatus = sp.getBoolean("isLogin", false);
                if (isLoginStatus == true) {
                    Intent intent = new Intent(SettingActivity.this, PersonalInformationActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(SettingActivity.this, "未登录，无法修改请先登录", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                }
            }
        });
    }

    /**
     * 判断登录状态
     */
    public void LoginStatus() {
        SharedPreferences sp = getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        isLoginStatus = sp.getBoolean("isLogin", false);
        if (isLoginStatus == true) {
            username.setText(getName());
            mPush.setChecked(checked());
        }
    }

    public void Out(Boolean status) {
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取编辑器
        SharedPreferences.Editor editor = sp.edit();
        //存入boolean类型的登录状态
        editor.putBoolean("isLogin", status);
        //提交修改
        editor.commit();
    }

    public String getName() {
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        String username = sp.getString("loginUserName", null);
        return username;
    }

    public void check(Boolean isChecked) {
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isChecked", isChecked);
        editor.commit();
    }

    public Boolean checked() {
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        Boolean checked = sp.getBoolean("isChecked", false);
        return checked;
    }
}