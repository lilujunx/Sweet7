package com.example.administrator.sweet7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    private WebView mWeb;
    private WebSettings mSettings;
    private String url="http://192.168.3.112:8080/Ticket/selectticket";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mWeb = (WebView) findViewById(R.id.web);
        mSettings = mWeb.getSettings();
        mWeb.loadUrl(url);
        mSettings.setJavaScriptEnabled(true);
        mWeb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                super.shouldOverrideUrlLoading(view, url);
                mWeb.loadUrl(url);
                return true;
            }
        });
    }
}
