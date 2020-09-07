package com.example.appofzhejiang.Setting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.appofzhejiang.Login.LoginActivity;
import com.example.appofzhejiang.Login.MD5Utils;
import com.example.appofzhejiang.Login.RegisterActivity;
import com.example.appofzhejiang.MainActivity;
import com.example.appofzhejiang.R;
import com.example.appofzhejiang.StatusBarUtil.StatusBarUtil;
import com.example.appofzhejiang.ToastUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PasswordChangeActivity extends AppCompatActivity {

    private Button mBtnChange;
    private EditText old_password, new_password, new_password_again;
    private Toolbar mBtnBack;
    OkHttpClient client = new OkHttpClient();
    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.right_in, R.anim.right_silent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);
        mBtnBack = findViewById(R.id.personal_toolbar);
        mBtnChange = findViewById(R.id.btn_password_change);
        old_password = findViewById(R.id.old_password);
        new_password = findViewById(R.id.new_password);
        new_password_again = findViewById(R.id.new_password_again);

        BindView();
        //设置沉浸式
        StatusBarUtil.setTransparent(this);
        StatusBarUtil.setDarkFont(this);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                String result = "";
                result = (String) msg.obj;
                if (result.equals("\"修改成功\"")) {
                    Toast.makeText(PasswordChangeActivity.this, "修改成功,请重新登录", Toast.LENGTH_SHORT).show();
                    //退出登录
                    Out(false);
                    //登录成功后关闭此页面进入主页
                    Intent data = new Intent();
                    //datad.putExtra( ); name , value ;
                    data.putExtra("isLogin", true);
                    //RESULT_OK为Activity系统常量，状态码为-1
                    // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
                    setResult(RESULT_OK, data);
                    //销毁设置界面
                    PasswordChangeActivity.this.finish();
                    //跳转到主界面，登录成功的状态传递到 MainActivity 中
                    startActivity(new Intent(PasswordChangeActivity.this, LoginActivity.class));
                }else if(result.equals("\"用户名不存在\"")){
                    Toast.makeText(PasswordChangeActivity.this, "用户名不存在", Toast.LENGTH_SHORT).show();
                    return;
                }else if(result.equals("\"不能和原密码相同\"")){
                    Toast.makeText(PasswordChangeActivity.this, "不能和原密码相同", Toast.LENGTH_SHORT).show();
                    return;
                }else if(result.equals("\"第二次输入的密码和第一次不同\"")){
                    Toast.makeText(PasswordChangeActivity.this, "第二次输入的密码和第一次不同", Toast.LENGTH_SHORT).show();
                    return;
                }else if(result.equals("\"修改失败\"")){
                    Toast.makeText(PasswordChangeActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        }
    };

    private void runChange(String username, String psw, String tel, String tell) throws InterruptedException {
        final OkHttpClient client = new OkHttpClient();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", username);
            jsonObject.put("psw", psw);
            jsonObject.put("tel", tel);
            jsonObject.put("tell", tell);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(jsonObject);

        RequestBody requestBody = RequestBody.create(JSON, jsonObject.toString());
        final Request request = new Request.Builder()
                .url("http://120.26.172.104:9002//web/updateTel")
                .put(requestBody)
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
//                        System.out.println(response.body().string());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        t1.join();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.right_silent, R.anim.right_out);
    }

    public void BindView() {
        Onclick onclick = new Onclick();
        mBtnBack.setOnClickListener(onclick);
        mBtnChange.setOnClickListener(onclick);
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

    private void submit() {
        String psw = old_password.getText().toString().trim();
        String newPsw = new_password.getText().toString().trim();
        String again = new_password_again.getText().toString().trim();

        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);

        String username = sp.getString("loginUserName", null);
        try {
            runChange(username, psw, newPsw, again);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//
//        if (TextUtils.isEmpty(psw)) {
//            Toast.makeText(PasswordChangeActivity.this, "请输入原始密码", Toast.LENGTH_SHORT).show();
//            return;
//        } else if (!MD5Utils.md5(psw).equals(readPsw())) {
//            Log.i("MD5Utils.md5(psw)", "" + MD5Utils.md5(psw));
//            Log.i("readPsw", "" + readPsw());
//            Toast.makeText(PasswordChangeActivity.this, "输入的密码与原始密码不一致", Toast.LENGTH_SHORT).show();
//            return;
//        } else if (MD5Utils.md5(newPsw).equals(readPsw())) {
//            Toast.makeText(PasswordChangeActivity.this, "输入的新密码与原始密码不能一致", Toast.LENGTH_SHORT).show();
//            return;
//        } else if (TextUtils.isEmpty(psw)) {
//            Toast.makeText(PasswordChangeActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
//            return;
//        } else if (TextUtils.isEmpty(again)) {
//            Toast.makeText(PasswordChangeActivity.this, "请再次输入新密码", Toast.LENGTH_SHORT).show();
//        } else if (!newPsw.equals(again)) {
//            Toast.makeText(PasswordChangeActivity.this, "再次输入的新密码不一致", Toast.LENGTH_SHORT).show();
//            return;
//        } else {
//            Toast.makeText(PasswordChangeActivity.this, "新密码设置成功,请重新登录", Toast.LENGTH_SHORT).show();
//            //存入新密码
//            modifyPsw();
//            //退出登录
//            Out(false);
//            //登录成功后关闭此页面进入主页
//            Intent data = new Intent();
//            //datad.putExtra( ); name , value ;
//            data.putExtra("isLogin", true);
//            //RESULT_OK为Activity系统常量，状态码为-1
//            // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
//            setResult(RESULT_OK, data);
//            //销毁设置界面
//            PasswordChangeActivity.this.finish();
//            //跳转到主界面，登录成功的状态传递到 MainActivity 中
//            startActivity(new Intent(PasswordChangeActivity.this, MainActivity.class));
//        }
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

    class Onclick implements View.OnClickListener {


        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.personal_toolbar:
                    PasswordChangeActivity.this.finish();
                    break;
                case R.id.btn_password_change:
                    submit();
//                    PasswordChangeActivity.this.finish();
                    break;
            }
        }
    }

    /**
     * 从SharedPreferences中根据用户名读取密码
     */
    private String readPsw() {
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        String username = sp.getString("loginUserName", null);
        String spPsw = sp.getString(username, "");
        Log.i("username", username);
        Log.i("spPsw", spPsw);
        return spPsw;

    }
    //读取当前登录的用户名

    private void modifyPsw() {
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);

        String username = sp.getString("loginUserName", null);

        String newPsw = new_password.getText().toString().trim();

        String md5psw = MD5Utils.md5(newPsw);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(username, md5psw);
        editor.commit();

    }

}