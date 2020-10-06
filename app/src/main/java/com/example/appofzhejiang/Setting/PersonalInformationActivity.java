package com.example.appofzhejiang.Setting;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.appofzhejiang.Login.LoginActivity;
import com.example.appofzhejiang.MainActivity;
import com.example.appofzhejiang.R;
import com.example.appofzhejiang.StatusBarUtil.StatusBarUtil;
import com.example.appofzhejiang.ToastUtils;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PersonalInformationActivity extends AppCompatActivity {

    private TextView sexChange, mBtnSave;
    private Toolbar mBack;
    private String[] sexArry = new String[]{"保密", "男", "女"};// 性别选择
    private TextView passwordChange;
    private EditText username;
    private CircleImageView mImage;

    OkHttpClient client = new OkHttpClient();
    Gson gson = new Gson();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.right_in,R.anim.right_silent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);
        mBtnSave = findViewById(R.id.save);
        sexChange = findViewById(R.id.sex_change);
        mBack = findViewById(R.id.personal_toolbar);
        passwordChange = findViewById(R.id.user_password);
        username = findViewById(R.id.ni_cheng);
        mImage = findViewById(R.id.user_image_my);


        //设置沉浸式
        StatusBarUtil.setTransparent(this);
        StatusBarUtil.setDarkFont(this);

        OnClick onClick = new OnClick();

        sexChange.setOnClickListener(onClick);
        mBtnSave.setOnClickListener(onClick);
        mBack.setOnClickListener(onClick);
        passwordChange.setOnClickListener(onClick);
        mImage.setOnClickListener(onClick);

        username.setText(save());
        sexChange.setText(sex());
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.right_silent,R.anim.right_out);
    }
    private void runChange(String username, String name) throws InterruptedException {
        final OkHttpClient client = new OkHttpClient();
        Map map = new HashMap<>();
        map.put("username", username);//呢称;
        map.put("name", name);//用户名
        String param = gson.toJson(map);

        RequestBody requestBody = RequestBody.create(JSON, param);
        final Request request = new Request.Builder()
                .url("http://47.97.10.218:9002//web/updateUsername")
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
                        System.out.println(response.body().string());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        t1.join();
    }
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                String  result = "";
                result = (String) msg.obj;


                String name = username.getText().toString().trim();
                String sex = sexChange.getText().toString().trim();

                changeName(name, sex);
                //一致登录成功
                Toast.makeText(PersonalInformationActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                //登录成功后关闭此页面进入主页
                Intent data = new Intent();
                //datad.putExtra( ); name , value ;
                data.putExtra("isLogin", true);
                //RESULT_OK为Activity系统常量，状态码为-1
                // 表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
                setResult(RESULT_OK, data);
                //销毁设置界面
                PersonalInformationActivity.this.finish();
                //跳转到主界面，登录成功的状态传递到 MainActivity 中
                Intent intent = new Intent(PersonalInformationActivity.this, MainActivity.class);
                intent.putExtra("numb",3);
                startActivity(intent);
            }
        }
    };

    class OnClick implements View.OnClickListener {

        Intent intent = null;

        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.sex_change:
                    AlertDialog.Builder builder3 = new AlertDialog.Builder(PersonalInformationActivity.this);// 自定义对话框
                    builder3.setSingleChoiceItems(sexArry, 0, new DialogInterface.OnClickListener() {// 2默认的选中

                        @Override
                        public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                            // showToast(which+"");
                            sexChange.setText(sexArry[which]);
                            dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
                        }
                    });
                    builder3.show();// 让弹出框显示
                    break;
                case R.id.user_password:
                    intent = new Intent(PersonalInformationActivity.this, PasswordChangeActivity.class);
                    startActivity(intent);
                    break;
                case R.id.personal_toolbar:
                    finish();
                    break;
                case R.id.save:
                    String name = username.getText().toString().trim();

                    SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
                    String oldName = sp.getString("loginUserName",null);
                    System.out.println(oldName);
                    try {
                        runChange(name, oldName);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.user_image_my:
                    break;
            }
        }
    }

    public String save() {
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        String username = sp.getString("loginNickname", null);
        return username;
    }

    public void changeName(String name, String sex) {

        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        String oldName = sp.getString("loginNickname", null);
        String password = sp.getString(oldName, "");
        String oldSex = sp.getString("sex", null);

        //更新存储数据的用户名
        editor.remove(oldName);
        editor.putString(name, password);

        //更新登陆状态的用户名
        editor.remove("loginNickname");
        editor.putString("loginNickname", name);

        if (oldSex == null) {
            editor.putString("sex", sex);
        } else {
            editor.remove("sex");
            editor.putString("sex", sex);
        }
        editor.commit();
    }

    public String sex() {
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        String sex = sp.getString("sex", "保密");
        return sex;
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