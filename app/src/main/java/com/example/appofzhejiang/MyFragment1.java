package com.example.appofzhejiang;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appofzhejiang.CustomDialog.CustomDialog;

public class MyFragment1 extends Fragment {

    private View view; //定义view用来设置fragment的layout
    public RecyclerView recyclerView; //定义RecyclerView
    private FragmentAdapter1 fragmentAdapter1;  //自定义RecyclerView的适配器
    private String content;
    private TextView txtCity;

    public MyFragment1(String content) {
        this.content = content;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my1, container, false);
        txtCity = view.findViewById(R.id.txt_city);
        txtCity.setText(content);
        //定位弹窗
        txtCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog customDialog = new CustomDialog(getActivity());
                customDialog.setCancel("取消", new CustomDialog.IOnCancelListener() {
                    @Override
                    public void onCancel(CustomDialog dialog) {
                    }
                }).setConfirm("确定", new CustomDialog.IOnConfirmListener() {
                    @Override
                    public void onConfirm(CustomDialog dialog) {
                    }
                }).show();
            }
        });
        initRecyclerView();
        return view;
    }

    /**
     * 对RecycleView进行配置
     */
    private void initRecyclerView() {
        //获取RecyclerView
        recyclerView = view.findViewById(R.id.fg1_recyclerView);
        //创建Adapter
        fragmentAdapter1 = new FragmentAdapter1(getActivity());
        //给RecyclerView设置Adapter
        recyclerView.setAdapter(fragmentAdapter1);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置item的分割线
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    }
}