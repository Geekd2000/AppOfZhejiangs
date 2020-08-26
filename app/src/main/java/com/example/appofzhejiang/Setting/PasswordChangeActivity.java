package com.example.appofzhejiang.Setting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.appofzhejiang.Login.MD5Utils;
import com.example.appofzhejiang.MainActivity;
import com.example.appofzhejiang.R;
import com.example.appofzhejiang.StatusBarUtil.StatusBarUtil;

public class PasswordChangeActivity extends AppCompatActivity {

    private Button mBtnChange;
    private EditText old_password, new_password, new_password_again;
    private Toolbar mBtnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

    public void BindView() {
        Onclick onclick = new Onclick();
        mBtnBack.setOnClickListener(onclick);
        mBtnChange.setOnClickListener(onclick);
    }

    private void submit(){
        String psw = old_password.getText().toString().trim();
        String newPsw = new_password.getText().toString().trim();
        String again = new_password_again.getText().toString().trim();

        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);

        String username = sp.getString("loginUserName",null);

        if (TextUtils.isEmpty(psw)) {
            Toast.makeText(PasswordChangeActivity.this, "请输入原始密码", Toast.LENGTH_SHORT).show();
            return;
        } else if (!MD5Utils.md5(psw).equals(readPsw())) {
            Log.i("MD5Utils.md5(psw)", "" + MD5Utils.md5(psw));
            Log.i("readPsw", "" + readPsw());
            Toast.makeText(PasswordChangeActivity.this, "输入的密码与原始密码不一致", Toast.LENGTH_SHORT).show();
            return;
        } else if (MD5Utils.md5(newPsw).equals(readPsw())) {
            Toast.makeText(PasswordChangeActivity.this, "输入的新密码与原始密码不能一致", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(psw)) {
            Toast.makeText(PasswordChangeActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(again)) {
            Toast.makeText(PasswordChangeActivity.this, "请再次输入新密码", Toast.LENGTH_SHORT).show();
        } else if (!newPsw.equals(again)) {
            Toast.makeText(PasswordChangeActivity.this, "再次输入的新密码不一致", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Toast.makeText(PasswordChangeActivity.this, "新密码设置成功,请重新登录", Toast.LENGTH_SHORT).show();
            //存入新密码
            modifyPsw();
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
            startActivity(new Intent(PasswordChangeActivity.this, MainActivity.class));
        }
    }

    public void Out(Boolean status){
        SharedPreferences sp = getSharedPreferences("loginInfo",MODE_PRIVATE);
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
        String username = sp.getString("loginUserName",null);
        String spPsw = sp.getString(username, "");
        Log.i("username", username);
        Log.i("spPsw", spPsw);
        return spPsw;

    }

    private void modifyPsw() {
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);

        String username = sp.getString("loginUserName",null);

        String newPsw = new_password.getText().toString().trim();

        String md5psw = MD5Utils.md5(newPsw);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(username, md5psw);
        editor.commit();

    }

}