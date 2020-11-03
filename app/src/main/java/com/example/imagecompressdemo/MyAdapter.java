package com.example.imagecompressdemo;

/*
 * Created by　Dullyoung on 2020/11/3
 */

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements ItemTouchHelperAdapter {

    public MyAdapter(List<String> data) {
        mData = data;
    }

    List<String> mData;

    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_img, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {

        holder.mTextView.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        //注意:这里最少有一个,因为有多了一个添加按钮
        return mData.size();
    }

    @Override
    public void onItemMove(RecyclerView.ViewHolder source,
                           RecyclerView.ViewHolder target) {
        int fromPosition = source.getAdapterPosition();
        int toPosition = target.getAdapterPosition();
        if (fromPosition < mData.size() && toPosition < mData.size()) {
            //交换数据位置
            Collections.swap(mData, fromPosition, toPosition);
            //刷新位置交换
            notifyItemMoved(fromPosition, toPosition);
        }
        //移动过程中移除view的放大效果
        onItemClear(source);
    }

    @Override
    public void onItemDelete(RecyclerView.ViewHolder source) {
        int position = source.getAdapterPosition();
        mData.remove(position); //移除数据
        notifyItemRemoved(position);//刷新数据移除
    }


    @Override
    public void onItemSelect(RecyclerView.ViewHolder viewHolder) {

        //当拖拽选中时放大选中的view
        viewHolder.itemView.setScaleX(1.2f);
        viewHolder.itemView.setScaleY(1.2f);
    }

    @Override
    public void onItemClear(RecyclerView.ViewHolder viewHolder) {

        //拖拽结束后恢复view的状态
        viewHolder.itemView.setScaleX(1.0f);
        viewHolder.itemView.setScaleY(1.0f);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.tv1);
        }
    }

}
