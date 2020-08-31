package com.example.appofzhejiang.Business;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appofzhejiang.CustomDialog.CustomDialog;
import com.example.appofzhejiang.R;
import com.example.appofzhejiang.fragment3.Ticket;

import java.util.List;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.LinearViewHolder> {

    private Context mContext;
    private List<AddressBean> addressData;//收货信息

    public ReceiptAdapter(List<AddressBean> addressData, Context context) {
        this.mContext = context;
        this.addressData = addressData;
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
        AddressBean addressBean=addressData.get(position);
        if (!addressBean.getDefault()) {
            holder.toleration.setVisibility(View.INVISIBLE);
        } else {
            holder.toleration.setVisibility(View.VISIBLE);
        }
        holder.username.setText(addressBean.getName());
        holder.phone.setText(addressBean.getMobile());
        holder.address.setText(addressBean.getAddress());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog customDialog = new CustomDialog(mContext);
                customDialog.setTitle("提示").setMessage("确定要删除该地址吗？")
                        .setCancel("取消", new CustomDialog.IOnCancelListener() {
                            @Override
                            public void onCancel(CustomDialog dialog) {
                                // 取消删除
                            }
                        }).setConfirm("确定", new CustomDialog.IOnConfirmListener() {
                    @Override
                    public void onConfirm(CustomDialog dialog) {
                        // 确定删除 删除记录
                        //删除自带默认动画
                        removeData(position);
                    }
                }).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressData.size();
    }

    public void refresh(List<AddressBean> newList) {
        //刷新数据
        addressData.removeAll(addressData);
        addressData.addAll(newList);
        notifyDataSetChanged();
    }

    /*// 添加数据
    public void addData(int position) {
        //在list中添加数据，并通知条目加入一条
        addressData.add(position, addressBean);
        //添加动画
        notifyItemInserted(position);
    }
*/
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

        public void OnItemClick(View view, AddressBean addressBean);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    //item的监听事件的接口end

}
