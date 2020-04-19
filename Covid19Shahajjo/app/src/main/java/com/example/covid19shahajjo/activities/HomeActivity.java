package com.example.covid19shahajjo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.covid19shahajjo.R;
import com.example.covid19shahajjo.adapters.HomeMenuAdapter;
import com.example.covid19shahajjo.services.ConnectivityReceiver;
import com.example.covid19shahajjo.utils.ConnectivityListener;
import com.example.covid19shahajjo.utils.Enums;
import com.example.covid19shahajjo.utils.SharedStorge;

import java.util.logging.Level;
import java.util.logging.Logger;


public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, ConnectivityReceiver.ConnectivityReceiverListener{

    private ListView menuView;
    private Enums.Language userLang;
    private HomeMenuAdapter adapter;

    private final int CONTACT_SUPPORT_POSITION = 0;
    private final int HEALTH_CENTER_POSITION = 1;
    private final int STATISTICS_POSITION = 2;
    private final int SETTINGS_POSITION = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setTitle("Home");
        checkPreconditions();
        layoutComponentMapping();

    }

    private void checkPreconditions(){
        Enums.Language language =  SharedStorge.getUserLanguage(this);
        if(language == Enums.Language.NONE){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        else{
            userLang = language;
        }
    }

    private void layoutComponentMapping(){
        menuView = (ListView) findViewById(R.id.home_menu_list);
        String[] menus = userPreferableMenu();
        adapter = new HomeMenuAdapter(this, R.layout.layout_home_menu_item, menus);
        menuView.setAdapter(adapter);
        menuView.setOnItemClickListener(this);
    }

    private String[] userPreferableMenu(){
        String menus[];
        if(userLang == Enums.Language.EN){
            menus = getResources().getStringArray(R.array.menu_en);
        }else{
            menus = getResources().getStringArray(R.array.menu_bd);
        }
       return menus;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        intentOnClickAction(position);
    }

    private void intentOnClickAction(int position){
        Intent intent;
        if(position == STATISTICS_POSITION){
            intent = new Intent(this, StatisticsActivity.class);
            startActivity(intent);
        }
        else if(position == SETTINGS_POSITION){
            intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        else if(position == HEALTH_CENTER_POSITION){
            intent = new Intent(this, HelpCenterMap.class);
            startActivity(intent);
        }
        else if(position == CONTACT_SUPPORT_POSITION){
            intent = new Intent(this, ContactSupport.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        ConnectivityReceiver connectivityReceiver = new ConnectivityReceiver();
        registerReceiver(connectivityReceiver, intentFilter);
        // register connection status listener
        ConnectivityListener.getInstance().setConnectivityListener(this);
    }
    //Auto triggers when Network Changed
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if(isConnected==false)
           { showDialouge();}
    }
    public void showDialouge(){
       try{
           AlertDialog.Builder builder =new AlertDialog.Builder(this);
           builder.setTitle("No internet Connection");
           builder.setMessage("Please turn on internet connection to continue");
           builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                   dialog.dismiss();
               }
           });
           AlertDialog alertDialog = builder.create();
           alertDialog.show();
       }catch(Error error){
           Log.e("ERROR","Error "+error.getMessage());
       }
    }
}
