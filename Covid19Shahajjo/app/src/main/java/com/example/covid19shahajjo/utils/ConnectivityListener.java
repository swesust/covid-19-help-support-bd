package com.example.covid19shahajjo.utils;
import android.app.Application;

import com.example.covid19shahajjo.services.ConnectivityReceiver;

public class ConnectivityListener extends Application {
    private static ConnectivityListener mConnectivityListener;
    @Override
    public void onCreate() {
        super.onCreate();

        mConnectivityListener = this;
    }

    public static synchronized ConnectivityListener getInstance() {
        return mConnectivityListener;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}
