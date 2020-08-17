package com.example.appofzhejiang;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appofzhejiang.fragment3.Ticket;
import com.example.appofzhejiang.fragment3.TicketActivity;
import com.example.appofzhejiang.fragment3.TicketDetailActivity;

import java.util.List;

public class FragmentAdapter3_1 extends RecyclerView.Adapter<FragmentAdapter3_1.LinearViewHolder> {

    private View ticketView; // 用来做点击事件的
    private Context context;
    private String txt[] = {"门票", "酒店", "包租车", "导游预约", "农家乐", "美食", "特产购买"};
    private int image[] = {R.drawable.ticket, R.drawable.hotel, R.drawable.taxi_logo, R.drawable.guider_logo,
            R.drawable.nongjiale, R.drawable.food, R.drawable.commodity};

    public FragmentAdapter3_1(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public FragmentAdapter3_1.LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LinearViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_linear_adapter3_1, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, final int position) {
        Drawable topDrawable=null;

        //根据位置判断设置不同位置不同的文字和图片
        switch (position){
            case 0:
                holder.nameText.setText(txt[0]);
                topDrawable=context.getResources().getDrawable(image[0]);
                topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(), topDrawable.getMinimumHeight());
                holder.nameText.setCompoundDrawables(null,topDrawable,null,null);
                break;
            case 1:
                holder.nameText.setText(txt[1]);
                topDrawable=context.getResources().getDrawable(image[1]);
                topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(), topDrawable.getMinimumHeight());
                holder.nameText.setCompoundDrawables(null,topDrawable,null,null);
                break;

            case 2:
                holder.nameText.setText(txt[2]);
                topDrawable=context.getResources().getDrawable(image[2]);
                topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(), topDrawable.getMinimumHeight());
                holder.nameText.setCompoundDrawables(null,topDrawable,null,null);
                break;
            case 3:
                holder.nameText.setText(txt[3]);
                topDrawable=context.getResources().getDrawable(image[3]);
                topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(), topDrawable.getMinimumHeight());
                holder.nameText.setCompoundDrawables(null,topDrawable,null,null);
                break;
            case 4:
                holder.nameText.setText(txt[4]);
                topDrawable=context.getResources().getDrawable(image[4]);
                topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(), topDrawable.getMinimumHeight());
                holder.nameText.setCompoundDrawables(null,topDrawable,null,null);
                break;
            case 5:
                holder.nameText.setText(txt[5]);
                topDrawable=context.getResources().getDrawable(image[5]);
                topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(), topDrawable.getMinimumHeight());
                holder.nameText.setCompoundDrawables(null,topDrawable,null,null);
                break;
            case 6:
                holder.nameText.setText(txt[6]);
                topDrawable=context.getResources().getDrawable(image[6]);
                topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(), topDrawable.getMinimumHeight());
                holder.nameText.setCompoundDrawables(null,topDrawable,null,null);
                break;
        }

        //更具位置不同 传递不同的值到目标页面 从而跳转至不同页面
        holder.nameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TicketActivity.class);
                String s=Integer.toString(position);
                intent.putExtra("num", s);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return txt.length;
    }

    public View getTicketView() {
        return ticketView;
    }

    public void setTicketView(View ticketView) {
        this.ticketView = ticketView;
    }

    public class LinearViewHolder extends RecyclerView.ViewHolder {

        private TextView nameText;

        public LinearViewHolder(@NonNull View view) {
            super(view);
            setTicketView(view);
            nameText = view.findViewById(R.id.txt_menu);
        }
    }
}
