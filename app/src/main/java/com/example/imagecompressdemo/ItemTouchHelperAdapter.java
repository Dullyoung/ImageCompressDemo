package com.example.imagecompressdemo;

import androidx.recyclerview.widget.RecyclerView;

/*
 * Created by　Dullyoung on 2020/11/3
 */
public interface ItemTouchHelperAdapter {

    //数据交换
    void onItemMove(RecyclerView.ViewHolder source, RecyclerView.ViewHolder target);

    //数据删除
    void onItemDelete(RecyclerView.ViewHolder source);

    //drag或者swipe选中
    void onItemSelect(RecyclerView.ViewHolder source);

    //状态清除
    void onItemClear(RecyclerView.ViewHolder source);
}