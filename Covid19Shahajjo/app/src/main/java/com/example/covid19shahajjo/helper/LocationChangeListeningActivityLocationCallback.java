package com.example.covid19shahajjo.helper;

import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.covid19shahajjo.activities.HelpCenterMap;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineResult;

import java.lang.ref.WeakReference;

public class LocationChangeListeningActivityLocationCallback implements LocationEngineCallback<LocationEngineResult> {

    private final WeakReference<HelpCenterMap> activityWeakReference;

    boolean chk = true;
    public static Location locationForNavigation;

    public LocationChangeListeningActivityLocationCallback(HelpCenterMap activity) {
        this.activityWeakReference = new WeakReference<>(activity);
    }

    @Override
    public void onSuccess(LocationEngineResult result) {

        HelpCenterMap activity = activityWeakReference.get();
        HelpCenterMap.latitude = result.getLastLocation().getLatitude();
        HelpCenterMap.longitude = result.getLastLocation().getLongitude();

            // Pass the new location to the Maps SDK's LocationComponent
            if (activity.mapboxMap != null && result.getLastLocation() != null) {
                activity.mapboxMap.getLocationComponent().forceLocationUpdate(result.getLastLocation());
            }

    }

    @Override
    public void onFailure(@NonNull Exception exception) {
        Log.d("LocationChangeActivity", exception.getLocalizedMessage());
        HelpCenterMap activity = activityWeakReference.get();
        if (activity != null) {
            Toast.makeText(activity, exception.getLocalizedMessage(),
                    Toast.LENGTH_SHORT).show();
        }

    }
}
