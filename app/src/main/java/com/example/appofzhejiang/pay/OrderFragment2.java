package com.example.appofzhejiang.pay;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appofzhejiang.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderFragment2#} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment2 extends Fragment {

    private View view;
    public RecyclerView recyclerView;
    private PayAdapter payAdapter;
    private List<FileList> fileLists = new ArrayList<FileList>();
    private String content;

    public OrderFragment2() {
    }

    public OrderFragment2(String content) {
        this.content=content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_order2, container, false);
        initData();
        initList();
        initRecyclerView();
        return view;
    }

    private void initData() {
//        for(int i = 0; i<fileLists.size(); i++){
//            FileList fileList = new FileList();
//            fileLists.add(fileList);
//        }
    }

    private void initRecyclerView() {
        //获取recyclerview
        recyclerView = view.findViewById(R.id.fragment_order2);
        //创建Adapter
        payAdapter = new PayAdapter(getActivity(), fileLists);
        recyclerView.setAdapter(payAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void initList() {
        for (int i = 0; i < 10; i++) {
            FileList fileList = new FileList(R.drawable.picturezhejiang, "订单号" + i, "西湖游船外事船舶成人票", "小船", "3", "35", "105", "待付款");
            fileLists.add(fileList);
        }
    }
}