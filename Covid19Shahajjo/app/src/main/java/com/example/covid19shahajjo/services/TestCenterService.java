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

public class TestCenterService implements FireStoreService {

    private FirebaseFirestore firestore;
    private final String collectionName = "testCenters";

    private TestCenterService(){
        firestore = FirebaseFirestore.getInstance();
    }

    private void getAllCenters(ServiceCallback callback){
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

    private void getCenters(String districtName, ServiceCallback callback){
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
