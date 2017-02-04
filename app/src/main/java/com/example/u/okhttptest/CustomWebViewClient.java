package com.example.u.okhttptest;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by u on 2017/1/28.
 */

public class CustomWebViewClient extends WebViewClient {


    private WebView webView;
    private boolean isRestart=false;

    public CustomWebViewClient(WebView webView) {

        this.webView = webView;
    }


    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);

    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);


        //编写 javaScript方法
        String javascript = "javascript:function hideOther() {" +
                "document.getElementsByTagName('body')[0].innerHTML;" +
                "document.getElementsByTagName('div')[0].style.display='none';" +
                "document.getElementsByTagName('div')[3].style.display='none';" +
                "document.getElementsByClassName('dropdown')[0].style.display='none';" +
                "document.getElementsByClassName('min')[0].remove();" +
                "var divs = document.getElementsByTagName('div');" +
                "var lastDiv = divs[divs.length-1];" +
                "lastDiv.remove();" +
                "document.getElementsByClassName('qr')[0].remove();" +
                "document.getElementsByClassName('nei-t3')[1].remove();}";



        //创建方法
        view.loadUrl(javascript);

        //加载方法
        view.loadUrl("javascript:hideOther();");

    }



}
