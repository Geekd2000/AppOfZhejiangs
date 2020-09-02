package com.example.appofzhejiang;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appofzhejiang.fragment1.recyclerpage.RecyclerBean;
import com.example.appofzhejiang.fragment2.RecycleBean2_2;
import com.example.appofzhejiang.fragment2.RecyclerBean_2;
import com.example.appofzhejiang.fragment3.Ticket;
import com.example.appofzhejiang.xihu.Dairy;
import com.example.appofzhejiang.xihu.Jingqu;
import com.example.appofzhejiang.xihu.RecyclerBeanJingqu;

import java.util.List;

public class FragmentAdapter2_1 extends RecyclerView.Adapter<FragmentAdapter2_1.LinearViewHolder> {

    private List<RecycleBean2_2> list;
    private List<RecyclerBeanJingqu> imageList;
    private Context context;

    public FragmentAdapter2_1(){}

    public FragmentAdapter2_1(List<RecycleBean2_2> list, List<RecyclerBeanJingqu> imageList, Context context) {
        this.list = list;
        this.imageList = imageList;
        this.context = context;
    }

    @NonNull
    @Override
    public FragmentAdapter2_1.LinearViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_linear_adapter2, parent, false);
        final FragmentAdapter2_1.LinearViewHolder viewHolder = new FragmentAdapter2_1.LinearViewHolder(view);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                RecycleBean2_2 recycler = list.get(position);
                for(RecyclerBeanJingqu recyclerBeanJingqu : imageList){
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
    public void onBindViewHolder(@NonNull FragmentAdapter2_1.LinearViewHolder holder, final int position) {
        RecycleBean2_2 recycler = list.get(position);
        for(RecyclerBeanJingqu recyclerBeanJingqu : imageList){
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
        return 3;
    }

    public void refresh(List<RecycleBean2_2> newList) {
        //刷新数据
        list.removeAll(list);
        list.addAll(newList);
        notifyDataSetChanged();
    }

    public class LinearViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle, txtLabel;
        private ImageView listImage;
        private View view;

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            txtTitle = itemView.findViewById(R.id.txt_place);
            txtLabel = itemView.findViewById(R.id.txt_label);
            listImage = itemView.findViewById(R.id.list2_image);

        }
    }
}
