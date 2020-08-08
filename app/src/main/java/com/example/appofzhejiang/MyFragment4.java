package com.example.appofzhejiang;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MyFragment4 extends Fragment {

    private String content;
    private View view;
    private TextView txtAddress,txtOrder,txtCollection,txtMyPost,txtSetting,txtAbout;

    public MyFragment4(String content){
        this.content=content;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_my4,container,false);
        txtAddress=view.findViewById(R.id.txt_address);
        txtOrder=view.findViewById(R.id.txt_order);
        txtCollection=view.findViewById(R.id.txt_collection);
        txtMyPost=view.findViewById(R.id.txt_myPost);
        txtSetting=view.findViewById(R.id.txt_setting);
        txtAbout=view.findViewById(R.id.txt_about);
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
        txtCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        txtMyPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        txtSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        txtAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }
}