package com.example.appofzhejiang.xihu;

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

import com.example.appofzhejiang.R;

public class RecycleViewAdepter_more extends RecyclerView.Adapter<RecycleViewAdepter_more.MyViewHolder>{
    private Context context;

    OnItemClickListener listener;
    public interface OnItemClickListener{
        /*注意参数*/
        public void OnItemClick(View v, int position, String id);
    }
    public void setOnItemClick(OnItemClickListener listener){
        this.listener=listener;
    }

    public RecycleViewAdepter_more(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecycleViewAdepter_more.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecycleViewAdepter_more.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_more, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.place.setText("西湖");
        holder.label.setText("5A景区");
        holder.list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent=new Intent(v.getContext(), Jingqu.class);
                    v.getContext().startActivity(intent);
                }

        });
    }


    @Override
    public int getItemCount() {
        return 10;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView place,label;
        private ImageView image;
        private LinearLayout list;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            place = itemView.findViewById(R.id.txt_place);
            label = itemView.findViewById(R.id.txt_label);
            image = itemView.findViewById(R.id.list2_image);
            list = itemView.findViewById(R.id.list1);
        }
    }
}
