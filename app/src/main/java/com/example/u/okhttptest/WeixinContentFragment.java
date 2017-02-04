package com.example.u.okhttptest;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.u.okhttptest.Bean.WeixinBean;
import com.example.u.okhttptest.Bean.ZhihuBean;
import com.example.u.okhttptest.Http.DataUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by u on 2017/1/31.
 */

public class WeixinContentFragment extends Fragment implements AdapterView.OnItemClickListener, AbsListView.OnScrollListener, LoadmoreListview.OnLoadMoreListener {
    private List<WeixinBean> data;
    private Map<String, Bitmap> images;
    private MyBaseAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private boolean isRefresh = false;
    private static int PAGE = 1;
    private LoadmoreListview listview;

    private Context context;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    PAGE+=1;
                    isRefresh = false;
                    adapter.addRefreshList(data);
                    adapter.notifyDataSetChanged();
                    refreshLayout.setRefreshing(false);

                    break;

                case 101:
                    Snackbar.make(refreshLayout, "连接服务器失败！", Snackbar.LENGTH_SHORT).show();
                    isRefresh = false;
                    refreshLayout.setRefreshing(false);
                    break;

                case 110:
                    PAGE+=1;
                    List<WeixinBean> more= (List<WeixinBean>) msg.obj;
                    adapter.addLoadMoreList(more);
                    adapter.notifyDataSetChanged();
                    listview.setLoadState(false);
                    break;
            }


        }
    };

    public WeixinContentFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        data = new ArrayList<>();
        images = new HashMap<>();
        adapter = new MyBaseAdapter(context);
         listview = (LoadmoreListview) view.findViewById(R.id.listview);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
        listview.setOnLoadMoreListener(this);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.SwipeRefresh);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);

        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
                isRefresh = true;
                PAGE = 1;
                OkHttpGetData();
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isRefresh == false) {
                    isRefresh = true;

                    data.clear();
                    PAGE = 1;
                    OkHttpGetData();
                } else {
                    Snackbar.make(refreshLayout, "已经刷新过了！", Snackbar.LENGTH_SHORT).show();
                }

            }
        });


        return view;
    }


    private void OkHttpGetData() {

        listview.setLoadState(true);
        Request request = new Request.Builder().url(DataUrl.WEIXIN_URL + PAGE).build();

        Log.i("url","string"+DataUrl.WEIXIN_URL + PAGE);

        OkHttpClient client = new OkHttpClient();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(101);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String res = response.body().string();
                try {
                    JSONObject json = new JSONObject(res);
                    JSONArray jsonarr = json.getJSONArray("newslist");
                    for (int i = 0; i < jsonarr.length(); i++) {
                        JSONObject one = jsonarr.getJSONObject(i);
                        WeixinBean weixinBean = new WeixinBean();
                        weixinBean.setTitle(one.getString("title"));
                        weixinBean.setUrl(one.getString("url"));
                        weixinBean.setPicUrl(one.getString("picUrl"));
                        Bitmap bitmap = getGossipImage(weixinBean.getPicUrl());

                        images.put(weixinBean.getUrl(), bitmap);




                        data.add(weixinBean);
                    }

                    Log.i("zhihu", "list" + data);

                    handler.sendEmptyMessage(100);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //   Log.i("zhihu", "data" + res);

            }
        });
    }

    private void LoadMore() {

        isRefresh=true;
        final List<WeixinBean> more = new ArrayList<>();
        Request request = new Request.Builder().url(DataUrl.WEIXIN_URL + PAGE).build();

        Log.i("url","string"+DataUrl.WEIXIN_URL + PAGE);

        OkHttpClient client = new OkHttpClient();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.sendEmptyMessage(101);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String res = response.body().string();
                try {
                    JSONObject json = new JSONObject(res);

                    JSONArray jsonarr = json.getJSONArray("newslist");
                    for (int i = 0; i < jsonarr.length(); i++) {
                        JSONObject one = jsonarr.getJSONObject(i);
                        WeixinBean weixinBean = new WeixinBean();
                        weixinBean.setTitle(one.getString("title"));
                        weixinBean.setUrl(one.getString("url"));
                        weixinBean.setPicUrl(one.getString("picUrl"));
                        Bitmap bitmap = getGossipImage(weixinBean.getPicUrl());

                        images.put(weixinBean.getUrl(), bitmap);


                        Log.i("url", "Bean" + weixinBean.toString());

                        more.add(weixinBean);
                    }
                    Message msg = new Message();
                    msg.what = 110;
                    msg.obj = more;

                    handler.sendMessage(msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //   Log.i("zhihu", "data" + res);

            }
        });


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Bundle bundle = new Bundle();
        bundle.putInt("type", DataUrl.TYPE_WEIXIN);
        bundle.putString("url", data.get(position).getUrl());
        bundle.putString("title", data.get(position).getTitle());

        Intent intent = new Intent(this.getActivity(), DetailActivity.class);
        intent.putExtra("data", bundle);
        startActivity(intent);


    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public void loadMore() {


        LoadMore();


    }

    public class MyBaseAdapter extends BaseAdapter {
        private List<WeixinBean> list = new ArrayList<>();
        private LayoutInflater layoutInflater;
        private ImageView iv;
        private TextView title;

        public MyBaseAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
        }


        private void addRefreshList(List<WeixinBean> list) {
            this.list = list;
        }

        private void addLoadMoreList(List<WeixinBean> list) {
            this.list.addAll(list);
        }


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.item_zhihu, parent, false);
            }

            iv = (ImageView) convertView.findViewById(R.id.image);
            title = (TextView) convertView.findViewById(R.id.title_zhihu);

            iv.setImageBitmap(images.get(list.get(position).getUrl()));

            title.setText(list.get(position).getTitle());


            return convertView;
        }
    }

    public static Bitmap getGossipImage(String url) {

        OkHttpClient client = new OkHttpClient();
        Bitmap bitmap;
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            InputStream is = response.body().byteStream();
            bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }


}


