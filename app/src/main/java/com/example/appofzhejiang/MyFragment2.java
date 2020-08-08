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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appofzhejiang.CustomDialog.CustomDialog;

public class MyFragment2 extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private FragmentAdapter2 fragmentAdapter2;
    private String content;
    private TextView txtCity,txtMore;

    public MyFragment2(String content){
        this.content=content;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_my2,container,false);
        txtCity = view.findViewById(R.id.txt_city);
        txtCity.setText(content);
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
        txtMore = view.findViewById(R.id.txt_more);
        txtMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        recyclerView = view.findViewById(R.id.fg2_grid);
        //创建Adapter
        fragmentAdapter2 = new FragmentAdapter2(getActivity());
        //给RecyclerView设置Adapter
        recyclerView.setAdapter(fragmentAdapter2);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        //设置item的分割线
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    }
}