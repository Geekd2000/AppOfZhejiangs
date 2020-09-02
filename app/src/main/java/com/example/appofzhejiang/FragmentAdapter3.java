package com.example.appofzhejiang;

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
import com.example.appofzhejiang.fragment3.Ticket;
import com.example.appofzhejiang.fragment3.TicketDetail.TicketDetailActivity;

import java.util.List;

public class FragmentAdapter3 extends RecyclerView.Adapter<LinearViewHolder> {

    private Context context;
    private List<Ticket> ticketList;
    private String price;

    public FragmentAdapter3(List<Ticket> ticketList, Context context) {
        this.ticketList = ticketList;
        this.context = context;
    }


    @NonNull
    @Override
    public LinearViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_linear_adapter3, parent, false);
        final LinearViewHolder linearViewHolder = new LinearViewHolder(view);
        // 注册点击事件 start
        linearViewHolder.getTicketView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = linearViewHolder.getAdapterPosition();
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
        return linearViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, final int position) {
        Ticket ticket = ticketList.get(position);
        if (context != null) {
            Glide.with(context).load(ticket.getPath()).centerCrop().dontAnimate().into(holder.getLogoImage());
        }

        //处理价格
        String s = ticket.getPrices();
        String[] ss = s.split(",");
        if (Integer.parseInt(ss[0]) < Integer.parseInt(ss[1])) {
            price = ss[0];
            holder.getPriceText().setText(ss[0]);
        } else {
            price = ss[1];
            holder.getPriceText().setText(ss[1]);
        }
        holder.getNameText().setText(ticket.getName());
        holder.getCountText().setText(ticket.getSales());
        holder.getLocationText().setText("全城旅游");
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    //下面两个方法提供给页面刷新和加载时调用
    public void add(List<Ticket> addTicketList) {
        //增加数据
        int position = ticketList.size();
        ticketList.addAll(position, addTicketList);
        notifyItemInserted(position);
    }

    public void refresh(List<Ticket> newTicketList) {
        //刷新数据
        ticketList.removeAll(ticketList);
        ticketList.addAll(newTicketList);
        notifyDataSetChanged();
    }
}

class LinearViewHolder extends RecyclerView.ViewHolder {

    private View ticketView; // 用来做点击事件的
    private ImageView logoImage;
    private TextView nameText;
    private TextView priceText;
    private TextView locationText;
    private TextView countText;

    public LinearViewHolder(@NonNull View view) {
        super(view);
        setTicketView(view);
        setLogoImage((ImageView) view.findViewById(R.id.placeImage));
        setNameText((TextView) view.findViewById(R.id.txt_placeTitle));
        setPriceText((TextView) view.findViewById(R.id.txt_price));
        setLocationText((TextView) view.findViewById(R.id.txt_company));
        setCountText((TextView) view.findViewById(R.id.txt_sale));
    }

    public View getTicketView() {
        return ticketView;
    }

    public void setTicketView(View ticketView) {
        this.ticketView = ticketView;
    }

    public void setLogoImage(ImageView logoImage) {
        this.logoImage = logoImage;
    }

    public void setNameText(TextView nameText) {
        this.nameText = nameText;
    }

    public void setPriceText(TextView priceText) {
        this.priceText = priceText;
    }

    public void setLocationText(TextView locationText) {
        this.locationText = locationText;
    }

    public void setCountText(TextView countText) {
        this.countText = countText;
    }

    public ImageView getLogoImage() {
        return logoImage;
    }

    public TextView getNameText() {
        return nameText;
    }

    public TextView getPriceText() {
        return priceText;
    }

    public TextView getLocationText() {
        return locationText;
    }

    public TextView getCountText() {
        return countText;
    }
}
