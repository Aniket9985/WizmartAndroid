package com.wiz.wizmart.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wiz.wizmart.R;


public class PoliciesActivity extends AppCompatActivity {
    private WebView webView;
    String url="https://wiz-mart.com/privacy.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_policies );
        webView=findViewById ( R.id.webViewP );
        webView.getSettings ().setJavaScriptEnabled ( true );
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient ( new WebViewClient () );
        webView.loadUrl ( url );
    }
}