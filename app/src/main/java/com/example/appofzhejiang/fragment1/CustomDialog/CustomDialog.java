package com.example.appofzhejiang.fragment1.CustomDialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.appofzhejiang.R;

public class CustomDialog extends Dialog implements View.OnClickListener {

    private TextView tvTitle, tvMessage, tvCancel, tvConfirm;

    private String title, message, cancel, confirm;

    private IOnCancelListener cancelListener;

    private IOnConfirmListener confirmListener;

    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    public CustomDialog(@NonNull Context context, int themeId) {
        super(context, themeId);
    }

    public CustomDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public CustomDialog setMessage(String message) {
        this.message = message;
        return this;
    }

    public CustomDialog setCancel(String cancel,IOnCancelListener listener) {
        this.cancel = cancel;
        this.cancelListener=listener;
        return this;
    }

    public CustomDialog setConfirm(String confirm,IOnConfirmListener listener) {
        this.confirm = confirm;
        this.confirmListener=listener;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_custom_dialog);
        //设置Dialog的宽度
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        Point size = new Point();
        d.getSize(size);
        p.width=(int)(size.x*0.8); //设置dialog的宽度为当前手机屏幕宽度的80％
        getWindow().setAttributes(p);

        tvTitle = findViewById(R.id.custom_title);
        tvMessage = findViewById(R.id.custom_message);
        tvCancel = findViewById(R.id.custom_cancel);
        tvConfirm = findViewById(R.id.custom_confirm);
        if (!TextUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
        if (!TextUtils.isEmpty(message)) {
            tvMessage.setText(message);
        }
        if (!TextUtils.isEmpty(cancel)) {
            tvCancel.setText(cancel);
        }
        if (!TextUtils.isEmpty(confirm)) {
            tvConfirm.setText(confirm);
        }
        tvCancel.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.custom_cancel:
                if(cancelListener!=null){
                    cancelListener.onCancel(this);
                    dismiss();
                }
                break;
            case R.id.custom_confirm:
                if(confirmListener!=null){
                    confirmListener.onConfirm(this);
                    dismiss();
                }
                break;
        }
    }

    public interface IOnCancelListener {
        void onCancel(CustomDialog dialog);
    }

    public interface IOnConfirmListener {
        void onConfirm(CustomDialog dialog);
    }
}