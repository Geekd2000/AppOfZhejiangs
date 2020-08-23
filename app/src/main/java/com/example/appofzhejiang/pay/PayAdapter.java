package com.example.appofzhejiang.pay;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appofzhejiang.R;
import com.example.appofzhejiang.fragment3.Ticket;
import com.example.appofzhejiang.fragment3.TicketDetailActivity;

import java.util.List;

public class PayAdapter extends RecyclerView.Adapter<PayAdapter.LinearViewHolder> {

    private Context mContext;
    private List<FileList> mDates;

    public PayAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(mContext).inflate(R.layout.layout_pay_message, parent, false);
        final LinearViewHolder linearViewHolder= new LinearViewHolder(view);
        /*// 注册点击事件 start
        linearViewHolder.payView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //int position = linearViewHolder.getAdapterPosition();
                linearViewHolder.mIvPicture.setDrawingCacheEnabled(true);
                Bitmap bitMap = Bitmap.createBitmap(linearViewHolder.mIvPicture.getDrawingCache());
                linearViewHolder.mIvPicture.setDrawingCacheEnabled(false);
                Intent intent = new Intent(mContext, OrderActivity.class);
                intent.putExtra("goodsName", linearViewHolder.mTvMessage.getText().toString());
                intent.putExtra("goodsType", linearViewHolder.mTvType.getText().toString());
                intent.putExtra("goodsUnitPrice", linearViewHolder.nTvMoney1.getText().toString());
                intent.putExtra("goodsAmount", linearViewHolder.mTvNumber.getText().toString());
                intent.putExtra("goodsPay", linearViewHolder.mTvMoney.getText().toString());
                intent.putExtra("goodsImage", bitMap);
                mContext.startActivity(intent);
            }
        });
        // 注册点击事件 end*/
        return linearViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class LinearViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvOrder, mTvMessage, mTvNumber, mTvMoney, nTvMoney1, mTvStatus,mTvType;
        private ImageView mIvPicture;
        private View payView;

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            payView = itemView;
            mTvMessage = itemView.findViewById(R.id.iv_message);
            mTvType= itemView.findViewById(R.id.iv_message_1);
            mTvOrder = itemView.findViewById(R.id.order_id);
            mTvNumber = itemView.findViewById(R.id.number);
            mTvMoney = itemView.findViewById(R.id.money);
            nTvMoney1 = itemView.findViewById(R.id.money1);
            mIvPicture = itemView.findViewById(R.id.iv_picture);
            mTvStatus = itemView.findViewById(R.id.status);
        }
    }
}
