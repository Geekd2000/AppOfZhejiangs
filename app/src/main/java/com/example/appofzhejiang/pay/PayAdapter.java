package com.example.appofzhejiang.pay;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appofzhejiang.R;
import com.example.appofzhejiang.fragment3.Ticket;

import java.util.List;

public class PayAdapter extends RecyclerView.Adapter<PayAdapter.LinearViewHolder> {

    private Context mContext;
    private List<OrderBean> orderBeanList;

    public PayAdapter(List<OrderBean> orderBeanList, Context context) {
        this.mContext = context;
        this.orderBeanList = orderBeanList;
    }

    @NonNull
    @Override
    public LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_pay_message, parent, false);
        final LinearViewHolder linearViewHolder = new LinearViewHolder(view);
        // 注册点击事件 start
        linearViewHolder.getFileListView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = linearViewHolder.getAdapterPosition();
                OrderBean orderBean = orderBeanList.get(position);
                double unitPrice = orderBean.getMoney() / orderBean.getNum();
                linearViewHolder.mIvPicture.setDrawingCacheEnabled(true);
                Intent intent = new Intent(mContext, OrderActivity.class);
                intent.putExtra("order_id", String.valueOf(orderBean.getOrder_id()));//传入订单id
                mContext.startActivity(intent);
            }
        });
        // 注册点击事件 end
        return linearViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, int position) {
        OrderBean orderBean = orderBeanList.get(position);
        //单价获取
        double unitPrice = orderBean.getMoney() / orderBean.getNum();

        Glide.with(mContext).load(orderBean.getRemarks()).into(holder.getIvPicture());
        holder.getTvOrder().setText(orderBean.getOrder_no());
        holder.getTvTitle().setText(orderBean.getShop_name());
        holder.getTvType().setText(orderBean.getParam());
        holder.getTvCount().setText(String.valueOf(orderBean.getNum()));
        holder.getTvMoney1().setText(String.valueOf(unitPrice));
        holder.getTvMoney2().setText(String.valueOf(orderBean.getMoney()));
        if (!orderBean.getPay()) {
            holder.getTvStatus().setText("待付款");
        } else if (orderBean.getPay()) {
            holder.getTvStatus().setText("已付款");
        }
        if (orderBean.getPin()) {
            holder.getTvStatus().setText("已完成");
        }
    }

    @Override
    public int getItemCount() {
        return orderBeanList.size();
    }

    public void refresh(List<OrderBean> newOrderList) {
        //刷新数据
        orderBeanList.removeAll(orderBeanList);
        orderBeanList.addAll(newOrderList);
        notifyDataSetChanged();
    }


    class LinearViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvOrder, mTvTitle, mTvCount, mTvMoney1, mTvMoney2, mTvStatus, mTvType;
        private ImageView mIvPicture;
        private View fileListView; // 用来做点击事件的

        public LinearViewHolder(@NonNull View view) {
            super(view);
            setFileListView(view);
            setIvPicture((ImageView) view.findViewById(R.id.iv_picture));
            setTvTitle((TextView) view.findViewById(R.id.iv_message));
            setTvType((TextView) view.findViewById(R.id.iv_message_1));
            setTvOrder((TextView) view.findViewById(R.id.order_id));
            setTvCount((TextView) view.findViewById(R.id.number));
            setTvMoney1((TextView) view.findViewById(R.id.money1));
            setTvMoney2((TextView) view.findViewById(R.id.money2));
            setTvStatus((TextView) view.findViewById(R.id.status));
        }

        public View getFileListView() {
            return fileListView;
        }

        public void setFileListView(View fileListView) {
            this.fileListView = fileListView;
        }

        public void setIvPicture(ImageView mIvPicture) {
            this.mIvPicture = mIvPicture;
        }

        public void setTvTitle(TextView mTvTitle) {
            this.mTvTitle = mTvTitle;
        }

        public void setTvType(TextView mTvType) {
            this.mTvType = mTvType;
        }

        public void setTvOrder(TextView mTvOrder) {
            this.mTvOrder = mTvOrder;
        }

        public void setTvCount(TextView mTvCount) {
            this.mTvCount = mTvCount;
        }

        public void setTvMoney1(TextView mTvMoney1) {
            this.mTvMoney1 = mTvMoney1;
        }

        public void setTvMoney2(TextView mTvMoney2) {
            this.mTvMoney2 = mTvMoney2;
        }

        public void setTvStatus(TextView mTvStatus) {
            this.mTvStatus = mTvStatus;
        }

        public ImageView getIvPicture() {
            return mIvPicture;
        }

        public TextView getTvTitle() {
            return mTvTitle;
        }

        public TextView getTvType() {
            return mTvType;
        }

        public TextView getTvOrder() {
            return mTvOrder;
        }

        public TextView getTvCount() {
            return mTvCount;
        }

        public TextView getTvMoney1() {
            return mTvMoney1;
        }

        public TextView getTvMoney2() {
            return mTvMoney2;
        }

        public TextView getTvStatus() {
            return mTvStatus;
        }
    }
}
