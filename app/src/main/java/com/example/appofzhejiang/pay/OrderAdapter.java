package com.example.appofzhejiang.pay;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appofzhejiang.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.LinearViewHolder> {

    private Context mContext;
    private List<FileList> mDates;

    public OrderAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_order_message, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class LinearViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvOrder, mTvMessage, mTvNumber, mTvMoney, nTvMoney1, mTvStatus;
        private ImageView mIvPicture;

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvMessage = itemView.findViewById(R.id.iv_message);
            mTvOrder = itemView.findViewById(R.id.order_id);
            mTvNumber = itemView.findViewById(R.id.number);
            mTvMoney = itemView.findViewById(R.id.money);
            nTvMoney1 = itemView.findViewById(R.id.money1);
            mIvPicture = itemView.findViewById(R.id.iv_picture);
            mTvStatus = itemView.findViewById(R.id.status);
        }
    }
}
