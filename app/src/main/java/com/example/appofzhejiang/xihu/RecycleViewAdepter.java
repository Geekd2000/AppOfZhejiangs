package com.example.appofzhejiang.xihu;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appofzhejiang.R;

public class RecycleViewAdepter extends RecyclerView.Adapter<RecycleViewAdepter.MyViewHolder> {

    private Context context;

    public RecycleViewAdepter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_linear_adapter1, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText("诗画浙江");
        holder.time.setText("2020-7-27 22:18");
        holder.amount.setText("浏览量：777");
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title, time, amount, label;
        private ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.list_title);
            time = itemView.findViewById(R.id.list_time);
            amount = itemView.findViewById(R.id.list_amount);
            label = itemView.findViewById(R.id.list_label);
            image = itemView.findViewById(R.id.list_image);
        }
    }
}
