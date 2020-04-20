package com.example.covid19shahajjo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.covid19shahajjo.R;
import com.example.covid19shahajjo.adapters.HomeMenuAdapter;
import com.example.covid19shahajjo.helper.DeviceNetwork;
import com.example.covid19shahajjo.helper.PermissionChecker;
import com.example.covid19shahajjo.services.ConnectivityReceiver;
import com.example.covid19shahajjo.utils.Alert;
import com.example.covid19shahajjo.utils.ConnectivityListener;
import com.example.covid19shahajjo.utils.Enums;
import com.example.covid19shahajjo.utils.PermissionManager;
import com.example.covid19shahajjo.utils.SharedStorge;


public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,
        ConnectivityReceiver.ConnectivityReceiverListener{

    private ListView menuView;
    private Enums.Language userLang;
    private HomeMenuAdapter adapter;

    private final int CONTACT_SUPPORT_POSITION = 0;
    private final int HEALTH_CENTER_POSITION = 1;
    private final int STATISTICS_POSITION = 2;
    private final int SETTINGS_POSITION = 3;

    private PermissionChecker permissionChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setUserPreferableTitle();
        checkPreconditions();
        checkAppPermission();
        layoutComponentMapping();

    }

    private void setUserPreferableTitle(){
        Enums.Language language = SharedStorge.getUserLanguage(this);
        if(language == Enums.Language.BD){
            String title = getResources().getString(R.string.home_title_bd);
            setTitle(title);
        }else{
            setTitle("COVID-19 Shahajjo");
        }
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
        if(position == STATISTICS_POSITION){
            goPageIfConnected(StatisticsActivity.class);
        }
        else if(position == SETTINGS_POSITION){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            finish();
        }
        else if(position == HEALTH_CENTER_POSITION){
            goPageIfConnected(HelpCenterMapActivity.class);
        }
        else if(position == CONTACT_SUPPORT_POSITION){
            goPageIfConnected(ContactSupportActivity.class);
        }
    }

    private void goPageIfConnected(Class<?> destinationClass){
        if(!DeviceNetwork.isConnected(this)){
            showDialog();
            return;
        }
        Intent intent = new Intent(this, destinationClass);
        startActivity(intent);
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
        if(isConnected==false) {
            showDialog();
        }
    }
    public void showDialog(){
        Alert alert = new Alert(this);
        alert.show("No internet Connection", "Please turn on internet connection to continue");
    }

    private void checkAppPermission(){
        permissionChecker = new PermissionChecker();
        if(!PermissionManager.hasPermission(this, Manifest.permission.INTERNET)){
            permissionChecker.requestInternetPermission(this);
        }
        if(!PermissionManager.hasPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            permissionChecker.requestFineLocationPermission(this);
        }
        if(!PermissionManager.hasPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)){
            permissionChecker.requestCoarseLocationPermission(this);
        }
        if(!PermissionManager.hasPermission(this, Manifest.permission.ACCESS_NETWORK_STATE)){
            permissionChecker.requestAccessNetworkStatePermission(this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == permissionChecker.INTERNET_CODE){
            if(!PermissionManager.hasPermission(this, Manifest.permission.INTERNET)){
                permissionChecker.requestInternetPermission(this);
            }
        }
        else if(requestCode == permissionChecker.ACCESS_NETWORK_STATE_CODE){
            if(!PermissionManager.hasPermission(this, Manifest.permission.ACCESS_NETWORK_STATE)){
                permissionChecker.requestAccessNetworkStatePermission(this);
            }
        }
        else if(requestCode == permissionChecker.ACCESS_FINE_LOCATION_CODE){
            if(!PermissionManager.hasPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                permissionChecker.requestFineLocationPermission(this);
            }
        }
        else if(requestCode == permissionChecker.ACCESS_COARSE_LOCATION_CODE){
            if(!PermissionManager.hasPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)){
                permissionChecker.requestCoarseLocationPermission(this);
            }
        }
    }
}
