package com.example.appofzhejiang.fragment3;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.appofzhejiang.R;
import com.example.appofzhejiang.fragment3.hotel.Hotel;
import com.example.appofzhejiang.fragment3.hotel.HotelAdapter;

import java.util.ArrayList;
import java.util.List;


public class FragmentFactory extends Fragment {

    private String mText;
    private int index;
    private List<Hotel> hotelList;
    private LinearLayout linearLayout;
    private HotelAdapter adapter;
    private SwipeRefreshLayout refreshLayout;

    private final String mText_key = "FragmentFactory";

    public static FragmentFactory newInstance(int index) {
        FragmentFactory mFragmentTest = new FragmentFactory();
        mFragmentTest.index = index;
        return mFragmentTest;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(mText_key)) {
            mText = savedInstanceState.getString(mText_key);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final int index = this.index;
        View v = inflater.inflate(R.layout.recyclerview, container, false);
        refreshLayout = v.findViewById(R.id.refreshLayout);
        refreshLayout.setColorSchemeResources(R.color.colorLightGreen);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                /*if (refreshLayout.isRefreshing()) {
                    refreshLayout.setRefreshing(false);
                }*/
                //模拟网络请求需要3000毫秒，请求完成，设置setRefreshing 为false
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });
        if (index == 0) {
            initTickets();
            RecyclerView recyclerView = v.findViewById(R.id.recycler_view);
            LinearLayoutManager layoutManager = new LinearLayoutManager(new TicketActivity());
            recyclerView.setLayoutManager(layoutManager);
            adapter = new HotelAdapter(hotelList, getActivity());
            recyclerView.setAdapter(adapter);
            //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
            adapter.setOnItemClickListener(new HotelAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(View view, Hotel hotel) {
                    //此处进行监听事件的业务处理
                    Intent intent = new Intent(getActivity(), TicketDetailActivity.class);
                    String s = Integer.toString(index);
                    String img = Integer.toString(hotel.getImageId());
                    intent.putExtra("index", s);
                    intent.putExtra("title", hotel.getName());
                    intent.putExtra("price", hotel.getPrice());
                    intent.putExtra("company", hotel.getLocation());
                    intent.putExtra("count", hotel.getCount());
                    intent.putExtra("image", img);
                    startActivity(intent);
                }
            });
            return v;
        } else if (index == 1) {
            initHotels();
            RecyclerView recyclerView = v.findViewById(R.id.recycler_view);
            LinearLayoutManager layoutManager = new LinearLayoutManager(new TicketActivity());
            recyclerView.setLayoutManager(layoutManager);
            adapter = new HotelAdapter(hotelList, getActivity());
            recyclerView.setAdapter(adapter);
            //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
            adapter.setOnItemClickListener(new HotelAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(View view, Hotel hotel) {
                    //此处进行监听事件的业务处理
                    Intent intent = new Intent(getActivity(), TicketDetailActivity.class);
                    String s = Integer.toString(index);
                    String img = Integer.toString(hotel.getImageId());
                    intent.putExtra("index", s);
                    intent.putExtra("title", hotel.getName());
                    intent.putExtra("price", hotel.getPrice());
                    intent.putExtra("company", hotel.getLocation());
                    intent.putExtra("count", hotel.getCount());
                    intent.putExtra("image", img);
                    startActivity(intent);
                }
            });
            return v;
        } else if (index == 2) {
            initTaxis();
            RecyclerView recyclerView = v.findViewById(R.id.recycler_view);
            LinearLayoutManager layoutManager = new LinearLayoutManager(new TicketActivity());
            recyclerView.setLayoutManager(layoutManager);
            adapter = new HotelAdapter(hotelList, getActivity());
            recyclerView.setAdapter(adapter);
            //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
            adapter.setOnItemClickListener(new HotelAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(View view, Hotel hotel) {
                    //此处进行监听事件的业务处理
                    Intent intent = new Intent(getActivity(), TicketDetailActivity.class);
                    String s = Integer.toString(index);
                    String img = Integer.toString(hotel.getImageId());
                    intent.putExtra("index", s);
                    intent.putExtra("title", hotel.getName());
                    intent.putExtra("price", hotel.getPrice());
                    intent.putExtra("company", hotel.getLocation());
                    intent.putExtra("count", hotel.getCount());
                    intent.putExtra("image", img);
                    startActivity(intent);
                }
            });
            return v;
        } else if (index == 3) {
            initGuiders();
            RecyclerView recyclerView = v.findViewById(R.id.recycler_view);
            LinearLayoutManager layoutManager = new LinearLayoutManager(new TicketActivity());
            recyclerView.setLayoutManager(layoutManager);
            adapter = new HotelAdapter(hotelList, getActivity());
            recyclerView.setAdapter(adapter);
            //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
            adapter.setOnItemClickListener(new HotelAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(View view, Hotel hotel) {
                    //此处进行监听事件的业务处理
                    Intent intent = new Intent(getActivity(), TicketDetailActivity.class);
                    String s = Integer.toString(index);
                    String img = Integer.toString(hotel.getImageId());
                    intent.putExtra("index", s);
                    intent.putExtra("title", hotel.getName());
                    intent.putExtra("price", hotel.getPrice());
                    intent.putExtra("company", hotel.getLocation());
                    intent.putExtra("count", hotel.getCount());
                    intent.putExtra("image", img);
                    startActivity(intent);
                }
            });
            return v;
        } else if (index == 4) {
            initFarmhouses();
            RecyclerView recyclerView = v.findViewById(R.id.recycler_view);
            LinearLayoutManager layoutManager = new LinearLayoutManager(new TicketActivity());
            recyclerView.setLayoutManager(layoutManager);
            adapter = new HotelAdapter(hotelList, getActivity());
            recyclerView.setAdapter(adapter);
            //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
            adapter.setOnItemClickListener(new HotelAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(View view, Hotel hotel) {
                    //此处进行监听事件的业务处理
                    Intent intent = new Intent(getActivity(), TicketDetailActivity.class);
                    String s = Integer.toString(index);
                    String img = Integer.toString(hotel.getImageId());
                    intent.putExtra("index", s);
                    intent.putExtra("title", hotel.getName());
                    intent.putExtra("price", hotel.getPrice());
                    intent.putExtra("company", hotel.getLocation());
                    intent.putExtra("count", hotel.getCount());
                    intent.putExtra("image", img);
                    startActivity(intent);
                }
            });
            return v;
        } else if (index == 5) {
            initFoods();
            RecyclerView recyclerView = v.findViewById(R.id.recycler_view);
            LinearLayoutManager layoutManager = new LinearLayoutManager(new TicketActivity());
            recyclerView.setLayoutManager(layoutManager);
            adapter = new HotelAdapter(hotelList, getActivity());
            recyclerView.setAdapter(adapter);
            //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
            adapter.setOnItemClickListener(new HotelAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(View view, Hotel hotel) {
                    //此处进行监听事件的业务处理
                    Intent intent = new Intent(getActivity(), TicketDetailActivity.class);
                    String s = Integer.toString(index);
                    String img = Integer.toString(hotel.getImageId());
                    intent.putExtra("index", s);
                    intent.putExtra("title", hotel.getName());
                    intent.putExtra("price", hotel.getPrice());
                    intent.putExtra("company", hotel.getLocation());
                    intent.putExtra("count", hotel.getCount());
                    intent.putExtra("image", img);
                    startActivity(intent);
                }
            });
            return v;
        } else if (index == 6) {
            initShopping();
            RecyclerView recyclerView = v.findViewById(R.id.recycler_view);
            LinearLayoutManager layoutManager = new LinearLayoutManager(new TicketActivity());
            recyclerView.setLayoutManager(layoutManager);
            adapter = new HotelAdapter(hotelList, getActivity());
            recyclerView.setAdapter(adapter);
            //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
            adapter.setOnItemClickListener(new HotelAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(View view, Hotel hotel) {
                    //此处进行监听事件的业务处理
                    Intent intent = new Intent(getActivity(), TicketDetailActivity.class);
                    String s = Integer.toString(index);
                    String img = Integer.toString(hotel.getImageId());
                    intent.putExtra("index", s);
                    intent.putExtra("title", hotel.getName());
                    intent.putExtra("price", hotel.getPrice());
                    intent.putExtra("company", hotel.getLocation());
                    intent.putExtra("count", hotel.getCount());
                    intent.putExtra("image", img);
                    startActivity(intent);
                }
            });
            return v;
        } else {
            return null;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putString(mText_key, mText);
    }

    public void initTickets() {
        hotelList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Hotel hotel = new Hotel(R.drawable.songcheng, "杭州宋城门票+千古情演出" + (i + 1), "50", "全域旅行", "666");
            hotelList.add(hotel);
        }
    }

    public void initHotels() {
        hotelList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Hotel hotel = new Hotel(R.drawable.hotel_image, "快乐大酒店" + (i + 1), "120", "无敌连锁公司", "999");
            hotelList.add(hotel);
        }
    }

    public void initTaxis() {
        hotelList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Hotel hotel = new Hotel(R.drawable.taxi_image, "大众" + (i + 1), "198", "八戒租车", "555");
            hotelList.add(hotel);
        }
    }

    public void initGuiders() {
        hotelList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Hotel hotel = new Hotel(R.drawable.guider_image, "杭州西湖导游" + (i + 1), "200", "无敌导游公司", "333");
            hotelList.add(hotel);
        }
    }

    public void initFarmhouses() {
        hotelList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Hotel hotel = new Hotel(R.drawable.farmhouse, "梅开小筑客栈" + (i + 1), "398", "超级无敌大公司", "222");
            hotelList.add(hotel);
        }
    }

    public void initFoods() {
        hotelList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Hotel hotel = new Hotel(R.drawable.longjingxiaren, "龙井虾仁" + (i + 1), "98", "诗画美食", "888");
            hotelList.add(hotel);
        }
    }

    public void initShopping() {
        hotelList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Hotel hotel = new Hotel(R.drawable.westlakelongjing, "杭州原产西湖龙井茶" + (i + 1), "198", "超级无敌大公司", "444");
            hotelList.add(hotel);
        }
    }
}
