package com.example.appofzhejiang.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appofzhejiang.MainActivity;
import com.example.appofzhejiang.R;
import com.example.appofzhejiang.StatusBarUtil.StatusBarUtil;
import com.example.appofzhejiang.ToastUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private TextView tv_main_title;//标题
    private TextView tv_register, tv_find_psw;//返回键,显示的注册，找回密码
    private Button btn_login;//登录按钮
    private String userName, psw, spPsw;//获取的用户名，密码，加密密码
    private EditText et_user_name, et_psw;//编辑框
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.bottom_in, R.anim.bottom_silent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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

    //获取界面控件
    private void init() {
        //从main_title_bar中获取的id
        tv_main_title = findViewById(R.id.tv_main_title);
        tv_main_title.setText("登录");
        toolbar = findViewById(R.id.bar_toolbar);
        //从activity_login.xml中获取的
        tv_register = findViewById(R.id.tv_register);
        tv_find_psw = findViewById(R.id.tv_find_psw);
        btn_login = findViewById(R.id.btn_login);
        et_user_name = findViewById(R.id.et_user_name);
        et_psw = findViewById(R.id.et_psw);
        //返回键的点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //登录界面销毁
                LoginActivity.this.finish();
            }
        });
        //立即注册控件的点击事件
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //为了跳转到注册界面，并实现注册功能
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        //找回密码控件的点击事件
        tv_find_psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到找回密码界面（此页面暂未创建）
            }
        });
        //登录按钮的点击事件
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开始登录，获取用户名和密码 getText().toString().trim();
                userName = et_user_name.getText().toString().trim();
                psw = et_psw.getText().toString().trim();
                //请求获取服务器数据
                try {
                    runLogin(userName, psw);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                String  result = "";
                result = (String) msg.obj;
                if (result.equals("[\"用户名不存在\"]")) {
                    ToastUtils.show(LoginActivity.this, "用户名不存在");
                    return;
                } else if (result.equals("[\"密码错误\"]")) {
                    ToastUtils.show(LoginActivity.this, "密码错误");
                    return;
                } else if (result.equals("[\"未审核\"]")) {
                    ToastUtils.show(LoginActivity.this, "未审核");
                    return;
                } else {
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    //保存登录状态，在界面保存登录的用户名 定义个方法 saveLoginStatus boolean 状态 , userName 用户名;
                    saveLoginStatus(true, userName);
                    //登录成功后关闭此页面进入主页
                    Intent data = new Intent();
                    //datad.putExtra( ); name , value ;
                    data.putExtra("isLogin", true);
                    //RESULT_OK为Activity系统常量，状态码为-1
                    // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
                    setResult(RESULT_OK, data);
                    //销毁登录界面
                    LoginActivity.this.finish();
                    //跳转到主界面，登录成功的状态传递到 MainActivity 中
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }
        }
    };
    private void runLogin( String username, String psw) throws InterruptedException {
        final OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder()
                .url("http://120.26.172.104:9002//web/userLogin?name="+username+"&tel="+psw)
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
//                        mHandler.obtainMessage(1, response.body().string()).sendToTarget();
//                        System.out.println(response.body().string());
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
     * 从SharedPreferences中根据用户名读取密码
     */
    private String readPsw(String userName) {
        //getSharedPreferences("loginInfo",MODE_PRIVATE);
        //"loginInfo",mode_private; MODE_PRIVATE表示可以继续写入
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        //sp.getString() userName, "";
        return sp.getString(userName, "");
    }

    /**
     * 保存登录状态和登录用户名到SharedPreferences中
     */
    private void saveLoginStatus(boolean status, String userName) {
        //saveLoginStatus(true, userName);
        //loginInfo表示文件名  SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取编辑器
        SharedPreferences.Editor editor = sp.edit();
        //存入boolean类型的登录状态
        editor.putBoolean("isLogin", status);
        //存入登录状态时的用户名
        editor.putString("loginUserName", userName);
        //提交修改
        editor.commit();
    }

    /**
     * 注册成功的数据返回至此
     *
     * @param requestCode 请求码
     * @param resultCode  结果码
     * @param data        数据
     */
    @Override
    //显示数据， onActivityResult
    //startActivityForResult(intent, 1); 从注册界面中获取数据
    //int requestCode , int resultCode , Intent data
    // LoginActivity -> startActivityForResult -> onActivityResult();
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            //是获取注册界面回传过来的用户名
            // getExtra().getString("***");
            String userName = data.getStringExtra("userName");
            if (!TextUtils.isEmpty(userName)) {
                //设置用户名到 et_user_name 控件
                et_user_name.setText(userName);
                //et_user_name控件的setSelection()方法来设置光标位置
                et_user_name.setSelection(userName.length());
            }
        }
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
}