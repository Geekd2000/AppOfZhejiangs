package com.example.appofzhejiang.fragment1.recyclerpage;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.appofzhejiang.R;

import java.util.List;

public class RecyclerPageAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<RecyclerBean> recyclerBeanList;
    private Context context;

    public RecyclerPageAdapter(List<RecyclerBean> recyclerBeanList, Context context) {
        this.recyclerBeanList = recyclerBeanList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_page_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        // 注册点击事件 start
        viewHolder.getHotelView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                RecyclerBean hotel = recyclerBeanList.get(position);
            }
        });
        // 注册点击事件 end

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecyclerBean hotel = recyclerBeanList.get(position);
//        holder.getLogoImage().setImageBitmap(hotel.getBitmapImg());
        if(context != null) {
            Glide.with(context)
                    .load(hotel.getPictures())
                    .centerCrop()
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .into(holder.getLogoImage());
        }

        holder.getNameText().setText(hotel.getTitle());
        holder.getTypeText().setText(hotel.getType());
        holder.getTimeText().setText(hotel.getTime());
        holder.getCountText().setText(hotel.getDread());
    }

    @Override
    public int getItemCount() {
        return recyclerBeanList.size();
    }
}

class ViewHolder extends RecyclerView.ViewHolder {
    private View hotelView; // 用来做点击事件的
    private ImageView logoImage;
    private TextView nameText;
    private TextView typeText;
    private TextView timeText;
    private TextView countText;

    public ViewHolder(View view) {
        super(view);
        setHotelView(view);
        setLogoImage((ImageView) view.findViewById(R.id.tourism_strategy_logo));
        setNameText((TextView) view.findViewById(R.id.tourism_strategy_name));
        setTypeText((TextView) view.findViewById(R.id.tourism_strategy_type));
        setTimeText((TextView) view.findViewById(R.id.tourism_strategy_time));
        setCountText((TextView) view.findViewById(R.id.tourism_strategy_count));
    }

    public View getHotelView() {
        return hotelView;
    }

    public void setHotelView(View hotelView) {
        this.hotelView = hotelView;
    }

    public ImageView getLogoImage() {
        return logoImage;
    }

    public void setLogoImage(ImageView logoImage) {
        this.logoImage = logoImage;
    }

    public TextView getNameText() {
        return nameText;
    }

    public void setNameText(TextView nameText) {
        this.nameText = nameText;
    }

    public TextView getTypeText() {
        return typeText;
    }

    public void setTypeText(TextView typeText) {
        this.typeText = typeText;
    }

    public TextView getTimeText() {
        return timeText;
    }

    public void setTimeText(TextView timeText) {
        this.timeText = timeText;
    }

    public TextView getCountText() {
        return countText;
    }

    public void setCountText(TextView countText) {
        this.countText = countText;
    }
}


