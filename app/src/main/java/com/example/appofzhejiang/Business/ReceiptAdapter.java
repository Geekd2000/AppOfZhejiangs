package com.example.appofzhejiang.Business;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appofzhejiang.R;
import com.example.appofzhejiang.fragment3.Ticket;
import com.example.appofzhejiang.fragment3.TicketDetail.TicketDetailActivity;
import com.example.appofzhejiang.fragment3.hotel.Hotel;
import com.example.appofzhejiang.fragment3.hotel.HotelAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.LinearViewHolder> {

    private Context mContext;
    private List<AddressList> addressData;//收货信息
    private AddressList addressList;

    public ReceiptAdapter(Context context, List<AddressList> addressData, AddressList addressList) {
        this.mContext = context;
        this.addressData = addressData;
        this.addressList = addressList;
    }

    @NonNull
    @Override
    public LinearViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_receipt, parent, false);
        final LinearViewHolder linearViewHolder = new LinearViewHolder(view);
        return linearViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, final int position) {
        if ((addressData.get(position).getSelect()).equals("false")) {
            holder.toleration.setVisibility(View.INVISIBLE);
        } else if ((addressData.get(position).getSelect()).equals("true")) {
            holder.toleration.setVisibility(View.VISIBLE);
        }
        holder.username.setText(addressData.get(position).getUsername());
        holder.phone.setText(addressData.get(position).getPhone());
        holder.address.setText(addressData.get(position).getAddress());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //删除自带默认动画
                removeData(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressData.size();
    }

    // 添加数据
    public void addData(int position) {
        //在list中添加数据，并通知条目加入一条
        addressData.add(position, addressList);
        //添加动画
        notifyItemInserted(position);
    }

    // 删除数据
    public void removeData(int position) {
        addressData.remove(position);
        //删除动画
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public class LinearViewHolder extends RecyclerView.ViewHolder {

        private TextView username, phone, address, toleration;
        private Button delete;

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.receipt_username);
            phone = itemView.findViewById(R.id.receipt_phone);
            address = itemView.findViewById(R.id.receipt_address);
            toleration = itemView.findViewById(R.id.receipt_default);
            delete = itemView.findViewById(R.id.receipt_delete);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //可以选择直接在本位置直接写业务处理
                    //Toast.makeText(context,"点击了xxx",Toast.LENGTH_SHORT).show();
                    //此处回传点击监听事件
                    if (onItemClickListener != null) {
                        onItemClickListener.OnItemClick(v, addressData.get(getAdapterPosition()));
                    }
                }
            });
        }
    }

    //设置item的监听事件的接口
    public interface OnItemClickListener {
        /*  *
         * 接口中的点击每一项的实现方法，参数自己定义
         *
         * @param view 点击的item的视图
         * @param data 点击的item的数据*/

        public void OnItemClick(View view, AddressList addressList);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    //item的监听事件的接口end

}
