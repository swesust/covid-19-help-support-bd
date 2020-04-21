package com.example.covid19shahajjo.helper;

import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.covid19shahajjo.activities.HelpCenterMapActivity;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineResult;

import java.lang.ref.WeakReference;

public class LocationChangeListeningActivityLocationCallback implements LocationEngineCallback<LocationEngineResult> {

    private final WeakReference<HelpCenterMapActivity> activityWeakReference;

    boolean chk = true;
    public static Location locationForNavigation;

    public LocationChangeListeningActivityLocationCallback(HelpCenterMapActivity activity) {
        this.activityWeakReference = new WeakReference<>(activity);
    }

    @Override
    public void onSuccess(LocationEngineResult result) {

        HelpCenterMapActivity activity = activityWeakReference.get();
        HelpCenterMapActivity.latitude = result.getLastLocation().getLatitude();
        HelpCenterMapActivity.longitude = result.getLastLocation().getLongitude();

            // Pass the new location to the Maps SDK's LocationComponent
            if (activity.mapboxMap != null && result.getLastLocation() != null) {
                activity.mapboxMap.getLocationComponent().forceLocationUpdate(result.getLastLocation());
            }

    }

    @Override
    public void onFailure(@NonNull Exception exception) {
        Log.d("LocationChangeActivity", exception.getLocalizedMessage());
        HelpCenterMapActivity activity = activityWeakReference.get();
        if (activity != null) {
            Toast.makeText(activity, exception.getLocalizedMessage(),
                    Toast.LENGTH_SHORT).show();
        }

    }
}
