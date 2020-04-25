package com.example.covid19shahajjo.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

public class Alert {

    private Context context;

    public Alert(Context context){
        this.context = context;
    }

    public void show(String title, String message){
        try{
            AlertDialog.Builder builder =new AlertDialog.Builder(context);
            builder.setTitle(title);
            builder.setMessage(message);
            builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }catch(Error error){
            Log.e("ERROR","Error "+error.getMessage());
        }
    }
}
