package com.example.coolweather;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class web extends AppCompatActivity {
    private WebView webView;
    private ProgressBar progressBar;
    private WebChromeClient webChromeClient;
    private WebViewClient webViewClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web);

        progressBar=(ProgressBar) findViewById(R.id.progressbar);//进度条

        webView=(WebView) findViewById(R.id.webview);

        webView.loadUrl("http://www.cthy.com/");//加载url

        webChromeClient=new WebChromeClient(){
            //获取网页标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            //加载进度条
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
            }
        };

        webViewClient=new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {//页面加载完成
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {//页面开始加载
                progressBar.setVisibility(View.VISIBLE);
            }
        };

        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);//默认，根据cache-control决定是否从网上获取数据。
//        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);//不使用缓存，只从网络获取
        //支持屏幕缩放
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        //不显示缩放按钮
        webView.getSettings().setDisplayZoomControls(true);

        webView.setWebViewClient(new WebViewClient());

        webView.getSettings().setJavaScriptEnabled(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (webView.canGoBack() && keyCode == KeyEvent.KEYCODE_BACK){//点击返回按钮的时候判断有没有上一页
            webView.goBack(); // goBack()表示返回webView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
        webView=null;
    }
}
