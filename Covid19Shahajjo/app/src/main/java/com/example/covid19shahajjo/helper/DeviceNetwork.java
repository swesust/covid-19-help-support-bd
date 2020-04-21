package com.example.covid19shahajjo.helper;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class DeviceNetwork {
    public static boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return isMobileNetworkConnected(connectivityManager) || isWiFiConnected(connectivityManager);
    }

    public static boolean isMobileNetworkConnected(ConnectivityManager connectivityManager){
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED;
    }

    public static boolean isWiFiConnected(ConnectivityManager connectivityManager){
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
    }

    public static boolean isGPSActive(Context context){
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE );
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
}
