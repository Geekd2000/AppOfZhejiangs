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

import com.example.appofzhejiang.xihu.Jingqu;

public class FragmentAdapter2_1 extends RecyclerView.Adapter<FragmentAdapter2_1.LinearViewHolder> {

    private Context context;

    public FragmentAdapter2_1(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public FragmentAdapter2_1.LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_linear_adapter2, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FragmentAdapter2_1.LinearViewHolder holder, final int position) {
        switch (position) {
            case 0:
                holder.txtTitle.setText("西湖");
                holder.txtLabel.setText("5A景区");
                holder.listImage.setImageResource(R.drawable.westlake);
                break;
            case 1:
                holder.txtTitle.setText("西溪国家湿地公园");
                holder.txtLabel.setText("5A景区");
                holder.listImage.setImageResource(R.drawable.wetland);
                break;
            case 2:
                holder.txtTitle.setText("杭州宋城景区");
                holder.txtLabel.setText("4A景区");
                holder.listImage.setImageResource(R.drawable.songcity);
                break;
        }
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Jingqu.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class LinearViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle, txtLabel;
        private ImageView listImage;
        private LinearLayout linearLayout;

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_place);
            txtLabel = itemView.findViewById(R.id.txt_label);
            listImage = itemView.findViewById(R.id.list2_image);
            linearLayout=itemView.findViewById(R.id.list2);
        }
    }
}
