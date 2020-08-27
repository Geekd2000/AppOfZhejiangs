package com.example.appofzhejiang.fragment1.beautifulzj;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appofzhejiang.R;

import java.util.List;

public class BeautifulPictureAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<BeautifulPicture> bpList;
    private Context context;


    public BeautifulPictureAdapter(List<BeautifulPicture> bpList, Context context) {
        this.bpList = bpList;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.beautiful_picture_item, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);

        // 注册点击事件 start
        viewHolder.getHotelView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                BeautifulPicture hotel = bpList.get(position);
            }
        });
        // 注册点击事件 end

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BeautifulPicture bp = bpList.get(position);


        if(context != null) {
            Glide.with(context).load(bp.getUrl()).centerCrop().dontAnimate().into(holder.getImageView());
        }
       holder.getTextView().setText(bp.getIntroduction());
    }

    @Override
    public int getItemCount() {
        return bpList.size();
    }
}

class ViewHolder extends RecyclerView.ViewHolder {
    private View hotelView; // 用来做点击事件的
    private ImageView imageView;
    private TextView textView;


    public ViewHolder(View view) {
        super(view);
        setHotelView(view);
        setImageView((ImageView) view.findViewById(R.id.beautiful_picture_image));
        setTextView((TextView) view.findViewById(R.id.beautiful_picture_introduction));
    }

    public View getHotelView() {
        return hotelView;
    }

    public void setHotelView(View hotelView) {
        this.hotelView = hotelView;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }
}



