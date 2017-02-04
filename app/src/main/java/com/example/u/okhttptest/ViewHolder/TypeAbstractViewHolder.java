package com.example.u.okhttptest.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by u on 2017/1/28.
 */

public abstract class TypeAbstractViewHolder<T> extends RecyclerView.ViewHolder{

    public TypeAbstractViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bindHolder(T model);
}


