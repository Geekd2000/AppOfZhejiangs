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

import com.example.appofzhejiang.fragment3.Ticket;
import com.example.appofzhejiang.fragment3.TicketDetailActivity;

import java.util.List;

public class FragmentAdapter3_2 extends RecyclerView.Adapter<FragmentAdapter3_2.LinearViewHolder> {

    private Context context;
    private List<Ticket> ticketList;

    public FragmentAdapter3_2(List<Ticket> ticketList, Context context) {
        this.ticketList = ticketList;
        this.context = context;
    }


    @NonNull
    @Override
    public FragmentAdapter3_2.LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_linear_adapter3_2, parent, false);
        final LinearViewHolder linearViewHolder = new LinearViewHolder(view);
        // 注册点击事件 start
        linearViewHolder.getTicketView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = linearViewHolder.getAdapterPosition();
                Ticket ticket = ticketList.get(position);
                //Toast.makeText(view.getContext(), "You clicked view：" + ticket.getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, TicketDetailActivity.class);
                intent.putExtra("index", "7");
                intent.putExtra("title", ticket.getName());
                intent.putExtra("price", ticket.getPrice());
                intent.putExtra("company", ticket.getLocation());
                intent.putExtra("count", ticket.getCount());
                context.startActivity(intent);
            }
        });
        // 注册点击事件 end
        return linearViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, final int position) {
        Ticket ticket = ticketList.get(position);
        holder.getLogoImage().setImageResource(ticket.getImageId());
        holder.getNameText().setText(ticket.getName());
        holder.getCountText().setText(ticket.getCount());
        holder.getLocationText().setText(ticket.getLocation());
        holder.getPriceText().setText(ticket.getPrice());
    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    /*//设置item的监听事件的接口
    public interface OnItemClickListener {
        *//*  *
     * 接口中的点击每一项的实现方法，参数自己定义
     *
     * @param view 点击的item的视图
     * @param data 点击的item的数据*//*

        public void OnItemClick(View view,Ticket ticket);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }*/

    public class LinearViewHolder extends RecyclerView.ViewHolder {

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
            /*view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //可以选择直接在本位置直接写业务处理
                    //Toast.makeText(context,"点击了xxx",Toast.LENGTH_SHORT).show();
                    //此处回传点击监听事件
                    if(onItemClickListener!=null){
                        onItemClickListener.OnItemClick(v, ticketList.get(getAdapterPosition()));
                    }
                }
            });*/
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
}
