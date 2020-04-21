package com.example.covid19shahajjo.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.example.covid19shahajjo.R;
import com.example.covid19shahajjo.helper.CameraChange;
import com.example.covid19shahajjo.helper.DeviceNetwork;
import com.example.covid19shahajjo.helper.LocationChangeListeningActivityLocationCallback;
import com.example.covid19shahajjo.models.HealthCenter;
import com.example.covid19shahajjo.services.HospitalService;
import com.example.covid19shahajjo.services.ServiceCallback;
import com.example.covid19shahajjo.utils.Alert;
import com.example.covid19shahajjo.utils.PermissionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.building.BuildingPlugin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


public class HelpCenterMapActivity extends AppCompatActivity implements OnMapReadyCallback, PermissionsListener, View.OnClickListener {

    private MapView mapView;
    public static MapboxMap mapboxMap;
    private BuildingPlugin buildingPlugin;
    private PermissionsManager permissionsManager;
    private FloatingActionButton myLocation;
    public static Double latitude, longitude;

    public static LocationEngine locationEngine;
    public static final long DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L;
    public static final long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5;
    List<MarkerOptions> markerOptions = new ArrayList<>();
    private LocationChangeListeningActivityLocationCallback callback = new LocationChangeListeningActivityLocationCallback(this);

    private HospitalService hospitalService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.access_token));
        setContentView(R.layout.activity_help_center_map);
        layoutComponentMapping(savedInstanceState);
        checkPreconditionsAndLoadData();
    }

    private void layoutComponentMapping(Bundle savedInstanceState){
        myLocation = findViewById(R.id.mylocation);
        myLocation.setOnClickListener(this);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    private void checkPreconditionsAndLoadData(){
        if(!PermissionManager.hasPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)){
           Toast.makeText(this, "Allow Permission To Access Location", Toast.LENGTH_SHORT).show();
           return;
        }
        if(!DeviceNetwork.isGPSActive(this)){
            showGPSDisabledAlert();
            return;
        }
        getHospitalInfo();
    }

    public void getHospitalInfo(){
        hospitalService = new HospitalService();
        hospitalService.getHospitals(new ServiceCallback<List<HealthCenter>>() {
            @Override
            public void onResult(List<HealthCenter> list) {
                markLocationOnMap(list);
            }

            @Override
            public void onFailed(Exception exception) {
                Toast.makeText(getApplicationContext(), "Failed to load hospitals information", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showGPSDisabledAlert(){
        Alert alert = new Alert(this);
        alert.show("GPS Service", "Please turn on your GPS");
    }

    private void markLocationOnMap(List<HealthCenter> healthCenters){
        for(HealthCenter center : healthCenters){
            markerOptions.add(getMark(center));
        }
        mapboxMap.addMarkers(markerOptions);
    }

    private MarkerOptions getMark(HealthCenter center){
        return new MarkerOptions()
                .position(new LatLng(center.Location.latitude, center.Location.longitude))
                .setTitle(center.Name+"\n"+center.Address);
    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                buildingPlugin = new BuildingPlugin(mapView, mapboxMap, style);
                buildingPlugin.setMinZoomLevel(15f);
                buildingPlugin.setVisibility(true);
                enableLocationComponent(style);
            }
        });
    }

    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            LocationComponent locationComponent = mapboxMap.getLocationComponent();
            locationComponent.activateLocationComponent(
                    LocationComponentActivationOptions.builder(this, loadedMapStyle).build());
            locationComponent.setLocationComponentEnabled(true);
            locationComponent.setCameraMode(CameraMode.TRACKING);
            locationComponent.setRenderMode(RenderMode.COMPASS);
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @SuppressLint("MissingPermission")
    public void initLocationEngine() {
        locationEngine = LocationEngineProvider.getBestLocationEngine(this);
        LocationEngineRequest request = new LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
                .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME).build();

        locationEngine.requestLocationUpdates(request, callback, getMainLooper());
        locationEngine.getLastLocation(callback);
    }

    // Add the mapView lifecycle to the activity's lifecycle methods
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {

    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        if(!DeviceNetwork.isGPSActive(this)){
            showGPSDisabledAlert();
            return;
        }
        initLocationEngine();
        CameraChange.setCameraPosition(latitude, longitude);
    }
}




