package com.example.covid19shahajjo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid19shahajjo.R;
import com.example.covid19shahajjo.adapters.AreaArrayAdapter;
import com.example.covid19shahajjo.helper.PermissionChecker;
import com.example.covid19shahajjo.models.Area;
import com.example.covid19shahajjo.models.AreaWithContacts;
import com.example.covid19shahajjo.models.ContactsInfo;
import com.example.covid19shahajjo.services.ContactService;
import com.example.covid19shahajjo.services.ServiceCallback;
import com.example.covid19shahajjo.utils.Enums;
import com.example.covid19shahajjo.utils.PermissionManager;
import com.example.covid19shahajjo.utils.SharedStorge;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContactSupportActivity extends AppCompatActivity {

    private Spinner spinnerDivision, spinnerDistrict, spinnerSubDistrict;
    private TextView spinnerDivisionLabel, spinnerDistrictLabel, spinnerSubDistrictLabel;
    private ContactService service;
    private final  String LOGGER = "contact_support_log";
    private final String NoNumber = "No Number";
    private TextView contactUNO, contactPolice, contactCivilSurgeon, contactFireService;

    private List<Area> divisionList, districtList;
    private Area selectedDivision, selectedDistrict;
    private List<AreaWithContacts> subDistrictList;
    private AreaWithContacts selectedSubDistrict;

    private PermissionChecker permissionChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_support);
        setUserPreferableTitle();
        service = new ContactService();
        permissionChecker = new PermissionChecker();
        layoutComponentMapping();
        setUserPreferableLabels();
        setPhoneCallActions();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadDivisions();
    }

    private void setUserPreferableTitle(){
        Enums.Language language = SharedStorge.getUserLanguage(this);
        if(language == Enums.Language.BD){
            String title = getResources().getString(R.string.contact_support_title_bd);
            setTitle(title);
        }else{
            setTitle("Nearest Contact Support");
        }
    }

    private void layoutComponentMapping(){
        spinnerDivision = (Spinner) findViewById(R.id.contact_division_spinner);
        spinnerDistrict = (Spinner) findViewById(R.id.contact_district_spinner);
        spinnerSubDistrict = (Spinner) findViewById(R.id.contact_sub_district_spinner);

        spinnerDivisionLabel = (TextView) findViewById(R.id.contact_division_spinner_label);
        spinnerDistrictLabel = (TextView) findViewById(R.id.contact_district_spinner_label);
        spinnerSubDistrictLabel = (TextView) findViewById(R.id.contact_sub_district_spinner_label);

        contactCivilSurgeon = (TextView) findViewById(R.id.contact_area_civil_surgeon);
        contactFireService = (TextView) findViewById(R.id.contact_area_fire_service);
        contactPolice = (TextView) findViewById(R.id.contact_area_police);
        contactUNO = (TextView) findViewById(R.id.contact_area_uno);
    }

    private void setUserPreferableLabels(){
        Enums.Language language = SharedStorge.getUserLanguage(this);
        if(language == Enums.Language.BD){
            String[] labels = getResources().getStringArray(R.array.area_labels_bd);
            setAreaLabels(labels);
        }else{
            String[] labels = getResources().getStringArray(R.array.area_labels_en);
            setAreaLabels(labels);
        }
    }

    private void setAreaLabels(String[] labels){
        spinnerDivisionLabel.setText(labels[0]);
        spinnerDistrictLabel.setText(labels[1]);
        spinnerSubDistrictLabel.setText(labels[2]);
    }

    private void setPhoneCallActions() {
        contactCivilSurgeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contact = contactCivilSurgeon.getText().toString();
                makePhoneCall(contact);
            }
        });
        contactFireService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contact = contactFireService.getText().toString();
                makePhoneCall(contact);
            }
        });
        contactPolice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contact = contactPolice.getText().toString();
                makePhoneCall(contact);
            }
        });
        contactUNO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contact = contactUNO.getText().toString();
                makePhoneCall(contact);
            }
        });
    }

    private void makePhoneCall(String phoneNumber){
        if(!PermissionManager.hasPermission(this, Manifest.permission.CALL_PHONE)){
            permissionChecker.requestPhoneCallPermission(this);
            return;
        }
        if(phoneNumber == NoNumber){
            return;
        }
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:"+phoneNumber));
        startActivity(callIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == permissionChecker.PHONE_CALL_CODE){
            if(!PermissionManager.hasPermission(this, Manifest.permission.CALL_PHONE)){
                Toast.makeText(this, "Phone Call Permission is required", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadDivisions(){
        Logger.getLogger(LOGGER).log(Level.INFO, "load division called");
        Toast.makeText(this, "Divisions is loading", Toast.LENGTH_SHORT).show();
        service.getDivisions(new ServiceCallback<List<Area>>() {
            @Override
            public void onResult(List<Area> areaData) {
                displayOptions(areaData, Enums.AreaType.Division);
            }

            @Override
            public void onFailed(Exception exception) {
                Logger.getLogger(LOGGER).log(Level.INFO, "callback onFailed");
                failedMessage(exception);
            }
        });
    }

    private void loadDistricts(String divisionId){
        Logger.getLogger(LOGGER).log(Level.INFO, "load districts called");
        Toast.makeText(this, "Districts is loading", Toast.LENGTH_SHORT).show();
        service.getDistricts(divisionId,new ServiceCallback<List<Area>>() {
            @Override
            public void onResult(List<Area> areaData) {
                displayOptions(areaData, Enums.AreaType.District);
            }

            @Override
            public void onFailed(Exception exception) {
                Logger.getLogger(LOGGER).log(Level.INFO, "callback onFailed");
                failedMessage(exception);
            }
        });
    }

    private void loadSubDistricts(String divisionId, String districtId){
        Logger.getLogger(LOGGER).log(Level.INFO, "load subDistricts called");
        Toast.makeText(this, "Sub-Districts is loading", Toast.LENGTH_SHORT).show();
        service.getSubDistricts(divisionId,districtId,new ServiceCallback<List<AreaWithContacts>>() {
            @Override
            public void onResult(List<AreaWithContacts> areaData) {
                displayOptions(areaData, Enums.AreaType.SubDistrict);
            }

            @Override
            public void onFailed(Exception exception) {
                Logger.getLogger(LOGGER).log(Level.INFO, "callback onFailed");
                failedMessage(exception);
            }
        });
    }

    private void failedMessage(Exception ex){
        Logger.getLogger(LOGGER).log(Level.INFO, "failed message toast");
        Toast.makeText(this, "failed to fetch data", Toast.LENGTH_SHORT).show();
    }

    private void displayOptions(List list, Enums.AreaType areaType){
        Logger.getLogger(LOGGER).log(Level.INFO, "display called");
        if(areaType == Enums.AreaType.Division){
            divisionList = list;
            displayDivisionOptions(list);
        }
        else if(areaType == Enums.AreaType.District){
            Logger.getLogger(LOGGER).log(Level.INFO, "found districts: "+list.size());
            districtList = list;
            displayDistrictOptions(list);
        }
        else{
            subDistrictList = list;
            displaySubDistrictOptions(list);
        }
    }

    private void displayDivisionOptions(List<Area> list){
        AreaArrayAdapter adapter = new AreaArrayAdapter(this, R.layout.layout_area_list_item, list);
        spinnerDivision.setAdapter(adapter);
        spinnerDivision.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDivision = divisionList.get(position);
                Logger.getLogger(LOGGER).log(Level.INFO, "you selected: "+selectedDivision.Name);
                loadDistricts(selectedDivision.Id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void displayDistrictOptions(List<Area> list){
        AreaArrayAdapter adapter = new AreaArrayAdapter(this, R.layout.layout_area_list_item, list);
        spinnerDistrict.setAdapter(adapter);
        spinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDistrict = districtList.get(position);
                loadSubDistricts(selectedDivision.Id, selectedDistrict.Id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void displaySubDistrictOptions(List<AreaWithContacts> list){
        AreaArrayAdapter adapter = new AreaArrayAdapter(this, R.layout.layout_area_list_item, list);
        spinnerSubDistrict.setAdapter(adapter);
        spinnerSubDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSubDistrict = subDistrictList.get(position);
                informationRender(selectedSubDistrict.Contacts);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void informationRender(ContactsInfo contact){
        setContactNumber(contactUNO, contact.UNO == "" ? NoNumber : contact.UNO);
        setContactNumber(contactCivilSurgeon, contact.CivilSurgeon == "" ? NoNumber : contact.CivilSurgeon);
        setContactNumber(contactPolice, contact.Police == "" ? NoNumber : contact.Police);
        setContactNumber(contactFireService, contact.FireService == "" ? NoNumber : contact.FireService);
    }

    private void setContactNumber(TextView view, String value){
        view.setText(value);
    }

    @Override
    protected void onStop() {
        super.onStop();
        try{
            divisionList.clear();
            districtList.clear();
            subDistrictList.clear();
            selectedDivision = null;
            selectedDistrict = null;
            selectedSubDistrict = null;
        }catch (NullPointerException e){

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            Runtime.getRuntime().gc();
            finish();
        }catch (Exception e){
            clearVariables();
        }
    }

    private void clearVariables(){
        service = null;
        divisionList = null;
        districtList = null;
        subDistrictList = null;
        selectedDivision = null;
        selectedDistrict = null;
        selectedSubDistrict = null;
    }
}
