package com.example.appofzhejiang.fragment3;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.appofzhejiang.R;
import com.example.appofzhejiang.fragment3.hotel.Hotel;
import com.example.appofzhejiang.fragment3.hotel.HotelAdapter;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;


public class FragmentFactory extends Fragment {

    private String mText;
    private int index;
    private List<Hotel> hotelList;
    private LinearLayout linearLayout;
    private HotelAdapter adapter;
    private SmartRefreshLayout refreshLayout;

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
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));//设置Header样式
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));//设置Footer样式
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
            //下拉刷新
            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    //在这里执行上拉刷新时的具体操作(网络请求、更新UI等)

                    //模拟网络请求到的数据
                    ArrayList<Hotel> newList = new ArrayList<Hotel>();
                    for (int i = 0; i < 20; i++) {
                        newList.add(new Hotel(R.drawable.songcity, "杭州宋城" +i, "50", "全域旅行", "99"));
                    }
                    adapter.refresh(newList);
                    refreshLayout.finishRefresh(2000/*false*/);//延迟2000毫秒后结束刷新  传入false表示刷新失败
                    //不传时间则立即停止刷新
                }
            });
            //上拉加载
            refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                    //模拟网络请求到的数据
                    ArrayList<Hotel> newList = new ArrayList<Hotel>();
                    for (int i = 0; i < 10; i++) {
                        newList.add(new Hotel(R.drawable.songcity, "杭州宋城" + (hotelList.size() + i + 1), "50", "全域旅行", "99"));
                    }
                    adapter.add(newList);
                    refreshLayout.finishLoadMore(2000/*false*/);//延迟2000毫秒后结束加载  传入false表示刷新失败
                    refreshLayout.finishLoadMoreWithNoMoreData();//完成加载并标记没有更多数据 1.0.4
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
            //下拉刷新
            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    //在这里执行上拉刷新时的具体操作(网络请求、更新UI等)

                    //模拟网络请求到的数据
                    ArrayList<Hotel> newList = new ArrayList<Hotel>();
                    for (int i = 0; i < 20; i++) {
                        newList.add(new Hotel(R.drawable.hotel_image, "大酒店" +i, "150", "无敌连锁公司", "99"));
                    }
                    adapter.refresh(newList);
                    refreshLayout.finishRefresh(2000/*false*/);//延迟2000毫秒后结束刷新  传入false表示刷新失败
                    //不传时间则立即停止刷新
                }
            });
            //上拉加载
            refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                    //模拟网络请求到的数据
                    ArrayList<Hotel> newList = new ArrayList<Hotel>();
                    for (int i = 0; i < 10; i++) {
                        newList.add(new Hotel(R.drawable.hotel_image, "大酒店" + (hotelList.size() + i + 1), "150", "无敌连锁公司", "99"));
                    }
                    adapter.add(newList);
                    refreshLayout.finishLoadMore(2000/*false*/);//延迟2000毫秒后结束加载  传入false表示刷新失败
                    refreshLayout.finishLoadMoreWithNoMoreData();//完成加载并标记没有更多数据 1.0.4
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
            //下拉刷新
            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    //在这里执行上拉刷新时的具体操作(网络请求、更新UI等)

                    //模拟网络请求到的数据
                    ArrayList<Hotel> newList = new ArrayList<Hotel>();
                    for (int i = 0; i < 20; i++) {
                        newList.add(new Hotel(R.drawable.taxi_image, "Tesla" +i, "200", "八戒租车", "99"));
                    }
                    adapter.refresh(newList);
                    refreshLayout.finishRefresh(2000/*false*/);//延迟2000毫秒后结束刷新  传入false表示刷新失败
                    //不传时间则立即停止刷新
                }
            });
            //上拉加载
            refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                    //模拟网络请求到的数据
                    ArrayList<Hotel> newList = new ArrayList<Hotel>();
                    for (int i = 0; i < 10; i++) {
                        newList.add(new Hotel(R.drawable.taxi_image, "Tesla" + (hotelList.size() + i + 1), "200", "八戒租车", "99"));
                    }
                    adapter.add(newList);
                    refreshLayout.finishLoadMore(2000/*false*/);//延迟2000毫秒后结束加载  传入false表示刷新失败
                    refreshLayout.finishLoadMoreWithNoMoreData();//完成加载并标记没有更多数据 1.0.4
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
            //下拉刷新
            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    //在这里执行上拉刷新时的具体操作(网络请求、更新UI等)

                    //模拟网络请求到的数据
                    ArrayList<Hotel> newList = new ArrayList<Hotel>();
                    for (int i = 0; i < 20; i++) {
                        newList.add(new Hotel(R.drawable.guider_image, "导游" +i, "220", "无敌导游公司", "99"));
                    }
                    adapter.refresh(newList);
                    refreshLayout.finishRefresh(2000/*false*/);//延迟2000毫秒后结束刷新  传入false表示刷新失败
                    //不传时间则立即停止刷新
                }
            });
            //上拉加载
            refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                    //模拟网络请求到的数据
                    ArrayList<Hotel> newList = new ArrayList<Hotel>();
                    for (int i = 0; i < 10; i++) {
                        newList.add(new Hotel(R.drawable.guider_image, "导游" + (hotelList.size() + i + 1), "220", "无敌导游公司", "99"));
                    }
                    adapter.add(newList);
                    refreshLayout.finishLoadMore(2000/*false*/);//延迟2000毫秒后结束加载  传入false表示刷新失败
                    refreshLayout.finishLoadMoreWithNoMoreData();//完成加载并标记没有更多数据 1.0.4
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
            //下拉刷新
            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    //在这里执行上拉刷新时的具体操作(网络请求、更新UI等)

                    //模拟网络请求到的数据
                    ArrayList<Hotel> newList = new ArrayList<Hotel>();
                    for (int i = 0; i < 20; i++) {
                        newList.add(new Hotel(R.drawable.farmhouse, "农家乐" +i, "398", "超级无敌大公司", "99"));
                    }
                    adapter.refresh(newList);
                    refreshLayout.finishRefresh(2000/*false*/);//延迟2000毫秒后结束刷新  传入false表示刷新失败
                    //不传时间则立即停止刷新
                }
            });
            //上拉加载
            refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                    //模拟网络请求到的数据
                    ArrayList<Hotel> newList = new ArrayList<Hotel>();
                    for (int i = 0; i < 10; i++) {
                        newList.add(new Hotel(R.drawable.farmhouse, "农家乐" + (hotelList.size() + i + 1), "398", "超级无敌大公司", "99"));
                    }
                    adapter.add(newList);
                    refreshLayout.finishLoadMore(2000/*false*/);//延迟2000毫秒后结束加载  传入false表示刷新失败
                    refreshLayout.finishLoadMoreWithNoMoreData();//完成加载并标记没有更多数据 1.0.4
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
            //下拉刷新
            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    //在这里执行上拉刷新时的具体操作(网络请求、更新UI等)

                    //模拟网络请求到的数据
                    ArrayList<Hotel> newList = new ArrayList<Hotel>();
                    for (int i = 0; i < 20; i++) {
                        newList.add(new Hotel(R.drawable.longjingxiaren, "龙井虾仁" +i, "98", "诗画美食", "99"));
                    }
                    adapter.refresh(newList);
                    refreshLayout.finishRefresh(2000/*false*/);//延迟2000毫秒后结束刷新  传入false表示刷新失败
                    //不传时间则立即停止刷新
                }
            });
            //上拉加载
            refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                    //模拟网络请求到的数据
                    ArrayList<Hotel> newList = new ArrayList<Hotel>();
                    for (int i = 0; i < 10; i++) {
                        newList.add(new Hotel(R.drawable.longjingxiaren, "龙井虾仁" + (hotelList.size() + i + 1), "98", "诗画美食", "99"));
                    }
                    adapter.add(newList);
                    refreshLayout.finishLoadMore(2000/*false*/);//延迟2000毫秒后结束加载  传入false表示刷新失败
                    refreshLayout.finishLoadMoreWithNoMoreData();//完成加载并标记没有更多数据 1.0.4
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
            //下拉刷新
            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    //在这里执行上拉刷新时的具体操作(网络请求、更新UI等)

                    //模拟网络请求到的数据
                    ArrayList<Hotel> newList = new ArrayList<Hotel>();
                    for (int i = 0; i < 20; i++) {
                        newList.add(new Hotel(R.drawable.westlakelongjing, "杭州原产西湖龙井茶" +i, "198", "超级无敌大公司", "99"));
                    }
                    adapter.refresh(newList);
                    refreshLayout.finishRefresh(2000/*false*/);//延迟2000毫秒后结束刷新  传入false表示刷新失败
                    //不传时间则立即停止刷新
                }
            });
            //上拉加载
            refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                    //模拟网络请求到的数据
                    ArrayList<Hotel> newList = new ArrayList<Hotel>();
                    for (int i = 0; i < 10; i++) {
                        newList.add(new Hotel(R.drawable.westlakelongjing, "杭州原产西湖龙井茶" + (hotelList.size() + i + 1), "198", "超级无敌大公司", "99"));
                    }
                    adapter.add(newList);
                    refreshLayout.finishLoadMore(2000/*false*/);//延迟2000毫秒后结束加载  传入false表示刷新失败
                    refreshLayout.finishLoadMoreWithNoMoreData();//完成加载并标记没有更多数据 1.0.4
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
