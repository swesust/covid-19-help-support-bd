package com.example.covid19shahajjo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.covid19shahajjo.R;
import com.example.covid19shahajjo.utils.AppWebViewClient;

public class StatisticsActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        layoutComponentMapping();
    }

    private void layoutComponentMapping(){
        webView = (WebView) findViewById(R.id.statistics_web_view);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        AppWebViewClient webViewClient = new AppWebViewClient(this);

        webView.setWebViewClient(webViewClient);
        webView.loadUrl(webViewClient.HOST_URL);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()){
            this.webView.goBack();;
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
