package com.example.covid19shahajjo.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AppWebViewClient extends WebViewClient {
    private Activity activity = null;
    public final String HOST_URL = "https://bdcovid19.herokuapp.com/";

    public AppWebViewClient(Activity activity){
        this.activity = activity;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url){
        if (url.indexOf(HOST_URL) > -1){
            return false;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(HOST_URL));
        activity.startActivity(intent);
        return true;
    }
}
