package com.example.appofzhejiang;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appofzhejiang.Business.ReceiptActivity;
import com.example.appofzhejiang.Login.LoginActivity;
import com.example.appofzhejiang.Setting.SettingActivity;
import com.example.appofzhejiang.StatusBarUtil.DensityUtil;
import com.example.appofzhejiang.StatusBarUtil.StatusBarUtil;
import com.example.appofzhejiang.pay.PayActivity;
import com.example.appofzhejiang.us.AboutUsActivity;
import com.example.appofzhejiang.us.NotesActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyFragment4 extends Fragment {

    private String content;
    private View view;
    private TextView txtAddress, txtOrder, txtSetting, txtAbout, txtUsername,
            txtTobePaid, txtPaid, txtFinish,txtService;
    private CircleImageView imageUser;
    private Boolean isLoginStatus;
    public String username;
    private Dialog mShareDialog;

    public MyFragment4(){}
    public MyFragment4(String content) {
        this.content = content;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my4, container, false);
        txtAddress = view.findViewById(R.id.txt_address);
        txtOrder = view.findViewById(R.id.txt_order);
        txtSetting = view.findViewById(R.id.txt_setting);
        txtAbout = view.findViewById(R.id.txt_about);
        txtUsername = view.findViewById(R.id.user_name);
        imageUser = view.findViewById(R.id.user_image_my_self);
        txtTobePaid = view.findViewById(R.id.txt_tobePaid);
        txtPaid = view.findViewById(R.id.txt_paid);
        txtFinish = view.findViewById(R.id.txt_finished);
        txtService = view.findViewById(R.id.txt_service);

        //底部弹出对话框
        txtService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();// 单击按钮后 调用显示视图的 showDialog 方法
            }
        });

        //跳转至收货信息界面
        txtAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                isLoginStatus = sp.getBoolean("isLogin", false);
                if (isLoginStatus == true) {
                    Intent intent = new Intent(getActivity(), ReceiptActivity.class);
                    intent.putExtra("code","10");
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        });

        //跳转至订单管理界面
        txtOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                isLoginStatus = sp.getBoolean("isLogin", false);
                if (isLoginStatus == true){
                    Intent intent = new Intent(getActivity(), PayActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        });

        //已完成订单
        txtFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                isLoginStatus = sp.getBoolean("isLogin", false);
                if (isLoginStatus == true) {
                    Intent intent = new Intent(getActivity(), PayActivity.class);
                    intent.putExtra("id", 3);    //在这里传递参数
                    getActivity().startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        });

        //已付款订单
        txtPaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                isLoginStatus = sp.getBoolean("isLogin", false);
                if (isLoginStatus == true) {
                    Intent intent = new Intent(getActivity(), PayActivity.class);
                    intent.putExtra("id", 2);    //在这里传递参数
                    getActivity().startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        });

        //待付款订单
        txtTobePaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                isLoginStatus = sp.getBoolean("isLogin", false);
                if (isLoginStatus == true) {
                    Intent intent = new Intent(getActivity(), PayActivity.class);
                    intent.putExtra("id", 1);    //在这里传递参数
                    getActivity().startActivity(intent);
                }
                else{
                    Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        });
        //跳转至设置界面
        txtSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });
        txtAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AboutUsActivity.class);
                startActivity(intent);
            }
        });

        //跳转至登陆界面
        txtUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        //跳转至登陆界面
        imageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        LoginStatus();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        LoginStatus();
    }

    private void showDialog() {
        if (mShareDialog == null) {
            initShareDialog();
        }
        mShareDialog.show();
    }

    /**
     * 初始化分享弹出框
     */
    private void initShareDialog() {
        mShareDialog = new Dialog(getActivity(), R.style.dialog_bottom_full);
        mShareDialog.setCanceledOnTouchOutside(true); //手指触碰到外界取消
        mShareDialog.setCancelable(true);             //可取消 为true
        Window window = mShareDialog.getWindow();      // 得到dialog的窗体
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.share_animation);

        View view = View.inflate(getActivity(), R.layout.dialog_service, null); //获取布局视图
        view.findViewById(R.id.know).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mShareDialog != null && mShareDialog.isShowing()) {
                    mShareDialog.dismiss();
                }
            }
        });
        window.setContentView(view);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);//设置横向全屏
    }

    /**
     * 判断登录状态
     */
    public void LoginStatus() {
        SharedPreferences sp = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        isLoginStatus = sp.getBoolean("isLogin", false);
        if (isLoginStatus == true) {
            txtUsername.setText(sp.getString("loginNickname", null));
            txtUsername.setEnabled(false);
            imageUser.setEnabled(false);
        }
    }
}