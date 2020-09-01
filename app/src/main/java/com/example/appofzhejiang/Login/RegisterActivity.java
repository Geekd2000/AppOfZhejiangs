package com.example.appofzhejiang.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appofzhejiang.R;
import com.example.appofzhejiang.StatusBarUtil.StatusBarUtil;
import com.example.appofzhejiang.ToastUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    private TextView tv_main_title;//标题
    private Toolbar mBack;//返回按钮
    private Button btn_register;//注册按钮
    //用户名，密码，再次输入的密码的控件
    private EditText et_user_name, et_psw, et_psw_again;
    //用户名，密码，再次输入的密码的控件的获取值
    private String userName, psw, pswAgain;
    //标题布局
    private RelativeLayout rl_title_bar;
    OkHttpClient client = new OkHttpClient();
    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.bottom_in, R.anim.bottom_silent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
        //设置沉浸式
        StatusBarUtil.setTransparent(this);
        StatusBarUtil.setDarkFont(this);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.bottom_silent, R.anim.bottom_out);
    }

    private void init() {
        //从main_title_bar.xml 页面布局中获取对应的UI控件
        tv_main_title = findViewById(R.id.tv_main_title);
        tv_main_title.setText("注册");
        mBack = findViewById(R.id.bar_toolbar);
        //布局根元素
        rl_title_bar = findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.TRANSPARENT);
        //从activity_register.xml 页面中获取对应的UI控件
        btn_register = findViewById(R.id.btn_register);
        et_user_name = findViewById(R.id.et_user_name);
        et_psw = findViewById(R.id.et_psw);
        et_psw_again = findViewById(R.id.et_psw_again);
        mBack.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回键
                RegisterActivity.this.finish();
            }
        });
        //注册按钮
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入在相应控件中的字符串
                getEditString();
                //
                try {
                    runRegister(userName, psw, pswAgain);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                //判断输入框内容
//                if (TextUtils.isEmpty(userName)) {
//                    Toast.makeText(RegisterActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
//                    return;
//                } else if (TextUtils.isEmpty(psw)) {
//                    Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
//                    return;
//                } else if (TextUtils.isEmpty(pswAgain)) {
//                    Toast.makeText(RegisterActivity.this, "请再次输入密码", Toast.LENGTH_SHORT).show();
//                    return;
//                } else if (!psw.equals(pswAgain)) {
//                    Toast.makeText(RegisterActivity.this, "输入两次的密码不一样", Toast.LENGTH_SHORT).show();
//                    return;
//                    /**
//                     *从SharedPreferences中读取输入的用户名，判断SharedPreferences中是否有此用户名
//                     */
//                } else if (isExistUserName(userName)) {
//                    Toast.makeText(RegisterActivity.this, "此账户名已经存在", Toast.LENGTH_SHORT).show();
//                    return;
//                } else {
//                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
//                    //把账号、密码和账号标识保存到sp里面
//                    /**
//                     * 保存账号和密码到SharedPreferences中
//                     */
//                    saveRegisterInfo(userName, psw);
//                    //注册成功后把账号传递到LoginActivity.java中
//                    // 返回值到loginActivity显示
//                    Intent data = new Intent();
//                    data.putExtra("userName", userName);
//                    setResult(RESULT_OK, data);
//                    //RESULT_OK为Activity系统常量，状态码为-1，
//                    // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
//                    RegisterActivity.this.finish();
//                }

            }
        });
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                String  result = "";
                result = (String) msg.obj;
                if (result.equals("\"注册成功\"")) {
                    ToastUtils.show(RegisterActivity.this, "注册成功");
                    /**
                     * * 保存账号和密码到SharedPreferences中
                     */
//                    saveRegisterInfo(userName, psw);
                    //注册成功后把账号传递到LoginActivity.java中
                    // 返回值到loginActivity显示
                    Intent data = new Intent();
                    data.putExtra("userName", userName);
                    setResult(RESULT_OK, data);
                    //RESULT_OK为Activity系统常量，状态码为-1，
                    // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
                    RegisterActivity.this.finish();
                } else if (result.equals("\"注册失败\"")) {
                    ToastUtils.show(RegisterActivity.this, "注册失败");
                    return;
                } else if (result.equals("\"用户名已存在\"")) {
                    ToastUtils.show(RegisterActivity.this, "用户名已存在");
                    return;
                } else if (result.equals("\"两次输入的密码不相同\"")) {
                    ToastUtils.show(RegisterActivity.this, "两次输入的密码不相同");
                    return;
                }
            }
        }
    };

    private void runRegister(final String name, final String tel, final String tell) throws InterruptedException {
        final OkHttpClient client = new OkHttpClient();
        Map map = new HashMap<>();
        map.put("name", name);
        map.put("tel", tel);
        map.put("tell", tell);
        String param = gson.toJson(map);

        RequestBody requestBody = RequestBody.create(JSON, param);
        final Request request = new Request.Builder()
                .url("http://120.26.172.104:9002//web/userRegister")
                .post(requestBody)
                .build();
        //处理注册逻辑
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = client.newCall(request).execute();
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected code " + response);
                    } else {
                        mHandler.obtainMessage(1, response.body().string()).sendToTarget();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        t1.join();
    }

    /**
     * 获取控件中的字符串
     */
    private void getEditString() {
        userName = et_user_name.getText().toString().trim();
        psw = et_psw.getText().toString().trim();
        pswAgain = et_psw_again.getText().toString().trim();
    }

    /**
     * 从SharedPreferences中读取输入的用户名，判断SharedPreferences中是否有此用户名
     */
//    private boolean isExistUserName(String userName) {
//        boolean has_userName = false;
//        //mode_private SharedPreferences sp = getSharedPreferences( );
//        // "loginInfo", MODE_PRIVATE
//        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
//        //获取密码
//        String spPsw = sp.getString(userName, "");//传入用户名获取密码
//        //如果密码不为空则确实保存过这个用户名
//        if (!TextUtils.isEmpty(spPsw)) {
//            has_userName = true;
//        }
//        return has_userName;
//    }

    /**
     * 保存账号和密码到SharedPreferences中SharedPreferences
     */
//    private void saveRegisterInfo(String userName, String psw) {
//        String md5Psw = MD5Utils.md5(psw);//把密码用MD5加密
//        //loginInfo表示文件名, mode_private SharedPreferences sp = getSharedPreferences( );
//        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
//        //获取编辑器， SharedPreferences.Editor  editor -> sp.edit();
//        SharedPreferences.Editor editor = sp.edit();
//        //以用户名为key，密码为value保存在SharedPreferences中
//        //key,value,如键值对，editor.putString(用户名，密码）;
//        editor.putString(userName, md5Psw);
//        //提交修改 editor.commit();
//        editor.commit();
//    }
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
}