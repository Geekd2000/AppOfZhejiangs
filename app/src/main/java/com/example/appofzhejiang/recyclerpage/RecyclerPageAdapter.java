package com.example.appofzhejiang.recyclerpage;


import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appofzhejiang.MainActivity;
import com.example.appofzhejiang.MyFragment1;
import com.example.appofzhejiang.R;

import java.util.List;

public class RecyclerPageAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<RecyclerBean> recyclerBeanList;
    private Fragment fragment;
    private Activity activity;

    public RecyclerPageAdapter(List<RecyclerBean> recyclerBeanList,Fragment fragment) {
        this.recyclerBeanList = recyclerBeanList;
        this.fragment = fragment;
    }
    public RecyclerPageAdapter(List<RecyclerBean> recyclerBeanList,Activity activity) {
        this.recyclerBeanList = recyclerBeanList;
        this.activity = activity;
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
                Toast.makeText(view.getContext(), "You clicked view：" + hotel.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        // 注册点击事件 end

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecyclerBean hotel = recyclerBeanList.get(position);
//        holder.getLogoImage().setImageBitmap(hotel.getBitmapImg());
        if(activity != null) {
            Glide.with(activity).load(hotel.getPictures()).centerCrop().dontAnimate().into(holder.getLogoImage());
        }
        if(fragment != null) {
            Glide.with(fragment).load(hotel.getPictures()).centerCrop().dontAnimate().into(holder.getLogoImage());
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


