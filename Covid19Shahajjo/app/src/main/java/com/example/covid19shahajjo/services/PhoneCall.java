package com.example.covid19shahajjo.services;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PhoneCall extends AppCompatActivity {

    //***************************Developer section begin****************************************
    //Calling way from another Activityclass

   /* private EditText mEditTextNumber;
    private  ImageView dialButtonImage;
    private PhoneCall phoneCall=null;

    Do the same on  onCreate() or any other method before getting call service:

    mEditTextNumber =findViewById(R.id.PhoneCallEditTextId);
    dialButtonImage = findViewById(R.id.PhoneCallIconImageId);
    phoneCall=new PhoneCall(this,PhoneCallActivity.this,mEditTextNumber,dialButtonImage);##Create Instance of phonecall Service*/

    //***************************Developer section End****************************************


    private static final int REQUEST_CALL =1;//user permission request Granted/Denied status
    private Context context=null; //calling activity context
    private EditText mEditTextNumber;
    private ImageView dialButtonImage=null; //call dialing button
    private Activity activity;

    public PhoneCall(final Context context,Activity activity,EditText mEditTextNumber,ImageView dialButtonImage){
        this.context=context;
        this.activity=activity;
        this.mEditTextNumber=mEditTextNumber;
        this.dialButtonImage=dialButtonImage;

        ///OnClick listener for making call
        this.dialButtonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall();
               // Toast.makeText(context,"Requesting",Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void makePhoneCall(){

        String number=mEditTextNumber.getText().toString().trim();

        if(number.trim().length()>0){
            if(ContextCompat.checkSelfPermission(context,
                    Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.CALL_PHONE},REQUEST_CALL);
            }else{
                String dial = "tel:"+number;
                context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        }else{
            Toast.makeText(context,"Enter a valid number",Toast.LENGTH_SHORT).show();;

        }



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode== REQUEST_CALL){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makePhoneCall();
            }else{
                Toast.makeText(activity,"Permission Denied",Toast.LENGTH_SHORT).show();
            }

        }else {

        }
    }


}
