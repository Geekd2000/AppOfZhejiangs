package com.example.appofzhejiang.Business;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appofzhejiang.R;

import java.util.List;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.LinearViewHolder> {

    private Context mContext;
    private List<AddressList> mData;

    public ReceiptAdapter(Context context ,List<AddressList> data){
        this.mContext = context;
        this.mData=data;
    }

    @NonNull
    @Override
    public LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.layout_receipt, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, int position) {
        if(position != 1){
            holder.toleration.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class LinearViewHolder extends RecyclerView.ViewHolder {

        private TextView username,phone,address,toleration;
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
