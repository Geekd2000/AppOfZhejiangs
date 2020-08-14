package com.example.appofzhejiang.Setting;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.appofzhejiang.R;

public class PersonalInformationActivity extends AppCompatActivity {

    public TextView sexChange, mBtnSave;
    private Button back;
    private String[] sexArry = new String[]{"女", "男"};// 性别选择

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);
        mBtnSave = findViewById(R.id.save);
        sexChange = findViewById(R.id.sex_change);
        back = findViewById(R.id.order_back);
        OnClick onClick = new OnClick();
        sexChange.setOnClickListener(onClick);
        mBtnSave.setOnClickListener(onClick);
        back.setOnClickListener(onClick);
    }

    class OnClick implements View.OnClickListener {

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
                case R.id.order_back:
                    finish();
                    break;
                case R.id.save:
                    break;
            }
        }
    }

}