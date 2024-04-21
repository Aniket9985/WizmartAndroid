package com.wiz.wizmart.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wiz.wizmart.R;


public class RefundActivity extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_refund );
        webView=findViewById ( R.id.webViewR );
        webView.getSettings ().setJavaScriptEnabled ( true );
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient ( new WebViewClient () );
        webView.loadUrl ("https://wiz-mart.com/refundncancel.html" );
    }
}