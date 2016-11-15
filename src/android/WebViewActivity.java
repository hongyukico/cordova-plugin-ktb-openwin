package com.ktb.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import org.apache.cordova.Config;
import org.apache.cordova.CordovaActivity;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaWebViewImpl;
import org.apache.cordova.engine.SystemWebChromeClient;
import org.apache.cordova.engine.SystemWebView;
import org.apache.cordova.engine.SystemWebViewEngine;

import io.cordova.hellocordova.R;


/**
 * 类描述：动态获取地址显示网页
 * 创建人：HONGYU.LIU
 * 创建时间： 2016/5/6
 * 修改人：HONGYU.LIU
 * 修改时间：2016/5/6 15:50
 * 修改备注：
 */
public class WebViewActivity extends CordovaActivity {
    public String webUrl;
    public SystemWebView webView;
    SystemWebViewEngine systemWebViewEngine;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        super.init();
        String top = "file:///android_asset/www/";
        String url = "";
        if (getIntent().hasExtra("redirect_url"))
            url = getIntent().getExtras().getString("redirect_url");
        if (url == null || url.equals(""))
            url = Config.getStartUrl();
        else url = top + url;
        // 初始化Webview后添加到ＵＩ中
        webUrl = url;
        webView = (SystemWebView) appView.getView();
        webView.getSettings().setJavaScriptEnabled(true);
        WebSettings set = webView.getSettings();
        set.setJavaScriptEnabled(true);
        webView.setWebChromeClient(new SystemWebChromeClient(systemWebViewEngine) {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.d("ANDROID_LAB", "TITLE=" + title);
                ((TextView) findViewById(R.id.top_title)).setText(title);

            }
        });
        loadUrl(url);

    }


    @Override
    protected CordovaWebView makeWebView() {
        SystemWebView webView = (SystemWebView) findViewById(R.id.cordovaWebView);
        systemWebViewEngine = new SystemWebViewEngine(webView);
        return new CordovaWebViewImpl(systemWebViewEngine);
    }

    @Override
    protected void createViews() {
        if (preferences.contains("BackgroundColor")) {
            int backgroundColor = preferences.getInteger("BackgroundColor", Color.BLACK);
            appView.getView().setBackgroundColor(backgroundColor);
        }
        appView.getView().requestFocusFromTouch();

    }


    public void onBack(View view) {
        this.finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
