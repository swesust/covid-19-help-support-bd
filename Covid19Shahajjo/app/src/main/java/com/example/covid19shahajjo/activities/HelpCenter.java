package com.example.covid19shahajjo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid19shahajjo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONObject;

public class HelpCenter extends AppCompatActivity {

    private Button button;
    private TextView division, district, thana;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference noteRef = db.document("hospitals/1lVyeJYuOAPnaCvlBWBn ");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center);

        button = findViewById(R.id.btn);
        division = findViewById(R.id.div);
        district = findViewById(R.id.dis);
        thana = findViewById(R.id.thana);

        apiCall();
        getData();
        data();

        button.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HelpCenterMap.class);
                startActivity(intent);
            }
        });

    }

    public void apiCall(){
        String URL = "https://api.mapbox.com/geocoding/v5/mapbox.places/GAZIPUR.json?access_token=pk.eyJ1IjoibWVoZWRpcnVtaSIsImEiOiJjanl6ZmFsdGEwMTJpM21vNTJkbHdpMHhuIn0.SgHk-BCo27ElvGh5enUbxg";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Rest Response: ", response.toString());
                        //Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest Response: ", error.toString());

                    }
                }
        );

        requestQueue.add(objectRequest);
    }

    public void getData(){

        noteRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            Log.e("response: ", documentSnapshot.toString());
                            Toast.makeText(getApplicationContext(),documentSnapshot.toString(), Toast.LENGTH_SHORT).show();
                        }else{

                            Toast.makeText(getApplicationContext(),"Document doesn't exist", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public void data(){
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.e( document.getId(),document.getData().toString());
                            }
                        } else {
                            Log.w("Error getting documents.", task.getException());
                        }
                    }
                });
    }
}
