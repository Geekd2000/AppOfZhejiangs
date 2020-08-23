package com.example.appofzhejiang.Business;

import android.content.Context;
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
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.LinearViewHolder> {

    private Context mContext;
    private List<AddressList> data;//收货信息
    private AddressList addressList;

    public ReceiptAdapter(Context context, List<AddressList> data, AddressList addressList) {
        this.mContext = context;
        this.data = data;
        this.addressList = addressList;
    }

    @NonNull
    @Override
    public LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_receipt, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, final int position) {
        if (position != 0) {
            holder.toleration.setVisibility(View.INVISIBLE);
        }
        holder.username.setText(data.get(position).getUsername());
        holder.phone.setText(data.get(position).getPhone());
        holder.address.setText(data.get(position).getAddress());
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
        return data.size();
    }

    // 添加数据
    public void addData(int position) {
        //在list中添加数据，并通知条目加入一条
        data.add(position, addressList);
        //添加动画
        notifyItemInserted(position);
    }

    // 删除数据
    public void removeData(int position) {
        data.remove(position);
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
        }
    }

}
