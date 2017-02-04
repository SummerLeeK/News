package com.example.u.okhttptest.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.u.okhttptest.Bean.ZhihuBean;
import com.example.u.okhttptest.R;

/**
 * Created by u on 2017/1/28.
 */

public class ZhihuViewHolder extends TypeAbstractViewHolder<ZhihuBean>{

    private ImageView imageView;
    private TextView title;

    public ZhihuViewHolder(View itemView) {
        super(itemView);

        imageView= (ImageView) itemView.findViewById(R.id.image);
        title= (TextView) itemView.findViewById(R.id.title_zhihu);
    }

    @Override
    public void bindHolder(ZhihuBean model) {

        title.setText(model.getTitle());

    }



}
