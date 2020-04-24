package com.example.covid19shahajjo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.covid19shahajjo.R;
import com.example.covid19shahajjo.utils.Enums;
import com.example.covid19shahajjo.utils.SharedStorge;

public class About extends AppCompatActivity {
    private TextView mesaage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        mesaage = findViewById(R.id.user_message);
        setText();
    }

    public void setText(){
        Enums.Language language = SharedStorge.getUserLanguage(this);
        if(language == Enums.Language.EN){
            mesaage.setText(R.string.message_for_user_eng);
        }else{
            mesaage.setText(R.string.message_for_user_ban);
        }
    }
}
