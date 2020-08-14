package com.example.appofzhejiang;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appofzhejiang.Login.LoginActivity;
import com.example.appofzhejiang.Setting.SettingActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyFragment4 extends Fragment {

    private String content;
    private View view;
    private TextView txtAddress, txtOrder, txtSetting, txtAbout, txtUsername;
    private CircleImageView imageUser;

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
        imageUser = view.findViewById(R.id.user_image);
        txtAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        txtAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        txtOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
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
            }
        });
        txtUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        imageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}