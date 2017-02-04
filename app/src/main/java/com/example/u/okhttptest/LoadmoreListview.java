package com.example.u.okhttptest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by u on 2017/1/30.
 */

public class LoadmoreListview extends ListView implements AbsListView.OnScrollListener {


    private boolean isLoading  = false;

    private View mFooterView;

    private int mFooterHeight;

    private OnLoadMoreListener mListener;
    private LayoutInflater inflater;

    public LoadmoreListview(Context context) {
        this(context, null);
    }

    public LoadmoreListview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadmoreListview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        initFooterView();
        setOnScrollListener(this);
    }

    /**
     * 初始化脚布局
     */
    private void initFooterView() {
        mFooterView = inflater.inflate(R.layout.refresh_load_more, null);
        mFooterView.measure(0, 0);
        mFooterHeight = mFooterView.getMeasuredHeight();
        mFooterView.setPadding(0, -mFooterHeight, 0, 0);
        this.addFooterView(mFooterView);
    }

    @Override public void onScrollStateChanged(AbsListView absListView, int scrollstate) {
        if(this.getLastVisiblePosition() == this.getAdapter().getCount() - 1
                && !isLoading && (scrollstate == SCROLL_STATE_FLING || scrollstate == SCROLL_STATE_IDLE)){
            setLoadState(true);
            if(this.mListener != null){
                this.mListener.loadMore();
            }
        }
    }

    /**
     * 设置状态
     * @param b
     */
    public void setLoadState(boolean b) {
        this.isLoading = b;
        if(isLoading){
            mFooterView.setPadding(0,0,0,0);
            this.setSelection(this.getAdapter().getCount() + 1);
        }else {
            mFooterView.setPadding(0,-mFooterHeight,0,0);
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener){
        this.mListener = listener;
    }

    public interface OnLoadMoreListener{
        void loadMore();
    }
}
