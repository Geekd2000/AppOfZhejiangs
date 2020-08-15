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
 * Use the {@link OrderFragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment2 extends Fragment {

    private View view;
    public RecyclerView recyclerView;
    private OrderAdapter orderAdapter;
    private List<FileList> fileLists = new ArrayList<FileList>();
    private String content;

    public OrderFragment2(String content){
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_order2, container, false);
        initData();
        initRecyclerView();
        return view;
    }

    private void initData(){
//        for(int i = 0; i<fileLists.size(); i++){
//            FileList fileList = new FileList();
//            fileLists.add(fileList);
//        }
    }

    private void initRecyclerView(){
        //获取recyclerview
        recyclerView = view.findViewById(R.id.fragment_order2);
        //创建Adapter
        orderAdapter = new OrderAdapter(getActivity());
        recyclerView.setAdapter(orderAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}