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

import com.bumptech.glide.Glide;
import com.example.appofzhejiang.R;
import com.example.appofzhejiang.fragment2.RecycleBean2_2;
import com.example.appofzhejiang.fragment3.Ticket;
import com.example.appofzhejiang.fragment3.TicketDetail.TicketDetailActivity;

import java.util.List;

public class RecycleViewAdepter_more extends RecyclerView.Adapter<RecycleViewAdepter_more.MyViewHolder> {
    private Context context;
    private List<RecycleBean2_2> list;
    private List<RecyclerBeanJingqu> recyclerBeanJingquList;

    OnItemClickListener listener;

    public interface OnItemClickListener {
        /*注意参数*/
        public void OnItemClick(View v, int position, String id);
    }

    public void setOnItemClick(OnItemClickListener listener) {
        this.listener = listener;
    }

    public RecycleViewAdepter_more(List<RecycleBean2_2> list,List<RecyclerBeanJingqu> recyclerBeanJingquList, Context context) {
        this.recyclerBeanJingquList = recyclerBeanJingquList;
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecycleViewAdepter_more.MyViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_linear_adapter2, parent, false);
        final RecycleViewAdepter_more.MyViewHolder viewHolder= new RecycleViewAdepter_more.MyViewHolder(view);
        // 注册点击事件 start
        viewHolder.jinquView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                RecycleBean2_2 recycler = list.get(position);
                for(RecyclerBeanJingqu recyclerBeanJingqu : recyclerBeanJingquList){
                    if(recycler.getName().equals(recyclerBeanJingqu.getName())){
                        int id = recyclerBeanJingqu.getProduct_id();
                        Intent intent = new Intent(parent.getContext(), Jingqu.class);
                        intent.putExtra("product_id", Integer.toString(id));
                        int id2 = recycler.getPlace_id();
                        intent.putExtra("place_id", Integer.toString(id2));
                        parent.getContext().startActivity(intent);
                    }
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        RecycleBean2_2 recycler = list.get(position);
        for(RecyclerBeanJingqu recyclerBeanJingqu : recyclerBeanJingquList){
            if(recycler.getName().equals(recyclerBeanJingqu.getName())){
                String path = recyclerBeanJingqu.getPath();
                Glide.with(context).load(path).into(holder.listImage);
                holder.txtTitle.setText(recycler.getName());
                holder.txtLabel.setText(recycler.getLevel()+"景区");
                break;
            }
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle, txtLabel;
        private ImageView listImage;
        private View jinquView;//用于点击事件

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            jinquView = itemView;
            txtTitle = itemView.findViewById(R.id.txt_place);
            txtLabel = itemView.findViewById(R.id.txt_label);
            listImage = itemView.findViewById(R.id.list2_image);
        }
    }
}
