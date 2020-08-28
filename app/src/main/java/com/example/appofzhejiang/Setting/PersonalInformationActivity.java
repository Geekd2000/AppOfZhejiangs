package com.example.appofzhejiang.Setting;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.appofzhejiang.MainActivity;
import com.example.appofzhejiang.R;
import com.example.appofzhejiang.StatusBarUtil.StatusBarUtil;

public class PersonalInformationActivity extends AppCompatActivity {

    private TextView sexChange, mBtnSave;
    private Toolbar mBack;
    private String[] sexArry = new String[]{"保密", "男", "女"};// 性别选择
    private TextView passwordChange;
    private EditText username;


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
        //设置沉浸式
        StatusBarUtil.setTransparent(this);
        StatusBarUtil.setDarkFont(this);

        OnClick onClick = new OnClick();

        sexChange.setOnClickListener(onClick);
        mBtnSave.setOnClickListener(onClick);
        mBack.setOnClickListener(onClick);
        passwordChange.setOnClickListener(onClick);

        username.setText(save());
        sexChange.setText(sex());

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.right_silent,R.anim.right_out);
    }

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
                    startActivity(new Intent(PersonalInformationActivity.this, MainActivity.class));
                    break;

            }
        }
    }

    public String save() {
        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        String username = sp.getString("loginUserName", null);
        return username;
    }

    public void changeName(String name, String sex) {

        SharedPreferences sp = getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        String oldName = sp.getString("loginUserName", null);
        String password = sp.getString(oldName, "");
        String oldSex = sp.getString("sex", null);

        //更新存储数据的用户名
        editor.remove(oldName);
        editor.putString(name, password);

        //更新登陆状态的用户名
        editor.remove("loginUserName");
        editor.putString("loginUserName", name);

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

}