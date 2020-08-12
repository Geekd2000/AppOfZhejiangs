package com.example.appofzhejiang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentAdapter1 extends RecyclerView.Adapter<FragmentAdapter1.LinearViewHolder> {

    private Context context;

    public FragmentAdapter1(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_linear_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, final int position) {
        holder.title.setText("诗画浙江");
        holder.time.setText("2020-7-27 22:18");
        holder.amount.setText("777");
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class LinearViewHolder extends RecyclerView.ViewHolder {

        private TextView title, time, amount, label;
        private ImageView image;

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.list_title);
            time = itemView.findViewById(R.id.list_time);
            amount = itemView.findViewById(R.id.list_amount);
            label = itemView.findViewById(R.id.list_label);
            image = itemView.findViewById(R.id.list_image);
        }
    }
}
