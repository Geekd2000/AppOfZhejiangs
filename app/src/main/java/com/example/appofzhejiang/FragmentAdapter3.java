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

public class FragmentAdapter3 extends RecyclerView.Adapter<FragmentAdapter3.LinearViewHolder> {

    private Context context;
    private List<Ticket> ticketList;

    public FragmentAdapter3(List<Ticket> ticketList, Context context) {
        this.ticketList = ticketList;
        this.context = context;
    }


    @NonNull
    @Override
    public FragmentAdapter3.LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_linear_adapter3, parent, false);
        final LinearViewHolder linearViewHolder = new LinearViewHolder(view);
        // 注册点击事件 start
        linearViewHolder.getTicketView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = linearViewHolder.getAdapterPosition();
                Ticket ticket = ticketList.get(position);
                String img = Integer.toString(ticket.getImageId());
                Intent intent = new Intent(context, TicketDetailActivity.class);
                intent.putExtra("index", "7");
                intent.putExtra("title", ticket.getName());
                intent.putExtra("price", ticket.getPrice());
                intent.putExtra("company", ticket.getLocation());
                intent.putExtra("count", ticket.getCount());
                intent.putExtra("image", img);
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
