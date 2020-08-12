package com.example.appofzhejiang;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.example.appofzhejiang.CustomDialog.CustomDialog;
import com.example.appofzhejiang.R;
import com.example.appofzhejiang.fragment3.IndicatorTabBar;
import com.example.appofzhejiang.fragment3.Ticket;
import com.example.appofzhejiang.fragment3.TicketActivity;
import com.example.appofzhejiang.fragment3.TicketDetailActivity;
import com.example.appofzhejiang.fragment3.hotel.Hotel;
import com.example.appofzhejiang.fragment3.hotel.HotelAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyFragment3 extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private FragmentAdapter3 fragmentAdapter3;
    private String content;
    private TextView txtCity, txtTicket, txtHotel, txtTaxi, txtGuider, txtNongjiale, txtFood, txtCommodity,txtSearch;
    private EditText editSearch;
    private List<Ticket> ticketList;

    public MyFragment3(String content) {
        this.content = content;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my3, container, false);
        init();
        RecyclerView recyclerView = view.findViewById(R.id.fg3_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(new MainActivity());
        recyclerView.setLayoutManager(layoutManager);
        fragmentAdapter3 = new FragmentAdapter3(ticketList, getActivity());
        recyclerView.setAdapter(fragmentAdapter3);
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
        txtTicket = view.findViewById(R.id.txt_ticket);
        txtTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TicketActivity.class);
                intent.putExtra("num", "0");
                startActivity(intent);
            }
        });
        txtHotel = view.findViewById(R.id.txt_hotel);
        txtHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TicketActivity.class);
                intent.putExtra("num", "1");
                startActivity(intent);
            }
        });
        txtTaxi = view.findViewById(R.id.txt_taxi);
        txtTaxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TicketActivity.class);
                intent.putExtra("num", "2");
                startActivity(intent);
            }
        });
        txtGuider = view.findViewById(R.id.txt_guider);
        txtGuider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TicketActivity.class);
                intent.putExtra("num", "3");
                startActivity(intent);
            }
        });
        txtNongjiale = view.findViewById(R.id.txt_nongjiale);
        txtNongjiale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TicketActivity.class);
                intent.putExtra("num", "4");
                startActivity(intent);
            }
        });
        txtFood = view.findViewById(R.id.txt_food);
        txtFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TicketActivity.class);
                intent.putExtra("num", "5");
                startActivity(intent);
            }
        });
        txtCommodity = view.findViewById(R.id.txt_commodity);
        txtCommodity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TicketActivity.class);
                intent.putExtra("num", "6");
                startActivity(intent);
            }
        });
        txtSearch = view.findViewById(R.id.txt_search);
        editSearch = view.findViewById(R.id.main_search);
        txtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s=editSearch.getText().toString();
                Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
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
        recyclerView = view.findViewById(R.id.fg3_recyclerView);
        //创建Adapter
        fragmentAdapter3 = new FragmentAdapter3(ticketList,getActivity());
        //给RecyclerView设置Adapter
        recyclerView.setAdapter(fragmentAdapter3);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置item的分割线
        //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
    }
    public void init() {
        ticketList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Ticket ticket = new Ticket(R.drawable.westlake, "杭州西湖"+i, "35", "超级无敌大公司", "666");
            ticketList.add(ticket);
        }
    }
}