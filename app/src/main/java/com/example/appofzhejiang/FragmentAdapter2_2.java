package com.example.appofzhejiang;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appofzhejiang.fragment2.RecycleBean2_2;
import com.example.appofzhejiang.fragment3.Ticket;
import com.example.appofzhejiang.fragment3.TicketDetail.TicketDetailActivity;
import com.example.appofzhejiang.xihu.Dairy;
import com.example.appofzhejiang.xihu.Jingqu;

import java.util.List;

public class FragmentAdapter2_2 extends RecyclerView.Adapter<FragmentAdapter2_2.GridViewHolder> {

    private Context context;
    private List<Ticket> ticketList;
    private String price;

    public FragmentAdapter2_2(List<Ticket> ticketList, Context context) {
        this.ticketList = ticketList;
        this.context = context;
    }


    @NonNull
    @Override
    public FragmentAdapter2_2.GridViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_grid_adapter2, parent, false);
        final GridViewHolder gridViewHolder = new GridViewHolder(view);
        // 注册点击事件 start
        gridViewHolder.gridView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = gridViewHolder.getAdapterPosition();
                Ticket ticket = ticketList.get(position);
                Intent intent = new Intent(parent.getContext(), TicketDetailActivity.class);
                intent.putExtra("index", "7");
                intent.putExtra("product_id", Integer.toString(ticket.getProduct_id()));
                intent.putExtra("company", "全城旅游");
                intent.putExtra("image", ticket.getPath());
                parent.getContext().startActivity(intent);
            }
        });
        // 注册点击事件 end
        return gridViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FragmentAdapter2_2.GridViewHolder holder, final int position) {
        Ticket ticket = ticketList.get(position);
        Glide.with(context).load(ticket.getPath()).into(holder.gridImage);
        //处理价格
        String s = ticket.getPrices();
        String[] ss = s.split(",");
        if (Integer.parseInt(ss[0]) < Integer.parseInt(ss[1])) {
            price = ss[0];
            holder.txtPrice.setText(ss[0]);
        } else {
            price = ss[1];
            holder.txtPrice.setText(ss[1]);
        }
        holder.txtTitle.setText(ticket.getName());
        holder.txtGridAmount.setText(ticket.getSales());
        holder.txtCompany.setText("全城旅游");
    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    public void refresh(List<Ticket> newList) {
        //刷新数据
        ticketList.removeAll(ticketList);
        ticketList.addAll(newList);
        notifyDataSetChanged();
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle, txtGridAmount, txtPrice, txtCompany;
        private ImageView gridImage;
        private View gridView;//用来设置点击事件

        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            gridView = itemView;
            txtTitle = itemView.findViewById(R.id.txt_grid_title);
            txtPrice = itemView.findViewById(R.id.txt_price);
            txtCompany = itemView.findViewById(R.id.txt_company);
            txtGridAmount = itemView.findViewById(R.id.txt_gridAmount);
            gridImage = itemView.findViewById(R.id.grid_image);
        }
    }
}