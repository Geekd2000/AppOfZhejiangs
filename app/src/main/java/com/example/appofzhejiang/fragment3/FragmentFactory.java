package com.example.appofzhejiang.fragment3;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appofzhejiang.R;
import com.example.appofzhejiang.fragment3.TicketDetail.TicketDetailActivity;
import com.example.appofzhejiang.fragment3.hotel.Hotel;
import com.example.appofzhejiang.fragment3.hotel.HotelAdapter;
import com.example.appofzhejiang.fragment3.hotel.HotelUtil;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;


public class FragmentFactory extends Fragment {

    private String mText;
    private int index;
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
        Intent intent = getActivity().getIntent();
        final String city = intent.getStringExtra("city");
        final int index = this.index;
        View v = inflater.inflate(R.layout.recyclerview, container, false);
        refreshLayout = v.findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));//设置Header样式
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));//设置Footer样式
        //配置RecyclerView
        RecyclerView recyclerView = v.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        if (index == 0) {
            adapter = new HotelAdapter(new HotelUtil(city, TicketType.TICKET).getHotelList(), getActivity(), TicketType.TICKET);
            recyclerView.setAdapter(adapter);
            //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
            adapter.setOnItemClickListener(new HotelAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(View view, Hotel hotel) {
                    //此处进行监听事件的业务处理
                    Intent intent = new Intent(getActivity(), TicketDetailActivity.class);
                    String s = Integer.toString(index);
                    intent.putExtra("product_id",Integer.toString(hotel.getProduct_id()));
                    intent.putExtra("index",s);
                    intent.putExtra("company", "全城旅游");
                    intent.putExtra("image", hotel.getPath());
                    startActivity(intent);
                }
            });
            //下拉刷新
            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    //在这里执行上拉刷新时的具体操作(网络请求、更新UI等)
                    adapter.refresh(new HotelUtil(city, TicketType.TICKET).getHotelList());
                    refreshLayout.finishRefresh();//结束刷新  传入false表示刷新失败
                    //不传时间则立即停止刷新
                }
            });
            //上拉加载
            refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                    //模拟网络请求到的数据
                    refreshLayout.finishLoadMore(2000/*false*/);//延迟2000毫秒后结束加载  传入false表示刷新失败
                    refreshLayout.finishLoadMoreWithNoMoreData();//完成加载并标记没有更多数据 1.0.4
                }
            });
            return v;
        } else if (index == 1) {
            adapter = new HotelAdapter(new HotelUtil(city, TicketType.HOTEL).getHotelList(), getActivity(), TicketType.HOTEL);
            recyclerView.setAdapter(adapter);
            //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
            adapter.setOnItemClickListener(new HotelAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(View view, Hotel hotel) {
                    //此处进行监听事件的业务处理
                    Intent intent = new Intent(getActivity(), TicketDetailActivity.class);
                    String s = Integer.toString(index);
                    intent.putExtra("product_id",Integer.toString(hotel.getProduct_id()));
                    intent.putExtra("index", s);
                    intent.putExtra("company", "钱江酒店集团");
                    intent.putExtra("image", hotel.getPath());
                    startActivity(intent);
                }
            });
            //下拉刷新
            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    //在这里执行上拉刷新时的具体操作(网络请求、更新UI等)
                    adapter.refresh(new HotelUtil(city, TicketType.HOTEL).getHotelList());
                    refreshLayout.finishRefresh();//结束刷新  传入false表示刷新失败
                    //不传时间则立即停止刷新
                }
            });
            //上拉加载
            refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                    //模拟网络请求到的数据

                    refreshLayout.finishLoadMore(2000/*false*/);//延迟2000毫秒后结束加载  传入false表示刷新失败
                    refreshLayout.finishLoadMoreWithNoMoreData();//完成加载并标记没有更多数据 1.0.4
                }
            });
            return v;
        } else if (index == 2) {
            adapter = new HotelAdapter(new HotelUtil(city, TicketType.TAXI).getHotelList(), getActivity(), TicketType.TAXI);
            recyclerView.setAdapter(adapter);
            //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
            adapter.setOnItemClickListener(new HotelAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(View view, Hotel hotel) {
                    //此处进行监听事件的业务处理
                    Intent intent = new Intent(getActivity(), TicketDetailActivity.class);
                    String s = Integer.toString(index);
                    intent.putExtra("product_id",Integer.toString(hotel.getProduct_id()));
                    intent.putExtra("index", s);
                    intent.putExtra("company", "八戒租车集团");
                    intent.putExtra("image", hotel.getPath());
                    startActivity(intent);
                }
            });
            //下拉刷新
            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    //在这里执行上拉刷新时的具体操作(网络请求、更新UI等)
                    adapter.refresh(new HotelUtil(city, TicketType.TAXI).getHotelList());
                    refreshLayout.finishRefresh();//结束刷新  传入false表示刷新失败
                    //不传时间则立即停止刷新
                }
            });
            //上拉加载
            refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                    //模拟网络请求到的数据

                    refreshLayout.finishLoadMore(2000/*false*/);//延迟2000毫秒后结束加载  传入false表示刷新失败
                    refreshLayout.finishLoadMoreWithNoMoreData();//完成加载并标记没有更多数据 1.0.4
                }
            });
            return v;
        } else if (index == 3) {
            adapter = new HotelAdapter(new HotelUtil(city, TicketType.GUIDER).getHotelList(), getActivity(), TicketType.GUIDER);
            recyclerView.setAdapter(adapter);
            //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
            adapter.setOnItemClickListener(new HotelAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(View view, Hotel hotel) {
                    //此处进行监听事件的业务处理
                    Intent intent = new Intent(getActivity(), TicketDetailActivity.class);
                    String s = Integer.toString(index);
                    intent.putExtra("product_id",Integer.toString(hotel.getProduct_id()));
                    intent.putExtra("index", s);
                    intent.putExtra("company", "百事通旅行社");
                    intent.putExtra("image", hotel.getPath());
                    startActivity(intent);
                }
            });
            //下拉刷新
            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    //在这里执行上拉刷新时的具体操作(网络请求、更新UI等)
                    adapter.refresh(new HotelUtil(city, TicketType.GUIDER).getHotelList());
                    refreshLayout.finishRefresh();//结束刷新  传入false表示刷新失败
                    //不传时间则立即停止刷新
                }
            });
            //上拉加载
            refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                    //模拟网络请求到的数据

                    refreshLayout.finishLoadMore(2000/*false*/);//延迟2000毫秒后结束加载  传入false表示刷新失败
                    refreshLayout.finishLoadMoreWithNoMoreData();//完成加载并标记没有更多数据 1.0.4
                }
            });
            return v;
        } else if (index == 4) {
            adapter = new HotelAdapter(new HotelUtil(city, TicketType.FARM).getHotelList(), getActivity(), TicketType.FARM);
            recyclerView.setAdapter(adapter);
            //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
            adapter.setOnItemClickListener(new HotelAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(View view, Hotel hotel) {
                    //此处进行监听事件的业务处理
                    Intent intent = new Intent(getActivity(), TicketDetailActivity.class);
                    String s = Integer.toString(index);
                    intent.putExtra("product_id",Integer.toString(hotel.getProduct_id()));
                    intent.putExtra("index", s);
                    intent.putExtra("company", "钱江农家乐集团");
                    intent.putExtra("image", hotel.getPath());
                    startActivity(intent);
                }
            });
            //下拉刷新
            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    //在这里执行上拉刷新时的具体操作(网络请求、更新UI等)
                    adapter.refresh(new HotelUtil(city, TicketType.FARM).getHotelList());
                    refreshLayout.finishRefresh();//结束刷新  传入false表示刷新失败
                    //不传时间则立即停止刷新
                }
            });
            //上拉加载
            refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                    //模拟网络请求到的数据

                    refreshLayout.finishLoadMore(2000/*false*/);//延迟2000毫秒后结束加载  传入false表示刷新失败
                    refreshLayout.finishLoadMoreWithNoMoreData();//完成加载并标记没有更多数据 1.0.4
                }
            });
            return v;
        } else if (index == 5) {
            adapter = new HotelAdapter(new HotelUtil(city, TicketType.FOOD).getHotelList(), getActivity(), TicketType.FOOD);
            recyclerView.setAdapter(adapter);
            //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
            adapter.setOnItemClickListener(new HotelAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(View view, Hotel hotel) {
                    //此处进行监听事件的业务处理
                    Intent intent = new Intent(getActivity(), TicketDetailActivity.class);
                    String s = Integer.toString(index);
                    intent.putExtra("product_id",Integer.toString(hotel.getProduct_id()));
                    intent.putExtra("index", s);
                    intent.putExtra("company", "钱江美食集团");
                    intent.putExtra("image", hotel.getPath());
                    startActivity(intent);
                }
            });
            //下拉刷新
            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    //在这里执行上拉刷新时的具体操作(网络请求、更新UI等)
                    adapter.refresh(new HotelUtil(city, TicketType.FOOD).getHotelList());
                    refreshLayout.finishRefresh();//结束刷新  传入false表示刷新失败
                    //不传时间则立即停止刷新
                }
            });
            //上拉加载
            refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                    //模拟网络请求到的数据

                    refreshLayout.finishLoadMore(2000/*false*/);//延迟2000毫秒后结束加载  传入false表示刷新失败
                    refreshLayout.finishLoadMoreWithNoMoreData();//完成加载并标记没有更多数据 1.0.4
                }
            });
            return v;
        } else if (index == 6) {
            adapter = new HotelAdapter(new HotelUtil(city, TicketType.PRODUCT).getHotelList(), getActivity(), TicketType.PRODUCT);
            recyclerView.setAdapter(adapter);
            //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
            adapter.setOnItemClickListener(new HotelAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(View view, Hotel hotel) {
                    //此处进行监听事件的业务处理
                    Intent intent = new Intent(getActivity(), TicketDetailActivity.class);
                    String s = Integer.toString(index);
                    intent.putExtra("product_id",Integer.toString(hotel.getProduct_id()));
                    intent.putExtra("index", s);
                    intent.putExtra("company", "钱江特产集团");
                    intent.putExtra("image", hotel.getPath());
                    startActivity(intent);
                }
            });
            //下拉刷新
            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    //在这里执行上拉刷新时的具体操作(网络请求、更新UI等)
                    adapter.refresh(new HotelUtil(city, TicketType.PRODUCT).getHotelList());
                    refreshLayout.finishRefresh();//结束刷新  传入false表示刷新失败
                    //不传时间则立即停止刷新
                }
            });
            //上拉加载
            refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                    //模拟网络请求到的数据

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
}
