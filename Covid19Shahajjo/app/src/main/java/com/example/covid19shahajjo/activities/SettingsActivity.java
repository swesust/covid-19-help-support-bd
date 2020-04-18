package com.example.covid19shahajjo.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.example.covid19shahajjo.R;
import com.example.covid19shahajjo.utils.Enums;
import com.example.covid19shahajjo.utils.SharedStorge;

public class SettingsActivity extends AppCompatActivity {

    private RadioButton btnBengali, btnEnglish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        layoutComponentsMapping();
        setUserSelectedLanguage();
    }

    private void layoutComponentsMapping(){
        btnBengali =  (RadioButton) findViewById(R.id.settings_lang_bengali);
        btnEnglish = (RadioButton) findViewById(R.id.settings_lang_english);
    }

    private void setUserSelectedLanguage(){
        Enums.Language userLanguage = SharedStorge.getUserLanguage(this);
        if(userLanguage == Enums.Language.BD){
            btnBengali.setSelected(true);
        }
        else if(userLanguage == Enums.Language.EN){
            btnEnglish.setSelected(true);
        }
    }

    public void onLanguageSelect(View view){
        if(view.getId() == btnEnglish.getId()){
            boolean result = SharedStorge.setUserLanguage(this, Enums.Language.BD);
        }
        else {
            boolean result = SharedStorge.setUserLanguage(this, Enums.Language.EN);
        }
    }

    public void onSave(View view){
        redirectHomePage();
    }

    public void redirectHomePage(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
