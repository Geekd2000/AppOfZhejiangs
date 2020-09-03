package com.example.appofzhejiang.Business;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appofzhejiang.CustomDialog.CustomDialog;
import com.example.appofzhejiang.R;
import com.example.appofzhejiang.fragment3.Ticket;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.LinearViewHolder> {

    private Context mContext;
    private List<AddressBean> addressData;//收货信息

    public ReceiptAdapter(List<AddressBean> addressData, Context context) {
        this.mContext = context;
        this.addressData = addressData;
    }

    @NonNull
    @Override
    public LinearViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_receipt, parent, false);
        final LinearViewHolder linearViewHolder = new LinearViewHolder(view);
        return linearViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LinearViewHolder holder, final int position) {
        final AddressBean addressBean=addressData.get(position);
        if (!addressBean.getDefault()) {
            holder.toleration.setVisibility(View.INVISIBLE);
        } else {
            holder.toleration.setVisibility(View.VISIBLE);
        }
        holder.username.setText(addressBean.getName());
        holder.phone.setText(addressBean.getMobile());
        holder.address.setText(addressBean.getAddress());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomDialog customDialog = new CustomDialog(mContext);
                customDialog.setTitle("提示").setMessage("确定要删除该地址吗？")
                        .setCancel("取消", new CustomDialog.IOnCancelListener() {
                            @Override
                            public void onCancel(CustomDialog dialog) {
                                // 取消删除
                            }
                        }).setConfirm("确定", new CustomDialog.IOnConfirmListener() {
                    @Override
                    public void onConfirm(CustomDialog dialog) {
                        // 确定删除 删除记录
                        runDelete(String.valueOf(addressBean.getId()));
                        //删除自带默认动画
                        removeData(position);
                    }
                }).show();
            }
        });
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,AddressActivity.class);
                intent.putExtra("id",String.valueOf(addressBean.getId()));
                intent.putExtra("num",1);
                mContext.startActivity(intent);
            }
        });
    }

    //向服务器发送put请求,修改默认地址
    private void runDelete(String id) {
        String url = "http://120.26.172.104:9002//wx/deleteAddress";
        RequestBody formBody = new FormBody.Builder()
                .add("id", id)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .delete(formBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("TAG", "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("TAG", response.protocol() + " " +response.code() + " " + response.message());
                Headers headers = response.headers();
                for (int i = 0; i < headers.size(); i++) {
                    Log.d("TAG", headers.name(i) + ":" + headers.value(i));
                }
                Log.d("TAG", "onResponse: " + response.body().string());
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressData.size();
    }

    public void refresh(List<AddressBean> newList) {
        //刷新数据
        addressData.removeAll(addressData);
        addressData.addAll(newList);
        notifyDataSetChanged();
    }

    /*// 添加数据
    public void addData(int position) {
        //在list中添加数据，并通知条目加入一条
        addressData.add(position, addressBean);
        //添加动画
        notifyItemInserted(position);
    }
*/
    // 删除数据
    public void removeData(int position) {
        addressData.remove(position);
        //删除动画
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public class LinearViewHolder extends RecyclerView.ViewHolder {

        private TextView username, phone, address, toleration,update;
        private Button delete;

        public LinearViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.receipt_username);
            phone = itemView.findViewById(R.id.receipt_phone);
            address = itemView.findViewById(R.id.receipt_address);
            toleration = itemView.findViewById(R.id.receipt_default);
            delete = itemView.findViewById(R.id.receipt_delete);
            update = itemView.findViewById(R.id.update);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //可以选择直接在本位置直接写业务处理
                    //Toast.makeText(context,"点击了xxx",Toast.LENGTH_SHORT).show();
                    //此处回传点击监听事件
                    if (onItemClickListener != null) {
                        onItemClickListener.OnItemClick(v, addressData.get(getAdapterPosition()));
                    }
                }
            });
        }
    }

    //设置item的监听事件的接口
    public interface OnItemClickListener {
        /*  *
         * 接口中的点击每一项的实现方法，参数自己定义
         *
         * @param view 点击的item的视图
         * @param data 点击的item的数据*/

        public void OnItemClick(View view, AddressBean addressBean);
    }

    //需要外部访问，所以需要设置set方法，方便调用
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    //item的监听事件的接口end

}
