package com.example.appofzhejiang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentAdapter2_2 extends RecyclerView.Adapter<FragmentAdapter2_2.GridViewHolder> {

    private Context context;

    public FragmentAdapter2_2(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public FragmentAdapter2_2.GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GridViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_grid_adapter2, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FragmentAdapter2_2.GridViewHolder holder, final int position) {
        if (position % 2 != 0) {
            holder.txtGrid.setText("西湖一日游");
            holder.txtGridAmount.setText("77人浏览");
            holder.txtGridName.setText("Clearlove7");
            holder.txtGridTime.setText("2020-7-7 07:17");
            holder.gridImage.setImageResource(R.drawable.demo);
            holder.userImage.setImageResource(R.drawable.userimage);
        } else {
            holder.txtGrid.setText("西溪湿地");
            holder.txtGridAmount.setText("66人浏览");
            holder.txtGridName.setText("UZi666");
            holder.txtGridTime.setText("2020-6-6 16:16");
            holder.gridImage.setImageResource(R.drawable.demo2);
            holder.userImage.setImageResource(R.drawable.userimage2);
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {
        private TextView txtGrid, txtGridAmount, txtGridName, txtGridTime;
        private ImageView gridImage, userImage;

        public GridViewHolder(@NonNull View itemView) {
            super(itemView);

            txtGrid = itemView.findViewById(R.id.txt_grid);
            txtGridAmount = itemView.findViewById(R.id.txt_gridAmount);
            txtGridName = itemView.findViewById(R.id.grid_name);
            txtGridTime = itemView.findViewById(R.id.grid_time);
            gridImage = itemView.findViewById(R.id.grid_image);
            userImage = itemView.findViewById(R.id.circle_user);
        }
    }
}
