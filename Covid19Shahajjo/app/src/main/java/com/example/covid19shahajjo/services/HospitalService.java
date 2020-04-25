package com.example.covid19shahajjo.services;

import androidx.annotation.NonNull;

import com.example.covid19shahajjo.models.HealthCenter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HospitalService implements FireStoreService{

    private FirebaseFirestore fireStore;

    public HospitalService(){
        fireStore = FirebaseFirestore.getInstance();
    }

    public void getHospitals(ServiceCallback callback){
        // to fetch all hospitals
        CollectionReference collectionReference = fireStore.collection("hospitals");
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

    public void getHospitals(String districtName, ServiceCallback callback){
        // to fetch a particular district hospitals and name should be cap first: Dhaka, Khulna
        fireStore.collection("hospitals").whereEqualTo("District", districtName)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
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
        List<HealthCenter> data = new ArrayList<>();
        if(querySnapshot.isEmpty()){
            callback.onResult(data);
            return;
        }
        for(DocumentSnapshot doc : querySnapshot.getDocuments()){
            HealthCenter healthCenter = doc.toObject(HealthCenter.class);
            healthCenter.Id = doc.getId();
            data.add(healthCenter);
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