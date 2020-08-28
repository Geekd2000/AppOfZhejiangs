package com.example.appofzhejiang.fragment3.hotel;

import android.app.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appofzhejiang.R;
import com.example.appofzhejiang.fragment3.Ticket;
import com.example.appofzhejiang.fragment3.TicketType;

import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ViewHolder> {

    private List<Hotel> hotelList;
    private Context context;
    private String price;
    private String type;

    public HotelAdapter(List<Hotel> hotelList, Context context, String type) {
        this.hotelList = hotelList;
        this.context = context;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_hotel_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Hotel hotel = hotelList.get(position);
        if (context != null) {
            Glide.with(context).load(hotel.getPath()).centerCrop().dontAnimate().into(holder.getLogoImage());
        }

        //处理价格
        String s = hotel.getPrices();
        String[] ss = s.split(",");
        if (Integer.parseInt(ss[0]) < Integer.parseInt(ss[1])) {
            price = ss[0];
            holder.getPriceText().setText(ss[0]);
        } else {
            price = ss[1];
            holder.getPriceText().setText(ss[1]);
        }
        holder.getNameText().setText(hotel.getName());
        holder.getCountText().setText(hotel.getSales());
        if (TicketType.TICKET.equals(type)) {
            holder.getLocationText().setText("全城旅游");
        } else if (TicketType.HOTEL.equals(type)) {
            holder.getLocationText().setText("钱江酒店集团");
        } else if (TicketType.TAXI.equals(type)) {
            holder.getLocationText().setText("八戒租车集团");
        } else if (TicketType.GUIDER.equals(type)) {
            holder.getLocationText().setText("百事通旅行社");
        } else if (TicketType.FARM.equals(type)) {
            holder.getLocationText().setText("钱江农家乐集团");
        } else if (TicketType.FOOD.equals(type)) {
            holder.getLocationText().setText("钱江美食集团");
        } else if (TicketType.PRODUCT.equals(type)) {
            holder.getLocationText().setText("钱江特产集团");
        }
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    //下面两个方法提供给页面刷新和加载时调用
    public void add(List<Hotel> addHotelList) {
        //增加数据
        int position = hotelList.size();
        hotelList.addAll(position, addHotelList);
        notifyItemInserted(position);
    }

    public void refresh(List<Hotel> newHotelList) {
        //刷新数据
        hotelList.removeAll(hotelList);
        hotelList.addAll(newHotelList);
        notifyDataSetChanged();
    }

    //设置item的监听事件的接口
    public interface OnItemClickListener {
        /*  *
         * 接口中的点击每一项的实现方法，参数自己定义
         *
         * @param view 点击的item的视图
         * @param data 点击的item的数据*/

        public void OnItemClick(View view, Hotel hotel);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    //item的监听事件的接口end

    class ViewHolder extends RecyclerView.ViewHolder {
        private View hotelView; // 用来做点击事件的
        private ImageView logoImage;
        private TextView nameText;
        private TextView priceText;
        private TextView locationText;
        private TextView countText;

        public ViewHolder(View view) {
            super(view);
            setLogoImage((ImageView) view.findViewById(R.id.image_logo));
            setNameText((TextView) view.findViewById(R.id.hotel_name));
            setPriceText((TextView) view.findViewById(R.id.hotel_price));
            setLocationText((TextView) view.findViewById(R.id.hotel_locationText));
            setCountText((TextView) view.findViewById(R.id.hotel_count));
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //可以选择直接在本位置直接写业务处理
                    //Toast.makeText(context,"点击了xxx",Toast.LENGTH_SHORT).show();
                    //此处回传点击监听事件
                    if (onItemClickListener != null) {
                        onItemClickListener.OnItemClick(v, hotelList.get(getAdapterPosition()));
                    }
                }
            });
        }

        public View getHotelView() {
            return hotelView;
        }

        public void setHotelView(View hotelView) {
            this.hotelView = hotelView;
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




