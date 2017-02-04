package com.example.u.okhttptest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.u.okhttptest.Bean.ZhihuBean;
import com.example.u.okhttptest.ViewHolder.ZhihuViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by u on 2017/1/27.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ZhihuBean> list=new ArrayList<>();
    private LayoutInflater layoutInflater;

    public MyRecyclerViewAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
    }


    public void addList(List<ZhihuBean> list) {

        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ZhihuViewHolder(layoutInflater.inflate(R.layout.item_zhihu, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((ZhihuViewHolder)holder).bindHolder(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
