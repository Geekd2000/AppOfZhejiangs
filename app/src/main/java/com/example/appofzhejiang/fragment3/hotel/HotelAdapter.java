package com.example.appofzhejiang.fragment3.hotel;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appofzhejiang.R;
import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ViewHolder> {
    private List<Hotel> hotelList;
    private Context context;

    public HotelAdapter(List<Hotel> hotelList, Context context) {
        this.hotelList = hotelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_hotel_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Hotel hotel = hotelList.get(position);
        holder.getLogoImage().setImageResource(hotel.getImageId());
        holder.getNameText().setText(hotel.getName());
        holder.getCountText().setText(hotel.getCount());
        holder.getLocationText().setText(hotel.getLocation());
        holder.getPriceText().setText(hotel.getPrice());
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View hotelView; // 用来做点击事件的
        private ImageView logoImage;
        private TextView nameText;
        private TextView priceText;
        private TextView locationText;
        private TextView countText;

        public ViewHolder(View view) {
            super(view);
            setHotelView(view);
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




