package com.example.covid19shahajjo.services;

import androidx.annotation.NonNull;

import com.example.covid19shahajjo.models.Area;
import com.example.covid19shahajjo.models.AreaWithContacts;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContactService implements FireStoreService {

    private FirebaseFirestore fireStore;
    private final String LOGGER = "contact_support_log";

    private final int TOKEN_WITH_CONTACTS = 2;
    private final int TOKEN_WITHOUT_CONTACTS = 1;

    public ContactService(){
        fireStore = FirebaseFirestore.getInstance();
    }

    public void getDivisions(ServiceCallback callback){
        CollectionReference collectionReference = fireStore.collection("divisions");
        getCollectionReference(collectionReference, callback, TOKEN_WITHOUT_CONTACTS);
    }

    public void getDistricts(String divisionId, ServiceCallback callback){
        String path = getDocumentPath(divisionId);
        CollectionReference collectionReference = fireStore.collection(path);
        Logger.getLogger(LOGGER).log(Level.INFO, "collection path: "+path);
        getCollectionReference(collectionReference, callback, TOKEN_WITHOUT_CONTACTS);
    }


    public void getSubDistricts(String divisionId, String districtId, ServiceCallback callback){
        String path = getDocumentPath(divisionId, districtId);
        CollectionReference collectionReference = fireStore.collection(path);
        Logger.getLogger(LOGGER).log(Level.INFO, "collection path: "+path);
        getCollectionReference(collectionReference, callback, TOKEN_WITH_CONTACTS);
    }

    @Override
    public void getCollectionReference(CollectionReference collectionReference, ServiceCallback callback, int token){
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                Logger.getLogger(LOGGER).log(Level.INFO, "reference onSuccess");
                mapResultAndCallback(documentSnapshots,callback, token);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Logger.getLogger(LOGGER).log(Level.INFO, "ref onFailure");
                callback.onFailed(e);
            }
        });
    }

    @Override
    public void getDocumentReference(DocumentReference documentReference, ServiceCallback callback, int token) {

    }

    @Override
    public void mapResultAndCallback(QuerySnapshot querySnapshot, ServiceCallback callback, int token) {
        if(token == TOKEN_WITHOUT_CONTACTS){
            mapAreaOnlyAndCallback(querySnapshot, callback);
        }else if(token == TOKEN_WITH_CONTACTS){
            mapAreaWithContactsAndCallback(querySnapshot, callback);
        }
    }

    @Override
    public void mapResultAndCallback(DocumentSnapshot documentSnapshot, ServiceCallback callback, int token) {

    }

    private String getDocumentPath(String divisionId){
        return "divisions/"+divisionId+"/districts";
    }

    private String getDocumentPath(String divisionId, String districtId){
        return getDocumentPath(divisionId)+"/"+districtId+"/subDistricts";
    }

    private void mapAreaOnlyAndCallback(QuerySnapshot querySnapshot, ServiceCallback callback){
        List<Area> areaData = new ArrayList<>();
        if(querySnapshot.isEmpty()){
            callback.onResult(areaData);
            return;
        }
        for(DocumentSnapshot doc : querySnapshot.getDocuments()){
            Area area = doc.toObject(Area.class);
            area.Id = doc.getId();
            areaData.add(area);
        }
        callback.onResult(areaData);
    }

    private void mapAreaWithContactsAndCallback(QuerySnapshot querySnapshot, ServiceCallback callback){
        List<AreaWithContacts> areaData = new ArrayList<>();
        if(querySnapshot.isEmpty()){
            callback.onResult(areaData);
            return;
        }
        for(DocumentSnapshot doc : querySnapshot.getDocuments()){
            AreaWithContacts area = doc.toObject(AreaWithContacts.class);
            area.Id = doc.getId();
            areaData.add(area);
        }
        callback.onResult(areaData);
    }
}

