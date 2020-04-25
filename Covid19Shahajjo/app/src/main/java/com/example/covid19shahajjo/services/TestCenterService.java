package com.example.covid19shahajjo.services;

import androidx.annotation.NonNull;

import com.example.covid19shahajjo.models.TestCenter;
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

public class TestCenterService implements FireStoreService {

    private FirebaseFirestore firestore;
    private final String collectionName = "testCenters";
    private final String LOGGER = "test_center_service";

    public TestCenterService(){
        firestore = FirebaseFirestore.getInstance();
    }

    public void getAllCenters(ServiceCallback callback){
        CollectionReference collectionReference = firestore.collection(collectionName);
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                mapResultAndCallback(documentSnapshots, callback, -1);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onFailed(e);
            }
        });
    }

    public void getCenters(String districtName, ServiceCallback callback){
        CollectionReference collectionReference = firestore.collection(collectionName);
        collectionReference.whereEqualTo("District", districtName).get()
            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot documentSnapshots) {
                    mapResultAndCallback(documentSnapshots, callback, -1);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    callback.onFailed(e);
                }
            });
    }

    @Override
    public void mapResultAndCallback(QuerySnapshot querySnapshot, ServiceCallback callback, int token) {
        Logger.getLogger(LOGGER).log(Level.INFO, "result mapper called");

        List<TestCenter> data = new ArrayList<>();
        if(querySnapshot.isEmpty()){
            callback.onResult(data);
            return;
        }
        for(DocumentSnapshot doc : querySnapshot.getDocuments()){
            TestCenter testCenter = doc.toObject(TestCenter.class);
            testCenter.Id = doc.getId();
            data.add(testCenter);
        }
        callback.onResult(data);
    }

    @Override
    public void mapResultAndCallback(DocumentSnapshot documentSnapshot, ServiceCallback callback, int token) {

    }

    @Override
    public void getCollectionReference(CollectionReference collectionReference, ServiceCallback callback, int token) {

    }

    @Override
    public void getDocumentReference(DocumentReference documentReference, ServiceCallback callback, int token) {

    }
}
