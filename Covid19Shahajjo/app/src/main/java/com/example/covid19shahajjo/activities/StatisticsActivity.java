package com.example.covid19shahajjo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.covid19shahajjo.R;
import com.example.covid19shahajjo.helper.DeviceNetwork;
import com.example.covid19shahajjo.utils.Alert;
import com.example.covid19shahajjo.utils.AppWebViewClient;
import com.example.covid19shahajjo.utils.Enums;
import com.example.covid19shahajjo.utils.SharedStorge;

public class StatisticsActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        setUserPreferableTitle();
        layoutComponentMapping();
    }

    private void setUserPreferableTitle(){
        Enums.Language language = SharedStorge.getUserLanguage(this);
        if(language == Enums.Language.BD){
            String title = getResources().getString(R.string.statistics_title_bd);
            setTitle(title);
        }else{
            setTitle("Statistics");
        }
    }

    private void layoutComponentMapping(){
        if(!DeviceNetwork.isConnected(this)){
            showDialog();
            return;
        }
        webView = (WebView) findViewById(R.id.statistics_web_view);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        AppWebViewClient webViewClient = new AppWebViewClient(this);

        webView.setWebViewClient(webViewClient);
        webView.loadUrl(webViewClient.HOST_URL);
        Toast.makeText(this, "Page is loading. Please wait", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()){
            this.webView.goBack();;
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void showDialog(){
        Alert alert = new Alert(this);
        alert.show("No internet Connection", "Please turn on internet connection to continue");
    }
}
