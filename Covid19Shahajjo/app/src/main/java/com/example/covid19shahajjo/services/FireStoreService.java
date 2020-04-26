package com.example.covid19shahajjo.services;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

interface FireStoreService {
    void mapResultAndCallback(QuerySnapshot querySnapshot, ServiceCallback callback, int token);
    void mapResultAndCallback(DocumentSnapshot documentSnapshot, ServiceCallback callback, int token);
    void getCollectionReference(CollectionReference collectionReference, ServiceCallback callback, int token);
    void getDocumentReference(DocumentReference documentReference, ServiceCallback callback, int token);
}
