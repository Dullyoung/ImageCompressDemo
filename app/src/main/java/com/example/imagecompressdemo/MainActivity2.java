package com.example.imagecompressdemo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity2 extends AppCompatActivity {

    @BindView(R.id.rv)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);

        List<String> strings = new ArrayList<>();
        for (int i = 0; i <= 20; i++) {
            strings.add("第" + (i + 1) + "个");
        }

        //创建adapter
        MyAdapter myAdapter = new MyAdapter(strings);
        //设置默认的布局方式
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3,RecyclerView.VERTICAL, false));
        //设置adapter
        mRecyclerView.setAdapter(myAdapter);
        //创建SimpleItemTouchHelperCallback
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(myAdapter);
        //用Callback构造ItemtouchHelper
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        //调用ItemTouchHelper的attachToRecyclerView方法建立联系
        touchHelper.attachToRecyclerView(mRecyclerView);
    }
}