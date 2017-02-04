package com.example.u.okhttptest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.u.okhttptest.Http.DataUrl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by u on 2017/1/28.
 */

public class DetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private WebView webView;
    private Request request;
    private OkHttpClient client;
    private Detail detail;
    private String mCurrentUrl;
    private FloatingActionButton actionButton;
    private NestedScrollView scrollView;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            webView.loadUrl(detail.share_url);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhihudetail_main);

        initView();
    }

    private void initView() {

        actionButton = (FloatingActionButton) findViewById(R.id.fab);
        scrollView = (NestedScrollView) findViewById(R.id.scrollView);
        webView = (WebView) findViewById(R.id.webdetail);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);

        final Bundle bundle = getIntent().getBundleExtra("data");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(bundle.getString("title"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        switch (bundle.getInt("type")) {
            case DataUrl.TYPE_ZHIHU:
                int id = bundle.getInt("id");
                String url = DataUrl.ZHIHU_DETAIL + String.valueOf(id);
                Log.i("zhihu", "detail" + url);
                request = new Request.Builder().url(url).build();
                client = new OkHttpClient();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        String data = response.body().string();

                        try {
                            JSONObject js = new JSONObject(data);
                            detail = new Detail();
                            detail.image = js.getString("image");
                            detail.share_url = js.getString("share_url");
                            detail.title = js.getString("title");
                            mCurrentUrl = detail.share_url;

                            handler.sendEmptyMessage(100);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


                webView.setWebViewClient(new CustomWebViewClient(webView));

                break;

            case DataUrl.TYPE_WEIXIN:

                detail = new Detail();
                String url_weixin = bundle.getString("url");

                detail.share_url = url_weixin;
                webView.setWebViewClient(new WebViewClient());
                webView.loadUrl(url_weixin);


                break;
        }


        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, bundle.getString("title") + "   " + detail.share_url + "来自"+R.string.app_name+"android客户端！");
                shareIntent.setType("text/plain");

                //设置分享列表的标题，并且每次都显示分享列表
                startActivity(Intent.createChooser(shareIntent, "分享到"));
            }
        });





    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    public class Detail {
        public String share_url;
        public String image;
        public String title;
    }
}
